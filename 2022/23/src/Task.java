import java.util.*;

public class Task {

    Set<Pos> map = new HashSet<>();

    int xMin = 0;
    int xMax = Integer.MIN_VALUE;
    int yMin = 0;
    int yMax = -1;

    Map<Pos, Integer> visitedInRound = new HashMap<>();

    LinkedList<String> steps = new LinkedList<>(Arrays.asList("N", "S", "W", "E"));

    int round = 0;

    public void init() {
    }

    public void addLine(String input) {
        yMax++;
        xMax = Math.max(xMax, input.length());

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '#') {
                Pos p = new Pos(i, yMax);
                map.add(p);
                visitedInRound.put(p, 1);
            }
        }
    }

    public void afterParse() {
        boolean someElvMoves = true;
        out(toString());

        while (someElvMoves) {
            someElvMoves = false;
            out("\r\n round: " + (++round));
//            out("steps: " + steps);

            Map<Pos, Pos> consideredMoves = new HashMap<>();
            // Consider Move
            for (Pos pos : map) {
                Set<String> neighbors = detectNeighbors(pos);
                if (neighbors.isEmpty())
                    continue;
                for (String step : steps) {
                    if (step.equals("N")) {
                        if (!map.contains(pos.add(-1, -1)) && !map.contains(pos.add(0, -1)) && !map.contains(pos.add(1, -1))) {
                            consideredMoves.put(pos, pos.add(0, -1));
                            break;
                        }
                    } else if (step.equals("S")) {
                        if (!map.contains(pos.add(-1, 1)) && !map.contains(pos.add(0, 1)) && !map.contains(pos.add(1, 1))) {
                            consideredMoves.put(pos, pos.add(0, 1));
                            break;
                        }
                    } else if (step.equals("W")) {
                        if (!map.contains(pos.add(-1, -1)) && !map.contains(pos.add(-1, 0)) && !map.contains(pos.add(-1, 1))) {
                            consideredMoves.put(pos, pos.add(-1, 0));
                            break;
                        }
                    } else if (step.equals("E")) {
                        if (!map.contains(pos.add(1, -1)) && !map.contains(pos.add(1, 0)) && !map.contains(pos.add(1, 1))) {
                            consideredMoves.put(pos, pos.add(1, 0));
                            break;
                        }
                    }
                }
            }

            // Move
            // Find duplicate targets
            Set<Pos> consideredTargets = new HashSet<>(consideredMoves.values());
            if (consideredTargets.size() < consideredMoves.size()) {
                Set<Pos> addTest = new HashSet<>();
                Set<Pos> targetDuplicates = new HashSet<>();

                for (Pos target : consideredMoves.values()) {
                    if (!addTest.add(target))
                        targetDuplicates.add(target);
                }

                Iterator<Map.Entry<Pos, Pos>> it = consideredMoves.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Pos, Pos> entry = it.next();
                    if (targetDuplicates.contains(entry.getValue()))
                        it.remove();
                }
            }

            someElvMoves = !consideredMoves.isEmpty();
            for (Map.Entry<Pos, Pos> entry : consideredMoves.entrySet()) {
                map.remove(entry.getKey());
                Pos newPos = entry.getValue();
                map.add(newPos);

                visitedInRound.put(newPos, round);

                xMax = Math.max(xMax, newPos.x);
                xMin = Math.min(xMin, newPos.x);
                yMax = Math.max(yMax, newPos.y);
                yMin = Math.min(yMin, newPos.y);
            }

            // Cycle steps
            String step = steps.removeFirst();
            steps.add(step);

//            out(toString());

//            if (round == 10) {
//                out("Calc");
//                xMin = map.stream().mapToInt(p -> p.x).min().orElse(0);
//                xMax = map.stream().mapToInt(p -> p.x).max().orElse(0);
//                yMin = map.stream().mapToInt(p -> p.y).min().orElse(0);
//                yMax = map.stream().mapToInt(p -> p.y).max().orElse(0);
//
//                int numAllPosWouldBe = (xMax - xMin + 1) * (yMax - yMin + 1);
//                int empty = numAllPosWouldBe - map.size();
//                out("empty grounds: " + empty);
//                return;
//            }
        }
    }

    Set<String> detectNeighbors(Pos p) {
        Set<String> neighbors = new HashSet<>();

        if (map.contains(p.add(-1, -1))) neighbors.add("NW");
        if (map.contains(p.add(0, -1))) neighbors.add("N");
        if (map.contains(p.add(1, -1))) neighbors.add("NE");
        if (map.contains(p.add(1, 0))) neighbors.add("E");
        if (map.contains(p.add(1, 1))) neighbors.add("SE");
        if (map.contains(p.add(0, 1))) neighbors.add("S");
        if (map.contains(p.add(-1, 1))) neighbors.add("SW");
        if (map.contains(p.add(-1, 0))) neighbors.add("W");

        return neighbors;
    }

    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            input = input.replace(string, "");
        }
        return input;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int y = yMin; y <= yMax; y++) {
            builder.append("\r\n");
            for (int x = xMin; x <= xMax; x++) {
                Pos p = new Pos(x, y);
                if (map.contains(p))
                    builder.append("#");
                else
                    builder.append(".");
            }
        }

        return builder.toString();
    }

    public String toStringSVG() {
        int startL = 20;
        int endL = 50;
        int startS = 20;
        int endS = 100;

        int startH = 260;
        int endH = 70;

        int steps = round;

        float stepL = (endL - startL) / (float) steps;
        float stepS = (endS - startS) / (float) steps;
        float stepH = (endH - startH) / (float) steps;

//        int step = 1;

//        String rgb = Util.HSLtoRGB(startH + step * stepH, startS * step * stepS, startL * step * stepL);

        StringBuilder builder = new StringBuilder();

        for (int y = yMin; y <= yMax; y++) {
            builder.append("\r\n");
            for (int x = xMin; x <= xMax; x++) {
                Pos pos = new Pos(x, y);
                if (map.contains(pos))
                    builder.append("<rect style=\"fill:#ff0000;\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />");
                else {
                    Integer numVisits = visitedInRound.getOrDefault(pos, 0);
                    if (numVisits > 0) {
                        float h = startH + numVisits.intValue() * stepH;
                        float s = startS + numVisits.intValue() * stepS;
                        float l = startL + numVisits.intValue() * stepL;
                        String rgb = Util.HSLtoRGB(h, s, l);
                        builder.append("<rect style=\"fill:" + rgb + ";\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />");
                    }
                }
            }
        }

        builder.append("x:").append(xMax - xMin).append(" y:").append(yMax - yMin);

        return builder.toString();
    }
}
