import java.util.HashMap;
import java.util.Map;

public class Registers {
    public static Map<String, Integer> registers = new HashMap<>();
    public static int allTimeMax = Integer.MIN_VALUE;

    public static int get(String register) {
        return registers.getOrDefault(register, 0);
    }

    public static void add(String register, int value) {
        Integer soFar = registers.getOrDefault(register, 0);
        int newVal = soFar + value;
        if (newVal > allTimeMax)
            allTimeMax = newVal;
        registers.put(register, newVal);
    }

    public static String toStr() {
        return registers.toString();
    }

    public static void clear() {
        registers.clear();
        allTimeMax = Integer.MIN_VALUE;
    }

    public static int largestValue() {
        return registers.values().stream().max(Integer::compare).orElse(0);
    }
}
