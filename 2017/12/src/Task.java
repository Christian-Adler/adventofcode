import java.util.*;

public class Task {

    Map<String, Set<String>> map = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split(" <-> ");
        String from = split[0];
        List<String> to = Arrays.asList(split[1].split(", "));

        Set<String> target = map.computeIfAbsent(from, k -> new HashSet<>());
        target.addAll(to);

        for (String toItem : to) {
            Set<String> fromTarget = map.computeIfAbsent(toItem, k -> new HashSet<>());
            fromTarget.add(from);
        }
    }

    public void afterParse() {
//        out(map);

        Set<String> resultSet = new HashSet<>();
        List<String> workList = new ArrayList<>();
        workList.add("0");

        while (!workList.isEmpty()) {
            String from = workList.remove(0);
            Set<String> targets = map.get(from);

            for (String target : targets) {
                if (resultSet.add(target))
                    workList.add(target);
            }
        }

//        out(resultSet);
        out("Programs in group 0:", resultSet.size());

        // Part 2
        // Einen aus Map nehmen und Gruppe ermitteln - alle aus der Gruppe aus Map entfernen
        // Wiederholen bis Map leer
        int groupsCount = 0;
        while (!map.isEmpty()) {
            groupsCount++;
            String startKey = map.keySet().iterator().next();

            resultSet = new HashSet<>();
            workList = new ArrayList<>();
            workList.add(startKey);

            while (!workList.isEmpty()) {
                String from = workList.remove(0);
                Set<String> targets = map.get(from);

                for (String target : targets) {
                    if (resultSet.add(target))
                        workList.add(target);
                }
            }

            for (String key : resultSet) {
                map.remove(key);
            }
        }
        out("groupsCount", groupsCount);
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
