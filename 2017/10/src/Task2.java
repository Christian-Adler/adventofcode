import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task2 {

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
//            out(list);
        } else {
            char[] lengthsChars = input.toCharArray();
            for (char aChar : lengthsChars) {
                lengths.add((int) aChar);
            }
            // fix dazu
            lengths.addAll(Arrays.asList(17, 31, 73, 47, 23));
//            out(lengths);
        }
    }

    public void afterParse() {
        int skipSize = 0;
        int actIdx = 0;

        for (int rounds = 0; rounds < 64; rounds++) {

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
        }
        out(list);
//        int multFirst2 = list.get(0) * list.get(1);
//        out("mult first 2:", multFirst2);

        List<Integer> denseHash = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            List<Integer> subList = list.subList(i * 16, (i + 1) * 16);
//            out(subList);
            int dense = 0;
            for (Integer integer : subList) {
                dense = dense ^ integer;
            }
            denseHash.add(dense);
        }

        StringBuilder hex = new StringBuilder();
        for (Integer hash : denseHash) {
            String h = Integer.toHexString(hash);
            if (h.length() < 2) h = "0" + h;
            hex.append(h);
        }

        out("hex:", hex.toString());
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
