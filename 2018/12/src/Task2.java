import java.util.*;

public class Task2 {
    ArrayList<Boolean> list = new ArrayList<>();
    Map<String, Boolean> pattern2Result = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        if (input.trim().isEmpty()) return;
        if (input.startsWith("ini")) {
            String in = Util.cleanFrom(input, "initial state: ");
            for (int i = 0; i < in.length(); i++) {
                list.add(in.charAt(i) == '#');
            }
            out("initial state");
            out(listToString(list));
            return;
        }

        String[] split = input.trim().split(" => ");
        pattern2Result.put(split[0], split[1].equals("#"));
    }

    public void afterParse() {

        long startIdx = 0;

        ArrayList<Boolean> workList;
        int generation = 0;

        long prevNumPlantPods = 0;
        int sameNumCounter = 0;
// Annahme: es kommt zu einer konstanten Anzahl innerhalb der ersten 1000 Iterationen
        while (generation < 1000) {
            // Arbeitsliste erstellen - links und rechts 4x false dazu
            workList = new ArrayList<>();
            workList.add(false);
            workList.add(false);
            workList.add(false);
            workList.add(false);
            workList.addAll(list);
            workList.add(false);
            workList.add(false);
            workList.add(false);
            workList.add(false);

            startIdx -= 2;

            list.clear();
            for (int i = 2; i < workList.size() - 2; i++) {
                List<Boolean> subList = workList.subList(i - 2, i + 3);
                String key = listToString(subList);
                Boolean patternResult = pattern2Result.getOrDefault(key, false);
                list.add(patternResult);
            }
            Iterator<Boolean> it = list.iterator();
            while (it.hasNext()) {
                if (it.next()) break;
                it.remove();
                startIdx++;
            }
            ListIterator<Boolean> listIterator = list.listIterator(list.size());
            while (listIterator.hasPrevious()) {
                if (listIterator.previous()) break;
                listIterator.remove();
            }

            out("Gen", generation + 1, "StartIdx", startIdx, listToString(list));

            // Wiederholt sich die Anzahl bepflanzter Pods?
            long planted = list.stream().filter(i -> i).count();
            if (planted == prevNumPlantPods) {
                sameNumCounter++;
                if (sameNumCounter > 8) {
                    break; // Jetzt wiederholt sich immer alles - nur der Start Index waechst.
                }
            } else {
                sameNumCounter = 0;
                prevNumPlantPods = planted;
            }

            generation++;
        }

        long sumPlantPods = 0;

        // StartIdx erhoeht sich mit jeder Generation um 1
        startIdx += (50000000000L - generation - 1);

        long workIdx = startIdx;
        for (Boolean aBoolean : list) {
            if (aBoolean)
                sumPlantPods += workIdx;
            workIdx++;
        }
        out("sumPlantPods", sumPlantPods);
    }

    static String listToString(List<Boolean> list) {
        return String.join("", list.stream().map(b -> b ? "#" : ".").toList());
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
