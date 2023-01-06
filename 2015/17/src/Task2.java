import java.util.*;

public class Task2 {
    List<Integer> containers = new ArrayList<>();

    Map<Integer, Integer> numContainers2NumPossibilities = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        containers.add(Integer.parseInt(input));
    }

    public void afterParse(int target) {
        Collections.sort(containers);
        Collections.reverse(containers);
        out(containers);
        int possibleCombinations = findCombinations(0, 0, target, 0);
        out(possibleCombinations);

        int min = numContainers2NumPossibilities.keySet().stream().mapToInt(k -> k).min().getAsInt();
        out(numContainers2NumPossibilities);
        out(min);
        out("possibilites for min:", numContainers2NumPossibilities.get(min));
    }

    int findCombinations(int idx, int soFarSum, int target, int numContainers) {
        int result = 0;

        if (soFarSum > target)
            return 0;
        if (soFarSum == target) {
            Integer soFarPossiblities = numContainers2NumPossibilities.get(numContainers);
            if (soFarPossiblities == null)
                soFarPossiblities = 0;
            numContainers2NumPossibilities.put(numContainers, soFarPossiblities + 1);
            return 1;
        }

        for (int i = idx; i < containers.size(); i++) {
            result += findCombinations(i + 1, soFarSum + containers.get(i), target, numContainers + 1);
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
