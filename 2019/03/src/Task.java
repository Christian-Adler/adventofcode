import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Task {

    Set<Pos> mapWire1 = new HashSet<>();
    Set<Pos> mapWire2 = new HashSet<>();
    Set<Pos> crossings = new HashSet<>();
    Pos start = new Pos(0, 0);

    public void init() {
    }

    public void addLine(String input) {
        boolean isWire2 = !mapWire1.isEmpty();
        Set<Pos> actMap = mapWire1.isEmpty() ? mapWire1 : mapWire2;
        String[] instructions = input.split(",");

        Pos actPos = start.copy();
        for (String instruction : instructions) {
            Pos add;
            if (instruction.startsWith("R"))
                add = new Pos(1, 0);
            else if (instruction.startsWith("L"))
                add = new Pos(-1, 0);
            else if (instruction.startsWith("U"))
                add = new Pos(0, -1);
            else if (instruction.startsWith("D"))
                add = new Pos(0, 1);
            else throw new IllegalArgumentException("Unexpected instruction: " + instruction);

            int steps = Integer.parseInt(instruction.substring(1));
            for (int i = 0; i < steps; i++) {
                actPos = actPos.addToNew(add);
                actMap.add(actPos);
                if (isWire2) {
                    if (mapWire1.contains(actPos))
                        crossings.add(actPos.copy());
                }
            }
        }
    }

    public void afterParse() throws IOException {
//        out(toStringConsole());
        Util.writeToAOCSvg(toStringSVG());

        int minDistance = Integer.MAX_VALUE;
        for (Pos crossing : crossings) {
            int dist = crossing.manhattanDistance();
            if (dist < minDistance)
                minDistance = dist;
        }
        out("Part 1", "min distance crossing", minDistance);
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
        for (Pos crossing : mapWire1) {
            svg.add(crossing, "#0000ff");
        }
        for (Pos crossing : mapWire2) {
            svg.add(crossing, "#00ff00");
        }
        for (Pos crossing : crossings) {
            svg.add(crossing, "#ff0000");
        }
        svg.add(start, "#00ffff");
//        return svg.toSVGStringAged();
        return svg.toSVGString();
    }


    public String toStringConsole() {
        SVG svg = new SVG();
        for (Pos crossing : mapWire1) {
            svg.add(crossing, "1");
        }
        for (Pos crossing : mapWire2) {
            svg.add(crossing, "2");
        }
        for (Pos crossing : crossings) {
            svg.add(crossing, "X");
        }
        svg.add(start, "O");
        return svg.toConsoleString();
    }
}
