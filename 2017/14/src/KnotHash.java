import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KnotHash {
    List<Integer> list = new ArrayList<>();
    List<Integer> lengths = new ArrayList<>();


    public KnotHash(String input) {

        int fillUpTo = 256;
        for (int i = 0; i < fillUpTo; i++) {
            list.add(i);
        }

        char[] lengthsChars = input.toCharArray();
        for (char aChar : lengthsChars) {
            lengths.add((int) aChar);
        }
//            // fix dazu
        lengths.addAll(Arrays.asList(17, 31, 73, 47, 23));
    }

    public String calcHexHash() {
        int skipSize = 0;
        int actIdx = 0;

        for (int rounds = 0; rounds < 64; rounds++) {

            for (Integer length : lengths) {
                List<Integer> subList = getSubList(actIdx, length);
                Collections.reverse(subList);

                fillToList(actIdx, subList);

                actIdx = (actIdx + length + skipSize) % list.size();
                skipSize++;
            }
        }

        List<Integer> denseHash = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            List<Integer> subList = list.subList(i * 16, (i + 1) * 16);
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

        return hex.toString();
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
}
