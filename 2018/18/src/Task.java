import java.util.*;

public class Task {
    private static String OPEN = ".";
    private static String TREE = "|";
    private static String LUMBERYARD = "#";

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

        for (int i = 1; i <= 10; i++) {
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
        }

        // Count
        long numWoodenAcres = area.values().stream().filter(p -> p.color.equals(TREE)).count();
        long numLumberyards = area.values().stream().filter(p -> p.color.equals(LUMBERYARD)).count();
        long resourceValue = numWoodenAcres * numLumberyards;
        out("numWoodenAcres", numWoodenAcres);
        out("numLumberyards", numLumberyards);
        out("resourceValue", resourceValue);
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
