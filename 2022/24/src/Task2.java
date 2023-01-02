import java.util.*;
import java.util.stream.Collectors;

public class Task2 {

    Pos start = null;
    Pos end = null;

    int xMin = 1;
    int yMin = 1;
    int xMax = 1;
    int yMax = 0;

    Set<Blizzard> blizzards = new HashSet<>();

    TreeItem shortestEnd = null;
    TreeItem shortestStart2 = null;
    TreeItem shortestEnd2 = null;

    Map<Pos, Integer> mapWay = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        xMax = input.length();
        yMax++;

        if (yMax == 1)
            start = new Pos(input.indexOf('.') + 1, 1);
        else if (input.charAt(1) == '#' || input.charAt(2) == '#')
            end = new Pos(input.indexOf('.') + 1, yMax);
        else {
            for (int x = 1; x < xMax - 1; x++) {
                char c = input.charAt(x);
                if (c != '.') {
                    Blizzard blizzard = new Blizzard();
                    blizzard.dir = c;
                    blizzard.pos = new Pos(x + 1, yMax);
                    if (c == '>')
                        blizzard.step = Blizzard.RIGHT;
                    else if (c == '<')
                        blizzard.step = Blizzard.LEFT;
                    else if (c == 'v')
                        blizzard.step = Blizzard.DOWN;
                    else
                        blizzard.step = Blizzard.UP;

                    blizzards.add(blizzard);
                }
            }
        }
    }

    public void afterParse() {

        TreeItem root = new TreeItem();
        root.pos = start.copy();

        // 2 End
        Set<TreeItem> workList = new HashSet<>();
        workList.add(root);

        while (shortestEnd == null) {

            for (Blizzard blizzard : blizzards) {
                blizzardStep(blizzard);
            }

            Set<TreeItem> nextWorkList = new HashSet<>();

            for (TreeItem treeItem : workList) {
                if (shortestEnd != null) break;

                // Possible steps
                Pos actPos = treeItem.pos;

                List<Pos> possibleStepsWouldBe = Arrays.asList(actPos.addTo(0, 0), actPos.addTo(-1, 0), actPos.addTo(1, 0), actPos.addTo(0, -1), actPos.addTo(0, 1));
                for (Pos pos : possibleStepsWouldBe) {
                    if (shortestEnd != null) break;

                    if (pos.equals(end)) {
                        shortestEnd = treeItem.addChild(pos);
                    } else if (pos.equals(start) || pos.x > xMin && pos.x < xMax && pos.y > yMin && pos.y < yMax) {
                        if (blizzards.stream().filter(b -> b.pos.equals(pos)).count() == 0) {
                            nextWorkList.add(treeItem.addChild(pos));
                        }
                    }
                }
            }

            workList = nextWorkList;
        }

        out("shortestEnd: " + shortestEnd.steps);

        // TO Start
        workList = new HashSet<>();
        workList.add(shortestEnd);

        while (shortestStart2 == null) {

            for (Blizzard blizzard : blizzards) {
                blizzardStep(blizzard);
            }

            Set<TreeItem> nextWorkList = new HashSet<>();

            for (TreeItem treeItem : workList) {
                if (shortestStart2 != null) break;

                // Possible steps
                Pos actPos = treeItem.pos;

                List<Pos> possibleStepsWouldBe = Arrays.asList(actPos.addTo(0, 0), actPos.addTo(-1, 0), actPos.addTo(1, 0), actPos.addTo(0, -1), actPos.addTo(0, 1));
                for (Pos pos : possibleStepsWouldBe) {
                    if (shortestStart2 != null) break;

                    if (pos.equals(start)) {
                        shortestStart2 = treeItem.addChild(pos);
                    } else if (pos.equals(end) || pos.x > xMin && pos.x < xMax && pos.y > yMin && pos.y < yMax) {
                        if (blizzards.stream().filter(b -> b.pos.equals(pos)).count() == 0) {
                            nextWorkList.add(treeItem.addChild(pos));
                        }
                    }
                }
            }

            workList = nextWorkList;
        }

        out("shortestStart2: " + shortestStart2.steps);

        // To End
        workList = new HashSet<>();
        workList.add(shortestStart2);

        while (shortestEnd2 == null) {

            for (Blizzard blizzard : blizzards) {
                blizzardStep(blizzard);
            }

            Set<TreeItem> nextWorkList = new HashSet<>();

            for (TreeItem treeItem : workList) {
                if (shortestEnd2 != null) break;

                // Possible steps
                Pos actPos = treeItem.pos;

                List<Pos> possibleStepsWouldBe = Arrays.asList(actPos.addTo(0, 0), actPos.addTo(-1, 0), actPos.addTo(1, 0), actPos.addTo(0, -1), actPos.addTo(0, 1));
                for (Pos pos : possibleStepsWouldBe) {
                    if (shortestEnd2 != null) break;

                    if (pos.equals(end)) {
                        shortestEnd2 = treeItem.addChild(pos);
                    } else if (pos.equals(start) || pos.x > xMin && pos.x < xMax && pos.y > yMin && pos.y < yMax) {
                        if (blizzards.stream().filter(b -> b.pos.equals(pos)).count() == 0) {
                            nextWorkList.add(treeItem.addChild(pos));
                        }
                    }
                }
            }

            workList = nextWorkList;
        }

        out("shortestEnd2: " + shortestEnd2.steps);

        // Fuer Zeichnung
        List<Pos> way = new LinkedList<>();
        way.add(shortestEnd2.pos);
        TreeItem treeItem = shortestEnd2;
        while (treeItem.parent != null) {
            treeItem = treeItem.parent;
            way.add(treeItem.pos);
        }

        Collections.reverse(way);

        int stp = 0;
        for (Pos pos : way) {
            mapWay.put(pos, ++stp);
        }
    }

    int minStepsToEnd(TreeItem treeItem) {
        return Math.abs(treeItem.pos.x - end.x) + Math.abs(treeItem.pos.y - end.y);
    }

    void blizzardStep(Blizzard blizzard) {
        blizzard.pos.add(blizzard.step);
        if (blizzard.pos.x == 1)
            blizzard.pos.x = xMax - 1;
        else if (blizzard.pos.x == xMax)
            blizzard.pos.x = 2;
        else if (blizzard.pos.y == 1)
            blizzard.pos.y = yMax - 1;
        else if (blizzard.pos.y == yMax)
            blizzard.pos.y = 2;
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

                if (p.equals(start) || p.equals(end))
                    builder.append("*");
                else if (x == xMin || x == xMax)
                    builder.append("#");
                else if (y == yMin || y == yMax)
                    builder.append("#");
                else {
                    List<Blizzard> blizzardsAtPos = blizzards.stream().filter(b -> b.pos.equals(p)).collect(Collectors.toList());
                    if (blizzardsAtPos.size() > 1)
                        builder.append(blizzardsAtPos.size());
                    else if (blizzardsAtPos.size() == 1)
                        builder.append(blizzardsAtPos.get(0).toStringSimple());
                    else
                        builder.append(".");
                }
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

        int steps = shortestEnd2.steps;

        float stepL = (endL - startL) / (float) steps;
        float stepS = (endS - startS) / (float) steps;
        float stepH = (endH - startH) / (float) steps;


        StringBuilder builder = new StringBuilder();

        for (int y = yMin; y <= yMax; y++) {
            builder.append("\r\n");
            for (int x = xMin; x <= xMax; x++) {
                Pos pos = new Pos(x, y);

                if (pos.equals(start) || pos.equals(end))
                    builder.append("<rect style=\"fill:#ff0000;\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />");
                else if (x == xMin || x == xMax)
                    builder.append("<rect style=\"fill:#d0d0d0;\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />");
                else if (y == yMin || y == yMax)
                    builder.append("<rect style=\"fill:#d0d0d0;\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />");

                else {
                    Integer numVisits = mapWay.getOrDefault(pos, 0);
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

        return builder.toString();
    }
}
