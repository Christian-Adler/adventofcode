import computer.Computer;
import computer.IOutput;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Task {
    Map<Long, Long> programInitial = new HashMap<>();
    Set<Pos> walls = new HashSet<>();
    Set<Pos> free = new HashSet<>();
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

        Task.this.out("oxygenSystem", oxygenSystem);
        Util.writeToAOCSvg(toStringSVG());

        // Map ist aufgebaut - nun von Start zu oxygen den Pfad finden

        Set<Pos> vistited = new HashSet<>();

        int minMovementCmds = findPathToOxygenSystem(droidStart, vistited);

        out("Part 1", "minMovementCmds", minMovementCmds);
    }

    private int findPathToOxygenSystem(Pos actPos, Set<Pos> visited) {
        visited.add(actPos);

        if (actPos.equals(oxygenSystem))
            return visited.size();


        for (Pos adjacent : Pos.adjacent) {
            Pos checkPos = actPos.addToNew(adjacent);
            if (visited.contains(checkPos)) continue;
            if (free.contains(checkPos)) {
                Set<Pos> nextVisited = new HashSet<>(visited);
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

        IOutput output = new IOutput() {
            @Override
            public void out(Long value) {
                if (value == 2) { // Found oxygen system
                    oxygenSystem.set(actPos);
                    computer.addInput(-1); // exit computer
                    return;
                }

                if (detectWalls.get()) {
                    if (value == 0) { // Hit wall
                        walls.add(actPos.addToNew(actDir));
                    } else if (value == 1) { // Step was possible
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
//        free.forEach(pos -> svg.add(pos, " "));
//        svg.add(actPos, "D");
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
