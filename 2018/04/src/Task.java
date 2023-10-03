import java.util.*;

public class Task {
    List<String> records = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        records.add(input);
    }

    public void afterParse() {
        Collections.sort(records);

//        out(records);

        Map<Integer, Integer> guard2SleepTime = new HashMap<>();

        int actGuard = -1;
        int startMinute = -1;

        for (String record : records) {
            String[] split = record.split("\\s");
            if (record.contains("begins shift")) {
                actGuard = Integer.parseInt(split[3].replace("#", ""));
            } else { // falls asleep || wakes up
                int minuteValue = Integer.parseInt(split[1].split(":")[1].replace("]", ""));
                if (record.contains("falls asleep"))
                    startMinute = minuteValue;
                else {
                    int sleepTime = minuteValue - startMinute;
                    guard2SleepTime.put(actGuard, guard2SleepTime.getOrDefault(actGuard, 0) + sleepTime);
                }
            }
        }

//        out(guard2SleepTime);
        int maxSleepTime = 0;
        int maxSleepGuard = 0;
        for (Map.Entry<Integer, Integer> entry : guard2SleepTime.entrySet()) {
            int guard = entry.getKey();
            int sleepTime = entry.getValue();
            if (sleepTime > maxSleepTime) {
                maxSleepTime = sleepTime;
                maxSleepGuard = guard;
            }
        }

        out("maxSleepGuardId", maxSleepGuard);
        out("maxSleepTime", maxSleepTime);

        Map<Integer, Integer> minute2sleepingCount = new HashMap<>();

        actGuard = -1;
        for (String record : records) {
            String[] split = record.split("\\s");
            if (record.contains("begins shift")) {
                actGuard = Integer.parseInt(split[3].replace("#", ""));
            } else if (actGuard == maxSleepGuard) { // falls asleep || wakes up
                int minuteValue = Integer.parseInt(split[1].split(":")[1].replace("]", ""));
                if (record.contains("falls asleep"))
                    startMinute = minuteValue;
                else {
                    for (int m = startMinute; m < minuteValue; m++) {
                        minute2sleepingCount.put(m, minute2sleepingCount.getOrDefault(m, 0) + 1);
                    }
                }
            }
        }

        int maxSleepPerMinute = 0;
        int maxSleepMinute = 0;
        for (Map.Entry<Integer, Integer> entry : minute2sleepingCount.entrySet()) {
            int minute = entry.getKey();
            int sleepCounter = entry.getValue();
            if (sleepCounter > maxSleepPerMinute) {
                maxSleepPerMinute = sleepCounter;
                maxSleepMinute = minute;
            }
        }

        out("maxSleepPerMinute", maxSleepPerMinute);
        out("maxSleepMinute", maxSleepMinute);

        out("Part 1 GuardId*maxSleepPerMin :", maxSleepGuard * maxSleepMinute);

        // Part 2
        Map<Integer, Map<Integer, Integer>> guard2minute2sleepingCount = new HashMap<>();

        actGuard = -1;
        Map<Integer, Integer> guardsMinute2sleepingCount = null;
        for (String record : records) {
            String[] split = record.split("\\s");
            if (record.contains("begins shift")) {
                actGuard = Integer.parseInt(split[3].replace("#", ""));
                guardsMinute2sleepingCount = guard2minute2sleepingCount.computeIfAbsent(actGuard, k -> new HashMap<>());
            } else { // falls asleep || wakes up
                int minuteValue = Integer.parseInt(split[1].split(":")[1].replace("]", ""));
                if (record.contains("falls asleep"))
                    startMinute = minuteValue;
                else {
                    for (int m = startMinute; m < minuteValue; m++) {
                        if (guardsMinute2sleepingCount == null) throw new IllegalStateException("Null map");
                        guardsMinute2sleepingCount.put(m, guardsMinute2sleepingCount.getOrDefault(m, 0) + 1);
                    }
                }
            }
        }

        maxSleepPerMinute = 0;
        maxSleepMinute = 0;
        int maxSleepPerMinuteGuard = 0;
        for (Map.Entry<Integer, Map<Integer, Integer>> guardEntry : guard2minute2sleepingCount.entrySet()) {
            Integer guardId = guardEntry.getKey();
            for (Map.Entry<Integer, Integer> entry : guardEntry.getValue().entrySet()) {
                int minute = entry.getKey();
                int sleepCounter = entry.getValue();
                if (sleepCounter > maxSleepPerMinute) {
                    maxSleepPerMinute = sleepCounter;
                    maxSleepMinute = minute;
                    maxSleepPerMinuteGuard = guardId;
                }
            }
        }

        out("maxSleepPerMinute", maxSleepPerMinute);
        out("maxSleepMinute", maxSleepMinute);
        out("maxSleepPerMinuteGuard", maxSleepPerMinuteGuard);

        out("Part 2 GuardId*maxSleepPerMin :", maxSleepPerMinuteGuard * maxSleepMinute);
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
