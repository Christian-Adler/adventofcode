import java.util.*;

public class Cave {
    private static final List<Direction> directions = Arrays.asList(Direction.Bottom, Direction.Right, Direction.Top, Direction.Left);
    private final LinkedList<LinkedList<RiskPos>> riskLevel = new LinkedList<>();
    private final Set<RiskPos> allRiskPos = new HashSet<>();

    public void add(String line) {
        int newY = riskLevel.size();
        String[] split = line.split("");
        LinkedList<RiskPos> xList = new LinkedList<>();
        riskLevel.add(xList);
        for (int x = 0; x < split.length; x++) {
            int risk = Integer.parseInt(split[x]);

            RiskPos riskPos = new RiskPos(x, newY, risk);
            allRiskPos.add(riskPos);
            xList.add(riskPos);
        }

        // noch 4x jeweils risk++
        ArrayList<RiskPos> source = new ArrayList<>(xList);
        int size = source.size();
        for (int i = 1; i <= 4; i++) {
            for (RiskPos riskPos : source) {
                int newRisk = riskPos.risk + i;
                if (newRisk > 9)
                    newRisk -= 9;
                xList.add(new RiskPos(riskPos.x + i * size, riskPos.y, newRisk));
            }
        }
    }

    public void increaseY() {
        LinkedList<LinkedList<RiskPos>> source = new LinkedList<>(riskLevel);
        int size = source.size();
        for (int i = 1; i <= 4; i++) {
            for (LinkedList<RiskPos> xListSource : source) {
                LinkedList<RiskPos> newXlist = new LinkedList<>();
                for (RiskPos riskPos : xListSource) {
                    int newRisk = riskPos.risk + i;
                    if (newRisk > 9)
                        newRisk -= 9;
                    newXlist.add(new RiskPos(riskPos.x, riskPos.y + i * size, newRisk));
                }
                riskLevel.add(newXlist);
            }
        }
    }

    public RiskPos getRiskLevel(int x, int y) {
        if (y >= riskLevel.size() || y < 0) return null;
        List<RiskPos> riskX = riskLevel.get(y);
        if (x >= riskX.size() || x < 0) return null;
        return riskX.get(x);
    }

    public void calcLowestRiskPath() {
        RiskPos start = getRiskLevel(0, 0);
        RiskPos end = riskLevel.getLast().getLast();

        start.shortestWayToStart.add(start);

        LinkedList<RiskPos> workList = new LinkedList<>();
        workList.add(start);

        boolean addEndAtEnd = true;

        while (!workList.isEmpty() || addEndAtEnd) {
            if (workList.isEmpty()) {
                addEndAtEnd = false;
                workList.add(end);
            }

            RiskPos actPos = workList.removeFirst();
//            System.out.println(actPos.x + "," + actPos.y);

            // Naechsten in der Arbeitsliste
            RiskPos toBottom = getRiskLevel(actPos.x, actPos.y + 1);
            if (toBottom != null && !workList.contains(toBottom))
                workList.add(toBottom);
            RiskPos toRight = getRiskLevel(actPos.x + 1, actPos.y);
            if (toRight != null && !workList.contains(toRight))
                workList.add(toRight);

            // Oben und links pruefen
            int fromTopRisk = -1;
            int fromLeftRisk = -1;

            RiskPos fromTop = getRiskLevel(actPos.x, actPos.y - 1);
            if (fromTop != null)
                fromTopRisk = calcRisk(fromTop.shortestWayToStart);
            RiskPos fromLeft = getRiskLevel(actPos.x - 1, actPos.y);
            if (fromLeft != null)
                fromLeftRisk = calcRisk(fromLeft.shortestWayToStart);

            // Beide Wege moeglich? Dann schnellsten waehlen und anderen Weg rekursiv korrigieren
            if (fromTopRisk > 0 && fromLeftRisk > 0) {
                if (fromTopRisk <= fromLeftRisk) {
                    actPos.shortestWayToStart = new ArrayList<>(fromTop.shortestWayToStart);
                    actPos.shortestWayToStart.add(actPos);
                    checkLowestPaths(fromLeft, actPos);
                } else  /* if (fromLeftRisk < fromTopRisk) */ {
                    actPos.shortestWayToStart = new ArrayList<>(fromLeft.shortestWayToStart);
                    actPos.shortestWayToStart.add(actPos);
                    checkLowestPaths(fromTop, actPos);
                }
            } else if (fromTopRisk > 0) {
                actPos.shortestWayToStart = new ArrayList<>(fromTop.shortestWayToStart);
                actPos.shortestWayToStart.add(actPos);
            } else if (fromLeftRisk > 0) {
                actPos.shortestWayToStart = new ArrayList<>(fromLeft.shortestWayToStart);
                actPos.shortestWayToStart.add(actPos);
            }
//            hightlightPath(actPos);
        }

//        System.out.println("===========================");
//        System.out.println("All shortest Paths");
//        System.out.println("===========================");
//
//        allRiskPos.forEach(riskPos -> {
//            System.out.println(riskPos.x + "," + riskPos.y);
//            hightlightPath(riskPos);
//        });


//        System.out.println("===========================");
        hightlightPath(end);
        System.out.println(calcRisk(end.shortestWayToStart) - start.risk);

    }

    /**
     * @param actPos     - zu pruefndene Pos
     * @param calledFrom - aus welcher Richtung aufgerufen? Das ist die zu pruefende
     */
    private void checkLowestPaths(RiskPos actPos, RiskPos calledFrom) {
        if (actPos.shortestWayToStart.isEmpty())
            return;

//        System.out.println("checkLowestPaths: " + actPos.x + "," + actPos.y + " calledFrom: " + calledFrom.x + "," + calledFrom.y);

        int actRisk = calcRisk(actPos.shortestWayToStart);

        // Waere ueber die calledFromRichtung besser als die bisherige?
        int newRiskWouldBe = calcRisk(calledFrom.shortestWayToStart) + actPos.risk;

        if (newRiskWouldBe < actRisk) {
            actPos.shortestWayToStart = new ArrayList<>(calledFrom.shortestWayToStart);
            actPos.shortestWayToStart.add(actPos);

            // Alle 4 Richtungen pruefen
            List<RiskPos> checkPositions = Arrays.asList(getRiskLevel(actPos.x - 1, actPos.y), getRiskLevel(actPos.x + 1, actPos.y), getRiskLevel(actPos.x, actPos.y - 1), getRiskLevel(actPos.x, actPos.y + 1));
            for (RiskPos checkPos : checkPositions) {
                if (checkPos != null && !checkPos.equals(calledFrom)) {
                    checkLowestPaths(checkPos, actPos);
                }
            }
        }
    }

    public int calcRisk(List<RiskPos> path) {
        if (path.isEmpty()) return -1;
        return path.stream().mapToInt(riskPos -> riskPos.risk).sum();
    }

    public void hightlightPath(RiskPos riskPos) {
        hightlightPath(riskPos.shortestWayToStart);
    }

    public void hightlightPath(List<RiskPos> path) {
        allRiskPos.forEach(riskPos -> riskPos.highlight = false);
        path.forEach(pos -> pos.highlight = true);
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        riskLevel.forEach(list -> builder.append(list).append("\r\n"));
        return builder.toString();
    }
}
