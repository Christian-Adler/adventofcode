import java.util.*;

public class Task {
    List<Integer> banks = null;
    Map<String, Integer> seenBefore = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        banks = new ArrayList<>(Arrays.stream(input.split("\\s")).mapToInt(Integer::parseInt).boxed().toList());
    }

    public void afterParse() {
        seenBefore.put(banks.toString(), 0);

        int banksSize = banks.size();

        int counter = 0;
        while (true) {
            counter++;

            ArrayList<Integer> sort = new ArrayList<>(banks);
            Collections.sort(sort);
            Collections.reverse(sort);

            Integer max = sort.get(0);
            int firstIdx = banks.indexOf(max);

            Integer steps = banks.get(firstIdx);
            banks.set(firstIdx, 0);

            for (int i = 1; i <= steps; i++) {
                int idx = (firstIdx + i) % banksSize;
                banks.set(idx, banks.get(idx) + 1);
            }

//            out(banks);

            Integer prevValue = seenBefore.put(banks.toString(), counter);
            if (prevValue != null) {
                out("size of loop", counter - prevValue);
                break;
            }

//            out(seenBefore);
        }

        out("counter", counter);

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
