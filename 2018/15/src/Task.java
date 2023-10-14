import java.util.*;
import java.util.stream.Collectors;

public class Task {

    Set<Pos> caverns = new HashSet<>();
    Set<Pos> walls = new HashSet<>();
    List<Unit> units = new ArrayList<>();
    Set<Pos> additionalInfoPositions = new HashSet<>();
    int maxX = 0;
    int maxY = 0;

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split("");
        maxX = Math.max(maxX, split.length);

        for (int x = 0; x < split.length; x++) {
            String s = split[x];

            Pos p;
            if (!s.equals("#")) {
                p = new Pos(x, maxY, ".");
                caverns.add(p);

                if (!s.equals(".")) {
                    Unit unit = new Unit(s, p.copy());
                    units.add(unit);
                }
            } else {
                p = new Pos(x, maxY, "#");
                walls.add(p);
            }
        }

        maxY++;
    }

    public void afterParse() {
        out(toStringConsole());

        List<Unit> unitsBackup = units.stream().map(Unit::copy).toList();

        part1();

        part2(unitsBackup);
    }

    private void part2(List<Unit> unitsBackup) {
        boolean allElvesAlive = false;
        int elveAttack = 3;
        while (!allElvesAlive) {
            elveAttack++;
            units = new ArrayList<>(unitsBackup.stream().map(Unit::copy).toList());
            for (Unit unit : units) {
                if (unit.isElfe)
                    unit.attackPower = elveAttack;
            }

            allElvesAlive = true;


            int i = 0;
            while (unitsOfBoothTypesAreAlive() && allElvesAlive) {
                i++;

                List<Unit> unitsWorkListInReadableOrder = getUnitsWorkListInReadableOrder();
                //        out(unitsWorkListInReadableOrder);

                while (!unitsWorkListInReadableOrder.isEmpty()) {
                    additionalInfoPositions.clear();
                    Unit actUnit = unitsWorkListInReadableOrder.remove(0);
                    //                out(actUnit);

                    if (!units.contains(actUnit)) {
                        out("ActUnit no longer alive");
                        continue;
                    }

                    // Gegner finden
                    List<Unit> targets = getUnitTargets(actUnit);
                    // out(targets);
                    if (targets.isEmpty()) {
                        out("Found no targets");
                        i--;
                        break;
                    }

                    move(actUnit, targets);

                    // attack
                    Unit attackUnit = getAttackUnitTarget(actUnit);
                    if (attackUnit == null) continue;

                    if (attackUnit.attack(actUnit.attackPower)) {
                        if (attackUnit.isElfe)
                            allElvesAlive = false;
                        units.remove(attackUnit);
                    }
                }
            }

            if (!allElvesAlive)
                continue;

            out("Part 2 Finished:");
            out(toStringConsole());
            out("elveAttack power", elveAttack);
            out("victory round", i);
//        out(getUnitsWorkListInReadableOrder());
            int sumRemainingHitPoints = units.stream().mapToInt(Unit::getHitPoints).sum();
            out("sumRemainingHitPoints", sumRemainingHitPoints);
            int outcome = i * sumRemainingHitPoints;
            out("outcome", outcome);
        }
    }

    private void part1() {
        int i = 0;
        while (unitsOfBoothTypesAreAlive()) {
            i++;

            List<Unit> unitsWorkListInReadableOrder = getUnitsWorkListInReadableOrder();
            //        out(unitsWorkListInReadableOrder);

            while (!unitsWorkListInReadableOrder.isEmpty()) {
                additionalInfoPositions.clear();
                Unit actUnit = unitsWorkListInReadableOrder.remove(0);
                //                out(actUnit);

                if (!units.contains(actUnit)) {
                    out("ActUnit no longer alive");
                    continue;
                }

                // Gegner finden
                List<Unit> targets = getUnitTargets(actUnit);
                // out(targets);
                if (targets.isEmpty()) {
                    out("Found no targets");
                    i--;
                    break;
                }

                move(actUnit, targets);

                // attack
                Unit attackUnit = getAttackUnitTarget(actUnit);
                if (attackUnit == null) continue;

                if (attackUnit.attack(attackUnit.attackPower))
                    units.remove(attackUnit);
            }
        }
        out("Finished:");
        out(toStringConsole());

        out("victory round", i);
//        out(getUnitsWorkListInReadableOrder());
        int sumRemainingHitPoints = units.stream().mapToInt(Unit::getHitPoints).sum();
        out("sumRemainingHitPoints", sumRemainingHitPoints);
        int outcome = i * sumRemainingHitPoints;
        out("outcome", outcome);
    }

    private boolean unitsOfBoothTypesAreAlive() {
        return units.stream().map(u -> u.isElfe).collect(Collectors.toSet()).size() > 1;
    }

    private void move(Unit actUnit, List<Unit> targets) {
        // Bereits direkt neben einem Gegner? Dann keine Bewegung notwendig
        List<Pos> targetPositions = targets.stream().map(u -> u.p).toList();
        boolean alreadyNextToEnemy = false;
        for (Pos adjacentPosition : Pos.adjacentPositions) {
            if (targetPositions.contains(actUnit.p.addToNew(adjacentPosition))) {
                alreadyNextToEnemy = true;
                break;
            }
        }

        // Nicht bereits direkt neben Gegner? Dann einen Schritt Richtung naechsten Gegener
        if (alreadyNextToEnemy)
            return;

        List<Pos> freeNextToTargetPositions = getFreeAdjacentInRangePositions(targets);
//        out("freeNextToTargetPositions", freeNextToTargetPositions.size());
//        for (Pos freeNextToTargetPosition : freeNextToTargetPositions) {
//            freeNextToTargetPosition.color = "?";
//            additionalInfoPositions.add(freeNextToTargetPosition);
//        }

        // Kein Gegner hat freien Platz neben sich - kein move moeglich.
        if (freeNextToTargetPositions.isEmpty())
            return;

        Map<Pos, List<Pos>> shortestPaths = getShortestPathsWithStartInReadableOrder(actUnit.p);
        // Im Moment kuerzeste Pfade zu allen moeglichen Positionen
//        out(shortestPaths);

//        for (Pos freeNextToTargetPosition : freeNextToTargetPositions) {
//            if (!shortestPaths.containsKey(freeNextToTargetPosition))
//                continue;
//            freeNextToTargetPosition.color = "@";
//            additionalInfoPositions.add(freeNextToTargetPosition);
//        }

        // jetzt nur noch kuerzeste Pfade zu moeglichen Zielen (bzw. Positionen direkt neben Ziel Units)
        shortestPaths.keySet().removeIf(p -> !freeNextToTargetPositions.contains(p));

        int shortestPathNumSteps = Integer.MAX_VALUE;
        Map<Pos, Integer> targetPos2ShortestPathLength = new HashMap<>();
        for (Map.Entry<Pos, List<Pos>> entry : shortestPaths.entrySet()) {
            int pathSteps = entry.getValue().size();
            targetPos2ShortestPathLength.put(entry.getKey(), pathSteps);
            shortestPathNumSteps = Math.min(shortestPathNumSteps, pathSteps);
        }

//        out("shortestPathNumSteps", shortestPathNumSteps);

        Set<Pos> shortestDistancePositions = new HashSet<>();
        for (Map.Entry<Pos, Integer> entry : targetPos2ShortestPathLength.entrySet()) {
            if (entry.getValue() == shortestPathNumSteps)
                shortestDistancePositions.add(entry.getKey());
        }
//        out("shortestDistancePositions", shortestDistancePositions);

//        for (Pos shortestDistancePosition : shortestDistancePositions) {
//            shortestDistancePosition.color = "!";
//            additionalInfoPositions.add(shortestDistancePosition);
//        }

        Pos nextTarget = getFirstPosInReadableOrder(shortestDistancePositions);
        if (nextTarget == null) // kein Ziel erreihbar?
            return;

//            nextTarget.color = "+";
//            additionalInfoPositions.add(nextTarget);

//            out(toStringConsole());

        //  kuerzesten Pfade ermitteln
        ArrayList<Pos> shortestPathsToNextTarget = getShortestPathInReadableOrderStart(actUnit.p, nextTarget, shortestPathNumSteps);
//            out(shortestPathsToNextTarget);

        if (shortestPathsToNextTarget == null) return;

        if (shortestPathsToNextTarget.isEmpty())
            return;

        actUnit.p = shortestPathsToNextTarget.get(0);
    }

    /**
     * (falls moeglich) kuerzesten Pfade von p nach end ermitteln mit start in Readable Order
     */
    ArrayList<Pos> getShortestPathInReadableOrderStart(Pos p, Pos end, int maxLength) {
        // Pfad-Suche in ReadableOrder-Reihenfolge starten - bei mehreren Moeglichkeiten ist nur der erste Schritt ausschlaggebend.
        LinkedList<Pos> adjacentPositionsInReadableOrder = getPositionsInReadableOrder(Pos.adjacentPositions);
        if (adjacentPositionsInReadableOrder == null) return new ArrayList<>();

        ArrayList<Pos> maxLengthPath = null;

        Set<Pos> freePositions = new HashSet<>(caverns);
        units.stream().map(u -> u.p).toList().forEach(freePositions::remove);

        Set<Pos> alreadyVisitedOnce = new HashSet<>();
        alreadyVisitedOnce.add(p);

        ArrayList<Pos> initialPath = new ArrayList<>();
        initialPath.add(p);

        ArrayList<ArrayList<Pos>> workList = new ArrayList<>();
        workList.add(initialPath);

        while (!workList.isEmpty() && maxLengthPath == null) {
            ArrayList<Pos> actPath = workList.remove(0);
            Pos actPos = actPath.get(actPath.size() - 1);

            for (Pos adjacentPosition : adjacentPositionsInReadableOrder) {
                Pos pNext = actPos.addToNew(adjacentPosition);

                // Generell moeglicher Schritt?
                if (freePositions.contains(pNext)) {
                    if (actPath.contains(pNext))
                        continue;

                    if (!alreadyVisitedOnce.add(pNext))
                        continue;

                    ArrayList<Pos> pathNext = new ArrayList<>(actPath);
                    pathNext.add(pNext);

                    // Ziel ueberhaupt noch theoretisch erreichbar innerhalb maxLength?
                    if (pathNext.size() + pNext.manhattanDistance(end) > maxLength)
                        continue;

                    if (pathNext.size() > maxLength)
                        continue;

                    if (pNext.equals(end))
                        maxLengthPath = pathNext;
                    else
                        workList.add(pathNext);
                }
            }
        }

        // Von allen Pfaden den ersten Punkt entfernen - das ist ja p
        if (maxLengthPath != null)
            maxLengthPath.remove(0);

        return maxLengthPath;
    }

    /**
     * Von p aus zu allen erreichbaren Positionen den kuerzesten Pfad ermitteln - dann kann fuer alle Targets einfach in der Map geschaut werden
     * und es muss nicht fuer jedes Ziel der Algorithmus laufen
     */
    Map<Pos, List<Pos>> getShortestPathsWithStartInReadableOrder(Pos p) {
        LinkedList<Pos> adjacentPositionsInReadableOrder = getPositionsInReadableOrder(Pos.adjacentPositions);
        if (adjacentPositionsInReadableOrder == null) throw new IllegalStateException("null");

        Map<Pos, List<Pos>> shortestPaths = new HashMap<>();
        shortestPaths.put(p, Collections.singletonList(p));

        Set<Pos> freePositions = new HashSet<>(caverns);
        units.stream().map(u -> u.p).toList().forEach(freePositions::remove);
        // Koennte noch opimiert werden. Wenn man bereits einen kuerzesten Pfad zu einem Gegner gefunden hat, dann waere die Pfad-Laenge ein Abbruch-Kriterium

        LinkedList<Pos> initialPath = new LinkedList<>();
        initialPath.add(p);

        LinkedList<LinkedList<Pos>> workList = new LinkedList<>();
        workList.add(initialPath);

        while (!workList.isEmpty()) {
            LinkedList<Pos> actPath = workList.removeFirst();
            Pos actPos = actPath.getLast();

            for (Pos adjacentPosition : adjacentPositionsInReadableOrder) {
                Pos pNext = actPos.addToNew(adjacentPosition);

                // Generell moeglicher Schritt?
                if (freePositions.contains(pNext)) {
                    if (actPath.contains(pNext)) continue;

                    // Schon kuerzerer Pfad vorhanden? Dann Abbruch
                    List<Pos> soFarShortestPath = shortestPaths.get(pNext);
                    if (soFarShortestPath != null && soFarShortestPath.size() <= actPath.size() + 1)
                        continue;

                    LinkedList<Pos> pathNext = new LinkedList<>(actPath);
                    pathNext.add(pNext);

                    shortestPaths.put(pNext, pathNext);
                    workList.add(pathNext);
                }
            }
        }

        return shortestPaths;
    }

    List<Pos> getFreeAdjacentInRangePositions(List<Unit> unitList) {
        List<Pos> freePositions = new ArrayList<>();

        // Erst mal alle Positionen ermitteln
        for (Unit unit : unitList) {
            for (Pos adjacentPosition : Pos.adjacentPositions) {
                freePositions.add(unit.p.addToNew(adjacentPosition));
            }
        }

        // Alle Unit-Positionen entfernen
        List<Pos> unitPositions = units.stream().map(u -> u.p).toList();
        freePositions.removeAll(unitPositions);

        // Alle Waende entfernen
        freePositions.removeAll(walls);

        return freePositions;
    }

    List<Unit> getUnitTargets(Unit unit) {
        return units.stream().filter(u -> u.isElfe != unit.isElfe).toList();
    }

    Unit getAttackUnitTarget(Unit unit) {
        List<Pos> unitsAdjacentPositions = Pos.adjacentPositions.stream().map(p -> p.addToNew(unit.p)).toList();
        List<Unit> adjacentTargets = units.stream().filter(u -> u.isElfe != unit.isElfe).filter(u -> unitsAdjacentPositions.contains(u.p)).toList();
        if (adjacentTargets.isEmpty()) return null;

        if (adjacentTargets.size() == 1)
            return adjacentTargets.get(0);

        // Mehrere moeglich? Dann kleinste HitPoints
        int minHitPoints = adjacentTargets.stream().mapToInt(u -> u.hitPoints).min().orElse(-1);
        if (minHitPoints <= 0)
            throw new IllegalStateException("invalid unit hit points");

        List<Unit> unitsWithMinHitPoints = adjacentTargets.stream().filter(u -> u.hitPoints == minHitPoints).toList();
        if (unitsWithMinHitPoints.size() == 1)
            return unitsWithMinHitPoints.get(0);

        // Wenn mehrere die gleiche kleinste HitPoints haben, dann den in readableOrder
        return getUnitListInReadableOrder(unitsWithMinHitPoints).get(0);
    }

    List<Unit> getUnitsWorkListInReadableOrder() {
        return getUnitListInReadableOrder(units);
    }

    private static ArrayList<Unit> getUnitListInReadableOrder(List<Unit> unitList) {
        ArrayList<Unit> worklist = new ArrayList<>(unitList);
        Comparator<Unit> comparator = Comparator.comparingInt(Unit::getY).thenComparing(Unit::getX);
        worklist.sort(comparator);
        return worklist;
    }

    Pos getFirstPosInReadableOrder(Collection<Pos> positions) {
        LinkedList<Pos> worklist = getPositionsInReadableOrder(positions);
        if (worklist == null) return null;
        if (worklist.isEmpty()) return null;
        return worklist.getFirst();
    }

    private static LinkedList<Pos> getPositionsInReadableOrder(Collection<Pos> positions) {
        if (positions.isEmpty()) return null;
        LinkedList<Pos> worklist = new LinkedList<>(positions);
        Comparator<Pos> comparator = Comparator.comparingInt(Pos::getY).thenComparing(Pos::getX);
        worklist.sort(comparator);
        return worklist;
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
        for (Pos pos : caverns) {
            svg.add(pos);
        }
        for (Pos pos : walls) {
            svg.add(pos);
        }
        for (Unit unit : units) {
            svg.add(unit.p, unit.toUnitString());
        }
        for (Pos p : additionalInfoPositions) {
            svg.add(p);
        }
        return svg.toConsoleString();
    }
}
