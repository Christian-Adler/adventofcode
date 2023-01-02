import java.util.*;

public class Task {

    Map<Pos, HeightItem> heightMap = new HashMap<>();

    HeightItem start = null;
    HeightItem end = null;

    int actY = 0;
    int maxX = 0;
    int maxY = -1;

    public void init() {
    }

    public void addLine(String input) {
        maxY++;

        char[] chars = input.toCharArray();
        maxX = chars.length - 1;

        for (int actX = 0; actX < chars.length; actX++) {
            char c = chars[actX];
            Pos pos = new Pos(actX, actY);
            HeightItem heightItem = new HeightItem(pos, c);
            heightMap.put(pos, heightItem);
            if (heightItem.elevation.equals("S"))
                start = heightItem;
            else if (heightItem.elevation.equals("E"))
                end = heightItem;
        }

        actY++;
    }

    public void afterParse() {
        end.steps2End = 0;
        LinkedList<HeightItem> workList = new LinkedList<>();
        workList.add(end);

        while (!workList.isEmpty()) {
//            out(workList);
            HeightItem actItem = workList.removeFirst();
            Pos actPos = actItem.pos;

            List<Pos> possiblePredecessors = Arrays.asList(new Pos(actPos.x - 1, actPos.y), new Pos(actPos.x + 1, actPos.y), new Pos(actPos.x, actPos.y - 1), new Pos(actPos.x, actPos.y + 1));

            for (Pos testPos : possiblePredecessors) {
                HeightItem testItem = heightMap.get(testPos);
                if (testItem == null)
                    continue;
                if (testItem.height - actItem.height < -1) // nur nach unten ist beschraenkt - hoch ist egal (ist von start gesehen ist es dann ein Sprung nach unten!)
                    continue;

                if (testItem.equals(end)) continue;


                // WegLaenge bis zum Ziel waere
                int steps2EndWouldBe = actItem.steps2End + 1;
                // TestItem noch nicht besucht? Dann kuerzester Pfad ueber aktuelles
                // Oder Schon besucht? Neuer Weg besser?
                if (testItem.steps2End < 0 ||
                        testItem.steps2End > steps2EndWouldBe) {
                    testItem.steps2End = steps2EndWouldBe;
                    testItem.nextPos2End = actPos;

                    if (!workList.contains(testItem))
                        workList.add(testItem);
                }
            }
        }

        out("Steps to end: " + start.steps2End);

        HeightItem actItem = start;
        actItem.isOfShortestPath = true;
        while (!actItem.equals(end)) {
            actItem = heightMap.get(actItem.nextPos2End);

            actItem.isOfShortestPath = true;
        }

        // Find shortest a
        long minAPathSteps = heightMap.values().stream().filter(i -> i.elevation.equals("a") && i.steps2End > 0).mapToInt(i -> i.steps2End).min().orElse(-1);
        out("min a path: " + minAPathSteps);
    }


    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y <= maxY; y++) {
            builder.append("\r\n");
            for (int x = 0; x <= maxX; x++) {
                Pos pos = new Pos(x, y);
//                builder.append(heightMap.get(pos));
//                builder.append(heightMap.get(pos).toStringElevation());
                builder.append(heightMap.get(pos).toStringSVG());
//                builder.append(heightMap.get(pos).toStringHeight());
//                builder.append(heightMap.get(pos).toStringPathLength());
            }
        }
        return builder.toString();
    }
}
