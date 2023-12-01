import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Task {
    private int sum = 0;

    Map<String, String> mapping = new HashMap<>();

    public void init() {
        mapping.put("one", "1");
        mapping.put("two", "2");
        mapping.put("three", "3");
        mapping.put("four", "4");
        mapping.put("five", "5");
        mapping.put("six", "6");
        mapping.put("seven", "7");
        mapping.put("eight", "8");
        mapping.put("nine", "9");
    }

    public void addLine(String input) {
        int smallestIdx = Integer.MAX_VALUE;
        String smallestStrNumber = null;
        int largestIdx = -1;
        String largestStrNumber = null;
        for (String key : mapping.keySet()) {
            int idx = input.indexOf(key);
            if (idx >= 0 && idx < smallestIdx) {
                smallestIdx = idx;
                smallestStrNumber = mapping.get(key);
            }
            idx = input.lastIndexOf(key);
            if (idx >= 0 && idx > largestIdx) {
                largestIdx = idx;
                largestStrNumber = mapping.get(key);
            }
        }

        if (largestIdx >= 0)
            input = input.substring(0, largestIdx) + largestStrNumber + input.substring(largestIdx);
        if (smallestIdx < Integer.MAX_VALUE)
            input = input.substring(0, smallestIdx) + smallestStrNumber + input.substring(smallestIdx);

        LinkedList<Integer> ints = new LinkedList<>(Util.str2List(input).stream().map(s -> {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException nfe) {
                return -1;
            }
        }).filter(i -> i > 0).toList());
        int calibrationValue = ints.getFirst() * 10 + ints.getLast();
        sum += calibrationValue;
    }

    public void afterParse() {
        out("Part 1", sum);
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
