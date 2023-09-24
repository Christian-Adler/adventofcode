import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task {

    List<Integer> list = new ArrayList<>();
    List<Integer> lengths = new ArrayList<>();


    public void init() {
    }

    public void addLine(String input) {
        if (list.isEmpty()) {
            int fillUpTo = Integer.parseInt(input);
            for (int i = 0; i <= fillUpTo; i++) {
                list.add(i);
            }
            out(list);
        } else {
            lengths = Arrays.stream(input.split(",")).map(String::trim).mapToInt(Integer::parseInt).boxed().toList();
            out(lengths);
        }
    }

    public void afterParse() {
        int skipSize = 0;
        int actIdx = 0;

        for (Integer length : lengths) {
            List<Integer> subList = getSubList(actIdx, length);
//            out(subList);
            Collections.reverse(subList);
//            out(subList);

            fillToList(actIdx, subList);
//            out(list);

            actIdx = (actIdx + length + skipSize) % list.size();
            skipSize++;
        }

        int multFirst2 = list.get(0) * list.get(1);
        out("mult first 2:", multFirst2);
    }

    private List<Integer> getSubList(int startIdx, int length) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            int idx = (startIdx + i) % list.size();
            result.add(list.get(idx));
        }

        return result;
    }

    private void fillToList(int startIdx, List<Integer> fills) {
        for (int i = 0; i < fills.size(); i++) {
            int idx = (startIdx + i) % list.size();
            list.set(idx, fills.get(i));
        }
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
