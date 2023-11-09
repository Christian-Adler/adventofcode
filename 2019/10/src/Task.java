import java.util.*;

public class Task {
    Set<Pos> map = new HashSet<>();
    int yMax = -1;
    int xMax = 0;
    Pos maxDetectalbeAsteroidsPos = null;

    public void init() {
    }

    public void addLine(String input) {
        yMax++;
        String[] split = input.split("");
        xMax = split.length - 1;
        for (int x = 0; x < split.length; x++) {
            String s = split[x];
            if (s.equals("#"))
                map.add(new Pos(x, yMax));
        }
    }

    public void afterParse() {

        // Fuer jede Pos alle andern Pos druchgehen und vecotr erstellen
        // in set alle vecotren speichern - bei jedem vector alle im Set pruefen, ob gleicher vecotr - wenn ja, dann nicht neu einfeugen
        //Groesse des Sets ist die Anzahl sichtbarer Asteroiden

        int maxDetectableAsteroids = 0;

//        Map<Pos, Integer> detectable = new HashMap<>();
        for (Pos pos : map) {
            Set<Vector> vectors = new HashSet<>();
            for (Pos checkPos : map) {
                if (pos.equals(checkPos)) continue;

                Vector vec = Vector.of(checkPos.subToNew(pos));
                vectors.add(vec);
            }

            if (vectors.size() > maxDetectableAsteroids) {
                maxDetectableAsteroids = vectors.size();
                maxDetectalbeAsteroidsPos = pos;
            }
//            detectable.put(pos, vectors.size());
        }

//        out(detectable);

//        out(toStringConsole());

        out("Part 1", "maxDetectableAsteroids", maxDetectableAsteroids);
        out("Part 1", maxDetectalbeAsteroidsPos);

        // Part 2
        LinkedList<WorkItem> workList = new LinkedList<>();
        for (Pos pos : map) {
            if (pos.equals(maxDetectalbeAsteroidsPos)) continue;
            workList.add(new WorkItem(pos, maxDetectalbeAsteroidsPos));
        }
        Collections.sort(workList); // Liste im Uhrzeigersinn sortiert.

//        out(workList);

        int noAsteroid = 200;
        int asteroidCounter = 0;

        String prevNormVec = null;
        WorkItem workItem;

        LinkedList<WorkItem> workListNextRound = new LinkedList<>();
        while (!workList.isEmpty()) {
            workItem = workList.removeFirst();
            if (workItem.normalized.equals(prevNormVec)) {
                workListNextRound.add(workItem);

                if (workList.isEmpty()) {
                    prevNormVec = null;
                    workList.addAll(workListNextRound);
                    workListNextRound.clear();
                }

                continue;
            }

            prevNormVec = workItem.normalized;
            asteroidCounter++;
            if (asteroidCounter == noAsteroid) {
                out("Part 2", asteroidCounter, "vaporized", workItem.pos);
                out(workItem.pos.x * 100 + workItem.pos.y);
            }

            if (workList.isEmpty()) {
                prevNormVec = null;
                workList.addAll(workListNextRound);
                workListNextRound.clear();
            }
        }
    }

    static class WorkItem implements Comparable<WorkItem> {
        final Pos pos;
        final Vector vector;
        final double xCompare;
        final double yCompare;
        final int manhattenDist;
        final String normalized;

        WorkItem(Pos pos, Pos monitoringStation) {
            this.pos = pos;
            vector = Vector.of(pos.subToNew(monitoringStation));
            manhattenDist = monitoringStation.manhattanDistance(pos);
            normalized = vector.normalized();
            double[] xyCompare = Arrays.stream(normalized.split(",")).mapToDouble(Double::parseDouble).toArray();
            xCompare = xyCompare[0];
            yCompare = xyCompare[1];
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("WorkItem{");
            sb.append("Vec(norm)=").append(normalized);
            sb.append(", pos=").append(pos);
            sb.append('}');
            return sb.toString();
        }

        public int compareTo(WorkItem other) {
            // Im Uhrzeigersinn sortieren

            int quadrant = getQuadrant(xCompare, yCompare);
            int quadrantOther = getQuadrant(other.xCompare, other.yCompare);

            int val = Integer.compare(quadrant, quadrantOther);
            if (val != 0)
                return val;

            if (quadrant == 2 || quadrant == 4) {
                val = Double.compare(yCompare, other.yCompare);
            } else if (quadrant == 6 || quadrant == 8) {
                val = Double.compare(other.yCompare, yCompare);
            }

            if (val != 0)
                return val;

            return Integer.compare(manhattenDist, other.manhattenDist);
        }

        private static int getQuadrant(double x, double y) {
            if (x == 0 && y == 0) return -1;

            if (x == 0) {
                if (y < 0)
                    return 1;
                else
                    return 5;
            }
            if (y == 0) {
                if (x > 0)
                    return 3;
                else
                    return 7;
            }

            if (x > 0) {
                if (y < 0)
                    return 2;
                else
                    return 4;
            } else {
                if (y > 0)
                    return 6;
                else
                    return 8;
            }
        }
    }

    public void out(Object... str) {
        Util.out(str);
    }

    public String toStringConsole() {
        SVG svg = new SVG();
        map.forEach(svg::add);
        if (maxDetectalbeAsteroidsPos != null)
            svg.add(maxDetectalbeAsteroidsPos, "X");
        return svg.toConsoleString();
    }
}
