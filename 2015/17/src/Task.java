import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task {
    List<Integer> containers = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        containers.add(Integer.parseInt(input));
    }

    public void afterParse(int target) {
        Collections.sort(containers);
        Collections.reverse(containers);
        out(containers);
        int possibleCombinations = findCombinations(0, 0, target);
        out(possibleCombinations);
    }

    int findCombinations(int idx, int soFarSum, int target) {
        int result = 0;

        if (soFarSum > target)
            return 0;
        if (soFarSum == target) return 1;

        for (int i = idx; i < containers.size(); i++) {
            result += findCombinations(i + 1, soFarSum + containers.get(i), target);
        }

        return result;
    }

    public void out(Object... str) {
        String out = "";
        for (Object o : str) {
            if (out.length() > 0)
                out += " ";
            out += o;
        }
        System.out.println(out);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            result = result.replace(string, "");
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
