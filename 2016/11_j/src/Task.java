import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task {
    final String regex = "([a-z-]+ generator)|([a-z-]+ microchip)";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    long minSteps = Long.MAX_VALUE;
    LinkedList<WorkItem> workList = new LinkedList<>();

    Set<Elements> elementsSet = new HashSet<>();

    ArrayList<ArrayList<Device>> floors = new ArrayList<>();

    Set<Integer> visited = new HashSet<>();

    public void init() {
        for (int i = 0; i < 4; i++) {
            floors.add(new ArrayList<>());
        }
    }

    public void addLine(String line) {
        int floor = (line.contains("first") ? 1 : (line.contains("second") ? 2 : (line.contains("third") ? 3 : 4))) - 1;

        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String[] matchParts = matcher.group(0).split(" ");
            Type type = Type.valueOf(matchParts[1]);
            Elements element = Elements.valueOf((type.equals(Type.microchip)) ? matchParts[0].replace("-compatible", "") : matchParts[0]);
            elementsSet.add(element);

            Device device = new Device(element, type);
            floors.get(floor).add(device);
        }
    }

    public void afterParse(boolean part2) {
        if (part2) {
            ArrayList<Device> firstFloor = floors.get(0);
            elementsSet.add(Elements.elerium);
            elementsSet.add(Elements.dilithium);
            firstFloor.add(new Device(Elements.elerium, Type.microchip));
            firstFloor.add(new Device(Elements.elerium, Type.generator));
            firstFloor.add(new Device(Elements.dilithium, Type.microchip));
            firstFloor.add(new Device(Elements.dilithium, Type.generator));
        }


        out(floors);

        workList.add(new WorkItem(0, floors, 0));

        long turns = 0;
        while (!workList.isEmpty()) {
            if (minSteps < Long.MAX_VALUE) break;
            turns++;

            if (turns % 10000 == 0)
                out(turns, " len:", workList.size());

            WorkItem workItem = workList.removeFirst();
            minMoves(workItem);
        }

        out("minSteps:", minSteps);
    }

    void minMoves(WorkItem workItem) {
//        printFloors(workItem);

        if (minSteps <= workItem.soFarSteps())
            return;
//        if (!checkValidity(workItem.floors())) // wird schon beim hinzufuegen geprueft.
//            return;

        ArrayList<ArrayList<Device>> flrs = workItem.floors();
        int elevator = workItem.elevator();
        long soFarSteps = workItem.soFarSteps();

        // Finished?
        if (flrs.get(0).isEmpty() && flrs.get(1).isEmpty() && flrs.get(2).isEmpty()) {
            out("found solution: ", workItem.soFarSteps());

            minSteps = workItem.soFarSteps();
            return;
        }

        List<Integer> possible = new ArrayList<>();
        if (elevator < 3) possible.add(elevator + 1);
        if (elevator > 0) possible.add(elevator - 1);

        ArrayList<Device> actFlr = flrs.get(elevator);
        ArrayList<ArrayList<Device>> deviceCombinationsOnFloor = getCombinations(actFlr);

        for (ArrayList<Device> devices : deviceCombinationsOnFloor) {
            for (Integer elevatorMove : possible) {
                ArrayList<ArrayList<Device>> cloneFloors = cloneFloors(flrs);
                ArrayList<Device> floorSoFar = cloneFloors.get(elevator);
                ArrayList<Device> floorTarget = cloneFloors.get(elevatorMove);

                for (Device device : devices) {
                    floorSoFar.remove(device);
                    floorTarget.add(device);
                }

                WorkItem nextItem = new WorkItem(elevatorMove, cloneFloors, soFarSteps + 1);

                if (checkValidity(nextItem.floors())) {
                    // String hashCode = floorsToString(nextItem);
                    String hashCode = floorsHash(nextItem); // reduziert auf Anzahlen pro Zeile
                    if (visited.add(hashCode.hashCode())) {
//                    System.out.print("(" + (++workItemCounter) + ") ");
//                    printFloors(nextItem);
                        workList.add(nextItem);
                    }
                }
            }
        }
    }


    long workItemCounter = 0;

    ArrayList<ArrayList<Device>> getCombinations(ArrayList<Device> flr) {
        ArrayList<ArrayList<Device>> result = new ArrayList<>();

        for (int i = 0; i < flr.size() - 1; i++) {
            for (int j = i + 1; j < flr.size(); j++) {
                ArrayList<Device> combination = new ArrayList<>();
                combination.add(flr.get(i));
                combination.add(flr.get(j));
                result.add(combination);
            }
        }

        for (Device device : flr) {
            ArrayList<Device> combination = new ArrayList<>();
            combination.add(device);
            result.add(combination);
        }
        return result;
    }

    ArrayList<ArrayList<Device>> cloneFloors(ArrayList<ArrayList<Device>> floors) {
        ArrayList<ArrayList<Device>> clone = new ArrayList<>();
        for (ArrayList<Device> floor : floors) {
            ArrayList<Device> floorClone = new ArrayList<>(floor);
            clone.add(floorClone);
        }
        return clone;
    }

    void printFloors(WorkItem workItem) {
        out(floorsToString(workItem));
    }

    private String floorsToString(WorkItem workItem) {
        ArrayList<ArrayList<Device>> floors = workItem.floors();
        int elevator = workItem.elevator();
        StringBuilder builder = new StringBuilder("--------");
        for (int i = floors.size() - 1; i >= 0; i--) {
            ArrayList<Device> flr = floors.get(i);
            builder.append("\r\n").append(i + 1).append(" ");
            if (i == elevator)
                builder.append("E ");
            else
                builder.append("  ");
            builder.append(flr.stream().map(Device::toString).sorted().collect(Collectors.joining(", ")));
        }
        return builder.toString();
    }

    /**
     * Hint from Internet.
     * Nur die Verteilung pro Zeile spielt eine Rolle - nicht die eigentlichen Elemente
     *
     * @param workItem
     * @return
     */
    private String floorsHash(WorkItem workItem) {
        ArrayList<ArrayList<Device>> floors = workItem.floors();
        StringBuilder builder = new StringBuilder();
        builder.append(workItem.elevator());
        for (int i = floors.size() - 1; i >= 0; i--) {
            ArrayList<Device> flr = floors.get(i);
            builder.append("_").append(i + 1).append("_");

            builder.append(flr.stream().filter(d -> d.type().equals(Type.microchip)).count());
            builder.append("-");
            builder.append(flr.stream().filter(d -> d.type().equals(Type.generator)).count());
        }
        return builder.toString();
    }

    boolean checkValidity(ArrayList<ArrayList<Device>> floors) {
        for (ArrayList<Device> floor : floors) {
            if (floor.size() <= 1) continue;

            Set<Elements> microChips = new HashSet<>();
            Set<Elements> generators = new HashSet<>();

            for (Device device : floor) {
                if (device.type().equals(Type.microchip))
                    microChips.add(device.element());
                else
                    generators.add(device.element());
            }

            if (generators.isEmpty())
                continue;

            if (!generators.containsAll(microChips))
                return false;
        }
        return true;
    }

    public void out(Object... str) {
        Util.out(str);
    }
}
