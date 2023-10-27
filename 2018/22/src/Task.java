import java.util.*;

public class Task {

    final static String ROCKY = ".";
    final static String WET = "=";
    final static String NARROW = "|";

    final static Map<String, Integer> type2riscLevel = new HashMap<>();

    static {
        type2riscLevel.put(ROCKY, 0);
        type2riscLevel.put(WET, 1);
        type2riscLevel.put(NARROW, 2);
    }

    static final int TOOL_SWITCH_TIME = 7;
    static final int STEP_TIME = 1;

    enum Tools {
        ClimbingGear, Torch, Neither
    }

    final static Map<String, Set<Tools>> type2allowedTools = new HashMap<>();

    static {
        type2allowedTools.put(ROCKY, new HashSet<>(Arrays.asList(Tools.ClimbingGear, Tools.Torch)));
        type2allowedTools.put(WET, new HashSet<>(Arrays.asList(Tools.ClimbingGear, Tools.Neither)));
        type2allowedTools.put(NARROW, new HashSet<>(Arrays.asList(Tools.Neither, Tools.Torch)));
    }

    private class WorkItem {
        Pos pos;
        String regionType;
        long soFarMinutes;
        Tools tool;
        WorkItem predecessor = null;

        public WorkItem(Pos pos, String regionType, Tools tool, long soFarMinutes) {
            this.pos = pos;
            this.regionType = regionType;
            this.tool = tool;
            this.soFarMinutes = soFarMinutes;
        }

        public long score() {
            // f(x) = g(x)      + h(x)
            return soFarMinutes + pos.manhattanDistance(T);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WorkItem workItem = (WorkItem) o;
            return Objects.equals(pos, workItem.pos) && tool == workItem.tool;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, tool);
        }
    }

    Pos M = new Pos(0, 0, "M");
    Pos T = new Pos(0, 0, "T");
    int depth = 0;

    Set<Pos> map = new HashSet<>();

    Map<Pos, Long> pos2ErosionLevel = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        if (input.startsWith("depth"))
            depth = Integer.parseInt(Util.cleanFrom(input, "depth:").trim());
        else if (input.startsWith("target")) {
            String[] split = Util.cleanFrom(input, "target:").trim().split(",");
            T = new Pos(Integer.parseInt(split[0]), Integer.parseInt(split[1]), "T");
        }
    }

    public void afterParse() {
        out("depth", depth);
        out("T", T);

        int riskLevel = 0;

        for (int y = 0; y <= T.y; y++) {
            for (int x = 0; x <= T.x; x++) {
                Pos p = new Pos(x, y);
                long erosionLevel = calcErosionLevel(p);
                String regionType = getRegionType(erosionLevel);
                p.color = regionType;
                riskLevel += type2riscLevel.get(regionType);
                map.add(p);
            }
        }

        out(toStringConsole());
        out("Part 1", "Risk level", riskLevel);

        // Part 2
//        long worstDirectWayMinutes = (long) T.manhattanDistance() * (STEP_TIME + TOOL_SWITCH_TIME);
        long minMinutes = -1;
        // https://de.wikipedia.org/wiki/A*-Algorithmus
        PriorityQueue<WorkItem> openList = new PriorityQueue<>(Comparator.comparingLong(WorkItem::score));
        Set<WorkItem> closedList = new HashSet<>();

        openList.add(new WorkItem(M, ROCKY, Tools.Torch, 0));
        // Sonderfall - schon auf dem ersten Feld kann das Tool gewechselt werden - daher diesen Schritt auch in die OpenList mit aufnehmen.
        openList.add(new WorkItem(M, ROCKY, Tools.ClimbingGear, TOOL_SWITCH_TIME));

        while (!openList.isEmpty()) {
            WorkItem workItem = openList.poll();

            if (workItem.pos.equals(T) && workItem.tool.equals(Tools.Torch)) {
                minMinutes = workItem.soFarMinutes;
                break;
            }

            closedList.add(workItem);

            // expand Node
            // Alle moeglichen Tools durchgehen
            for (Tools tool : type2allowedTools.get(workItem.regionType)) {
                long addMinutesTool = 0;
                if (!tool.equals(workItem.tool))
                    addMinutesTool += TOOL_SWITCH_TIME;

                for (Pos adjacent : Pos.adjacent) {
                    Pos nextPosWouldBe = workItem.pos.addToNew(adjacent);

                    if (nextPosWouldBe.x < 0 || nextPosWouldBe.y < 0)
                        continue;

                    // Mit Tool betretbar?
                    String nextRegionTypeWouldBe = getRegionType(nextPosWouldBe);
                    if (!type2allowedTools.get(nextRegionTypeWouldBe).contains(tool))
                        continue;

                    long stepCosts = addMinutesTool + STEP_TIME;


                    long tentativeG = workItem.soFarMinutes + stepCosts;

                    WorkItem nextWorkItemWouldBe = new WorkItem(nextPosWouldBe, nextRegionTypeWouldBe, tool, tentativeG);
                    nextWorkItemWouldBe.predecessor = workItem;
                    if (closedList.contains(nextWorkItemWouldBe))
                        continue;

                    WorkItem alreadyInList = openList.stream().filter(w -> w.equals(nextWorkItemWouldBe)).findFirst().orElse(null);
                    if (alreadyInList != null && tentativeG >= alreadyInList.soFarMinutes)
                        continue;

                    openList.remove(alreadyInList);
                    openList.add(nextWorkItemWouldBe);
                }
            }
        }

        out("Part 2", "min minutes", minMinutes); // = 944
    }


    long calcGeologicIdx(Pos p) {
        if (p.equals(M) || p.equals(T))
            return 0;
        if (p.y == 0)
            return p.x * 16807L;
        if (p.x == 0)
            return p.y * 48271L;
        return calcErosionLevel(new Pos(p.x - 1, p.y)) * calcErosionLevel(new Pos(p.x, p.y - 1));
    }

    long calcErosionLevel(Pos p) {
        Long erosionLevel = pos2ErosionLevel.get(p);
        if (erosionLevel == null) {

            long geologicIdx = calcGeologicIdx(p);
            erosionLevel = (geologicIdx + depth) % 20183;
            pos2ErosionLevel.put(p, erosionLevel);
        }
        return erosionLevel;
    }

    String getRegionType(long erosionLevel) {
        long mod = erosionLevel % 3;
        if (mod == 0) return ROCKY;
        if (mod == 1) return WET;
        return NARROW;
    }

    String getRegionType(Pos p) {
        return getRegionType(calcErosionLevel(p));
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
        for (Pos pos : map) {
            svg.add(pos);
        }
        return svg.toConsoleString();
    }
}
