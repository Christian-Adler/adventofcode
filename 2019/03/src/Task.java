import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Task {
    private record Crossing(int stepsW1, int stepsW2) {
        int sumDistance() {
            return stepsW1 + stepsW2;
        }
    }


    Map<Pos, Integer> mapWire1 = new HashMap<>();
    Map<Pos, Integer> mapWire2 = new HashMap<>();
    Map<Pos, Crossing> crossings = new HashMap<>();
    Pos start = new Pos(0, 0);

    public void init() {
    }

    public void addLine(String input) {
        boolean isWire2 = !mapWire1.isEmpty();
        Map<Pos, Integer> actMap = mapWire1.isEmpty() ? mapWire1 : mapWire2;
        String[] instructions = input.split(",");

        Pos actPos = start.copy();
        int stepCount = 0;
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
                stepCount++;
                actPos = actPos.addToNew(add);

                actMap.putIfAbsent(actPos, stepCount);
                if (isWire2) {
                    if (mapWire1.containsKey(actPos))
                        crossings.put(actPos.copy(), new Crossing(mapWire1.get(actPos), stepCount));
                }
            }
        }
    }

    public void afterParse() throws IOException {
//        out(toStringConsole());
        Util.writeToAOCSvg(toStringSVG());

        int minDistance = Integer.MAX_VALUE;
        int minSignalDelay = Integer.MAX_VALUE;
        for (Map.Entry<Pos, Crossing> crossing : crossings.entrySet()) {
            int dist = crossing.getKey().manhattanDistance();
            if (dist < minDistance)
                minDistance = dist;

            int signalDelay = crossing.getValue().sumDistance();
            if (signalDelay < minSignalDelay)
                minSignalDelay = signalDelay;
        }
        out("Part 1", "min distance crossing", minDistance);
        out("Part 2", "min combined steps crossing", minSignalDelay); // > 1020
    }

    public void out(Object... str) {
        Util.out(str);
    }


    public String toStringSVG() {
        SVG svg = new SVG();
        for (Pos crossing : mapWire1.keySet()) {
            svg.add(crossing, "#0000ff");
        }
        for (Pos crossing : mapWire2.keySet()) {
            svg.add(crossing, "#00ff00");
        }
        for (Pos crossing : crossings.keySet()) {
            svg.add(crossing, "#ff0000");
        }
        svg.add(start, "#ffffff");
//        return svg.toSVGStringAged();
        return svg.toSVGString();
    }


    public String toStringConsole() {
        SVG svg = new SVG();
        for (Pos crossing : mapWire1.keySet()) {
            svg.add(crossing, "1");
        }
        for (Pos crossing : mapWire2.keySet()) {
            svg.add(crossing, "2");
        }
        for (Pos crossing : crossings.keySet()) {
            svg.add(crossing, "X");
        }
        svg.add(start, "O");
        return svg.toConsoleString();
    }
}
