import java.util.*;

public class Task {
    Set<Pos> walls = new HashSet<>();
    Map<Pos, FlowingWater> alreadyWater = new HashMap<>();
    int yMax = 0;
    int yMin = Integer.MAX_VALUE;
    FlowingWater root = new FlowingWater(new Pos(500, 0, "O"), Pos.DOWN);
    Set<FlowingWater> nextRoundStarts = new LinkedHashSet<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] split1 = input.split(",\\s+");
        if (split1[0].startsWith("x")) {
            int xValue = Integer.parseInt(Util.cleanFrom(split1[0], "x="));
            String[] split2 = Util.cleanFrom(split1[1], "y=").split("\\.\\.");
            int yStart = Integer.parseInt(split2[0]);
            int yEnd = Integer.parseInt(split2[1]);
            for (int y = yStart; y <= yEnd; y++) {
                walls.add(new Pos(xValue, y, "#"));
            }
            yMax = Math.max(Math.max(yMax, yStart), yEnd);
            yMin = Math.min(Math.min(yMin, yStart), yEnd);
        } else {
            int yValue = Integer.parseInt(Util.cleanFrom(split1[0], "y="));
            String[] split2 = Util.cleanFrom(split1[1], "x=").split("\\.\\.");
            int xStart = Integer.parseInt(split2[0]);
            int xEnd = Integer.parseInt(split2[1]);
            for (int x = xStart; x <= xEnd; x++) {
                walls.add(new Pos(x, yValue, "#"));
            }
            yMax = Math.max(yMax, yValue);
            yMin = Math.min(yMin, yValue);
        }
    }

    public void afterParse() {
//        out(toStringConsole());
        nextRoundStarts.add(root);

        Pos nextStepWouldBe;
        FlowingWater waterCheckD;
        FlowingWater waterCheckL;
        FlowingWater waterCheckR;
        FlowingWater fw;

        int stepCount = 0;
        while (!nextRoundStarts.isEmpty()) {
//            if (stepCount > 100) break;

            LinkedList<FlowingWater> workList = new LinkedList<>(nextRoundStarts);
            nextRoundStarts.clear();

            for (FlowingWater flowingWater : workList) {
                if (flowingWater.isSettled())
                    continue;
                if (flowingWater.stepDownWouldBe().y > yMax)
                    continue;

                waterCheckL = alreadyWater.get(flowingWater.stepLeftWouldBe());
                waterCheckR = alreadyWater.get(flowingWater.stepRightWouldBe());
                waterCheckD = alreadyWater.get(flowingWater.stepDownWouldBe());

                // Im Fall von unten Wasser verbinden
                if (waterCheckD != null)
                    connectWaterFlow(flowingWater, waterCheckD);

                if (isWall(flowingWater.stepDownWouldBe()) || isSettledWater(flowingWater.stepDownWouldBe())) {

                    if (isWall(flowingWater.stepLeftWouldBe())) {
                        flowingWater.setSettledLeft();
                        nextRoundStarts.addAll(flowingWater.prev);
                    } else if (waterCheckL == null) {
                        fw = flowingWater.stepLeft();
                        nextRoundStarts.add(fw);
                        putToAlreadyWater(fw);
                    } else { // links Wasser
                        connectWaterFlow(flowingWater, waterCheckL);
                        if (waterCheckL.isSettledLeft()) { // Nach links irgendwo gegen die Wand?
                            if (flowingWater.setSettledLeft()) {
                                nextRoundStarts.addAll(flowingWater.prev);
                                if (waterCheckR != null)
                                    nextRoundStarts.add(waterCheckR);
                            }
                        }
                    }

                    if (isWall(flowingWater.stepRightWouldBe())) {
                        flowingWater.setSettledRight();
                        nextRoundStarts.addAll(flowingWater.prev);
                    } else if (waterCheckR == null) {
                        fw = flowingWater.stepRight();
                        nextRoundStarts.add(fw);
                        putToAlreadyWater(fw);
                    } else { // rechts Wasser
                        connectWaterFlow(flowingWater, waterCheckR);
                        if (waterCheckR.isSettledRight()) { // Nach rechts irgendwo gegen die Wand?
                            if (flowingWater.setSettledRight()) {
                                nextRoundStarts.addAll(flowingWater.prev);
                                if (waterCheckL != null)
                                    nextRoundStarts.add(waterCheckL);
                            }
                        }
                    }

                    if (flowingWater.isSettled()) {
                        nextRoundStarts.addAll(flowingWater.prev);
                        if (waterCheckL != null)
                            nextRoundStarts.add(waterCheckL);
                        if (waterCheckR != null)
                            nextRoundStarts.add(waterCheckR);
                    }
                } else if (waterCheckD == null) {
                    fw = flowingWater.stepDown();
                    nextRoundStarts.add(fw);
                    putToAlreadyWater(fw);
                }
            }


            stepCount++;
//            out();
//            out(stepCount);
//            out(toStringConsole());
//            out(alreadyWater);
        }
        out("stepCount", stepCount);

        out(toStringConsole());
//        out(toStringSVG());
        out("yMin", yMin);
        // Nicht alles Wasser zaehlt, sondern nur das ab dem kleinsten y-Hindernis
        int countableWater = alreadyWater.size() - (yMin - 1);
        out("Part 1", "Water count", countableWater); //

        // Part 2
        long sumSettledWater = alreadyWater.values().stream().filter(FlowingWater::isSettled).count();
        out("Part 2", "Settled water count", sumSettledWater);
    }


    private static void connectWaterFlow(FlowingWater from, FlowingWater to) {
        // Schon andere Richtung? Dann Abbruch
        if (to.next.contains(from))
            return;
        from.next.add(to);
        to.prev.add(from);
    }

    private boolean isWall(Pos pos) {
        return walls.contains(pos);
    }

    boolean isSettledWater(Pos pos) {
        FlowingWater fw = alreadyWater.get(pos);
        if (fw == null) return false;
        return fw.isSettled();
    }

    void putToAlreadyWater(FlowingWater fw) {
        alreadyWater.put(fw.pos, fw);
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        return toStringConsole();
    }

    public String toStringSVG() {
        SVG svg = new SVG();
        for (Pos wall : walls) {
            svg.add(wall, "#505050");
        }
        root.treeWalk(pos -> {
            Pos p = pos.copy();
            p.color = "#0000ff";
            svg.add(p);
        });
        svg.add(root.pos, "#00ff00");
        return svg.toSVGStringAged();
    }


    public String toStringConsole() {
        SVG svg = new SVG();
        for (Pos wall : walls) {
            svg.add(wall);
        }
        root.treeWalk((svg::add));
        svg.add(root.pos);
        return svg.toConsoleString();
    }
}
