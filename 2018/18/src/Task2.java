import java.util.*;

public class Task2 {
    private static final String OPEN = ".";
    private static final String TREE = "|";
    private static final String LUMBERYARD = "#";

    int maxX = 0;
    int maxY = -1;
    Map<Pos, Pos> area = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        maxY++;
        maxX = input.length() - 1;
        String[] split = input.split("");
        for (int x = 0; x < split.length; x++) {
            String s = split[x];
            Pos pos = new Pos(x, maxY, s);
            area.put(pos, pos);
        }
    }

    public void afterParse() {
        out("area", maxX + 1, "x", maxY + 1);
        out(toStringConsole());

        List<Long> resourceValues = new LinkedList<>();
        LinkedList<Long> last10Values = new LinkedList<>();

        for (int i = 1; i <= 1000000000; i++) {
            Set<Pos> targetArea = new HashSet<>();

            for (int y = 0; y <= maxY; y++) {
                for (int x = 0; x <= maxX; x++) {
                    Pos pos = area.get(new Pos(x, y));

                    List<Pos> possibleAdjacencies = Pos.adjacent.stream().map(pos::addToNew).toList();
                    int countOpenAcre = 0;
                    int countTreeAcre = 0;
                    int countLumberyard = 0;

                    for (Pos possibleAdjacency : possibleAdjacencies) {
                        Pos p = area.get(possibleAdjacency);
                        if (p != null) {
                            if (p.color.equals(OPEN))
                                countOpenAcre++;
                            else if (p.color.equals(TREE))
                                countTreeAcre++;
                            else if (p.color.equals(LUMBERYARD))
                                countLumberyard++;
                        }
                    }

                    Pos copy = pos.copy();
                    if (pos.color.equals(OPEN)) {
                        if (countTreeAcre >= 3)
                            copy.color = TREE;
                    } else if (pos.color.equals(TREE)) {
                        if (countLumberyard >= 3)
                            copy.color = LUMBERYARD;
                    } else if (pos.color.equals(LUMBERYARD)) {
                        if (countLumberyard >= 1 && countTreeAcre >= 1)
                            copy.color = LUMBERYARD;
                        else copy.color = OPEN;
                    }
                    targetArea.add(copy);
                }
            }

            area.clear();
            for (Pos pos : targetArea) {
                area.put(pos, pos);
            }

            out(i);
//            out(toStringConsole());


            long numWoodenAcres = area.values().stream().filter(p -> p.color.equals(TREE)).count();
            long numLumberyards = area.values().stream().filter(p -> p.color.equals(LUMBERYARD)).count();
            long resourceValue = numWoodenAcres * numLumberyards;
            last10Values.add(resourceValue);
            if (last10Values.size() > 10)
                last10Values.removeFirst();

            // Find repeats
            // check if the last 10 resourceValues could be found twice in the list.
            // Difference of idx is the step size it repeats
            int idx = Collections.indexOfSubList(resourceValues, last10Values);
            int idx2 = Collections.lastIndexOfSubList(resourceValues, last10Values);
            if (idx >= 0 && idx2 >= 0 && idx != idx2 && i < 100000000) {
                out("duplicate resourceValue at ", idx, idx2);
                int repeatSteps = idx2 - idx;
                while (i + repeatSteps < 1000000000)
                    i += repeatSteps;
            }

            resourceValues.add(resourceValue);
        }

        // Count
        long numWoodenAcres = area.values().stream().filter(p -> p.color.equals(TREE)).count();
        long numLumberyards = area.values().stream().filter(p -> p.color.equals(LUMBERYARD)).count();
        long resourceValue = numWoodenAcres * numLumberyards;
        out("numWoodenAcres", numWoodenAcres);
        out("numLumberyards", numLumberyards);
        out("Part 2", "resourceValue", resourceValue);
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {
        SVG svg = new SVG();
        return svg.toSVGStringAged();
    }


    public String toStringConsole() {
        SVG svg = new SVG();
        area.values().forEach(svg::add);
        return svg.toConsoleString();
    }
}
