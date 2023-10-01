import java.util.HashMap;
import java.util.Map;

public class Task {

    int count2 = 0;
    int count3 = 0;

    public void init() {
    }

    public void addLine(String input) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : Util.str2List(input)) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }

        if (map.containsValue(3))
            count3++;
        if (map.containsValue(2))
            count2++;
    }

    public void afterParse() {
        out(count2, count3);
        out("checksum", count2 * count3);
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
