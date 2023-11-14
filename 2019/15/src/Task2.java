import computer.Computer;
import computer.IOutput;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Task2 {
    Map<Long, Long> programInitial = new HashMap<>();
    Set<Pos> walls = new HashSet<>();
    Set<Pos> free = new HashSet<>();
    Set<Pos> visited = new HashSet<>();
    Pos droidStart = new Pos(0, 0);
    Pos actPos = droidStart.copy();
    Pos oxygenSystem = new Pos(0, 0);

    public void init() {
        free.add(actPos.copy());
    }

    public void addLine(String input) {
        programInitial = Computer.parseProgram(input);
    }

    public void afterParse() throws InterruptedException, IOException {
        // Allgemeine Idee: random walk fuer Map-Aufbau
        // auf aktueller Position alle nicht bekannten adjazenten Positionen pruefen (detectWalls=true),
        // indem versucht wird diesen Schritt zu gehen.
        // Entweder es war eine Wand, oder man muss danach diesen Schritt zurueck (stepBack=true).
        // Danach wird per Zufall eine freie Richtung fuer den naechsten Schritt gewaehlt.
        buildMap();

        Task2.this.out("oxygenSystem", oxygenSystem);
        Util.writeToAOCSvg(toStringSVG());

        // Map ist aufgebaut - nun von Start zu oxygen den Pfad finden

        int minMovementCmds = findPathToOxygenSystem(droidStart, new HashSet<>());

        out("Part 1", "minMovementCmds", minMovementCmds);

        // Part 2
        Set<Pos> oxygenFilled = new HashSet<>();
        oxygenFilled.add(oxygenSystem.copy());

        int oxyStepCounter = 0;

        Set<Pos> checks = new HashSet<>();
        checks.add(oxygenSystem.copy());

        while (!checks.isEmpty()) {
            oxyStepCounter++;
            oxygenFilled.addAll(checks);
            Set<Pos> toCheck = new HashSet<>(checks);
            checks.clear();
            for (Pos toCheckPos : toCheck) {
                for (Pos adjacent : Pos.adjacent) {
                    Pos checkPos = toCheckPos.addToNew(adjacent);
                    if (oxygenFilled.contains(checkPos)) continue;
                    if (free.contains(checkPos)) {
                        checks.add(checkPos);
                    }
                }
            }
        }

        out("Part 2", "steps", oxyStepCounter);
    }

    private int findPathToOxygenSystem(Pos actPos, Set<Pos> soFarVisited) {
        soFarVisited.add(actPos);

        if (actPos.equals(oxygenSystem))
            return soFarVisited.size();


        for (Pos adjacent : Pos.adjacent) {
            Pos checkPos = actPos.addToNew(adjacent);
            if (soFarVisited.contains(checkPos)) continue;
            if (free.contains(checkPos)) {
                Set<Pos> nextVisited = new HashSet<>(soFarVisited);
                int result = findPathToOxygenSystem(checkPos, nextVisited);
                if (result > 0)
                    return result;
            }
        }

        return -1;
    }

    private void buildMap() throws InterruptedException {
        Computer computer = new Computer(programInitial, value -> {
        });

        Pos actDir = new Pos(0, -1);

        AtomicBoolean detectWalls = new AtomicBoolean(true);
        AtomicBoolean stepBack = new AtomicBoolean(false);

        AtomicLong counter = new AtomicLong();

        IOutput output = new IOutput() {
            @Override
            public void out(Long value) {
                long count = counter.incrementAndGet();
//                if (count % 1000 == 0) {
//                    try {
//                        Util.writeToAOCSvg(toStringSVG());
//                    } catch (IOException e) {
//                        //
//                    }
//                }

                if (value == 2) { // Found oxygen system
                    oxygenSystem.set(actPos);
                }

                if (detectWalls.get()) {
                    if (value == 0) { // Hit wall
                        walls.add(actPos.addToNew(actDir));
                    } else if (value == 1 || value == 2) { // Step was possible
                        actPos.add(actDir);
                        if (stepBack.get()) {
                            stepBack.set(false);
                        } else {
                            free.add(actPos.copy());
                            stepBack.set(true); // Schritt zurueck gehen
                        }
                    }

//                    Task.this.out("-----------------");
//                    Task.this.out(toStringConsole());

                    if (stepBack.get()) { // Frei gefunden -> dann einfach einen Schritt zurueck, um wieder an die Position zu kommen von der wir gerade pruefen.
                        Pos flipDir = actDir.multToNew(-1);
                        actDir.set(flipDir);
                        computer.addInput(getInputForDir(flipDir)); // flip
                    } else {
                        // gibt es noch unbekannte? oben rechts unten links?
                        for (Pos pos : Pos.adjacent) {
                            Pos checkPos = actPos.addToNew(pos);
                            if (!free.contains(checkPos) && !walls.contains(checkPos)) {
                                actDir.set(pos);
                                computer.addInput(getInputForDir(pos));
                                return;
                            }
                        }

                        // keine unbekannten gefunden? Dann einen freien Nachbar waehlen und auch hin laufen - keine Wand-Detektion mehr
                        detectWalls.set(false);
                        List<Pos> possibleFreeNeighbors = new ArrayList<>();

                        for (Pos pos : Pos.adjacent) {
                            Pos checkPos = actPos.addToNew(pos);
                            if (free.contains(checkPos)) {
                                possibleFreeNeighbors.add(pos);
                            }
                        }

                        if (possibleFreeNeighbors.isEmpty()) throw new IllegalStateException("No free neighbor found");

                        if (possibleFreeNeighbors.size() == 1) {
                            Pos neighbor = possibleFreeNeighbors.get(0);
                            actDir.set(neighbor);
                            computer.addInput(getInputForDir(neighbor));
                            return;
                        }

                        int rndIdx = getRandomNumberInRange(0, possibleFreeNeighbors.size());
                        Pos neighbor = possibleFreeNeighbors.get(rndIdx);
                        actDir.set(neighbor);

                        computer.addInput(getInputForDir(neighbor));
                    }
                } else {
                    actPos.add(actDir);
                    visited.add(actPos.copy());

                    // Abbruchbedingung
                    if (visited.containsAll(free)) {
                        boolean allAdjacentKnown = true;
                        for (Pos pos : Pos.adjacent) {
                            Pos checkPos = actPos.addToNew(pos);
                            if (!free.contains(checkPos) && !walls.contains(checkPos)) {
                                allAdjacentKnown = false;
                            }
                        }

                        if (allAdjacentKnown) {
                            computer.addInput(-1);
                            return;
                        }
                    }

//
//                    Task.this.out("-----------------");
//                    Task.this.out(toStringConsole());

                    detectWalls.set(true);

                    computer.addInput(getInputForDir(actDir));
                }
            }
        };
        computer.setOutput(output);

        computer.addInput(getInputForDir(actDir)); // start with north

        computer.exec();
        computer.join();
    }

    private static int getInputForDir(Pos direction) {
        if (direction.x == 1) return 4;
        if (direction.x == -1) return 3;
        if (direction.y == 1) return 2;
        if (direction.y == -1) return 1;
        throw new IllegalStateException("Got illegal direction");
    }

    public int getRandomNumberInRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
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
        walls.forEach(pos -> svg.add(pos, "#a0a0a0"));
        visited.forEach(pos -> svg.add(pos, "#505050"));
        svg.add(actPos, "#ff00ff");
        if (!oxygenSystem.equals(new Pos(0, 0)))
            svg.add(oxygenSystem, "#ff0000");
        svg.add(droidStart, "#00ff00");

        return svg.toSVGString();
    }


    public String toStringConsole() {
        SVG svg = new SVG();
        walls.forEach(pos -> svg.add(pos, "#"));
        free.forEach(pos -> svg.add(pos, " "));
        svg.add(actPos, "D");
        if (!oxygenSystem.equals(new Pos(0, 0)))
            svg.add(oxygenSystem, "!");
        return svg.toConsoleString();
    }
}
