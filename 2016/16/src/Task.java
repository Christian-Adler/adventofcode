import java.util.ArrayList;
import java.util.Collections;

public class Task {

    public void init() {
    }

    public void addLine(String input) {
        String[] parts = input.split(":");
        int discSize = Integer.parseInt(parts[0]);
        String initialState = parts[1];
        ArrayList<Boolean> list = new ArrayList<>();
        for (int i = 0; i < initialState.length(); i++) {
            list.add(initialState.charAt(i) == '1');
        }
        out("discSize", discSize);
//        out(list);
        while (list.size() < discSize)
            list = dragon(list);
//        out(list);
        list = new ArrayList<>(list.subList(0, discSize));
//        out(list);

        ArrayList<Boolean> checkSum = buildChecksum(list);
//        out(checkSum);
        while (checkSum.size() % 2 == 0)
            checkSum = buildChecksum(checkSum);
        out("CheckSum:", toStr(checkSum));
    }

    ArrayList<Boolean> dragon(ArrayList<Boolean> a) {
        ArrayList<Boolean> b = new ArrayList<>(a);
        Collections.reverse(b);
        ArrayList<Boolean> result = new ArrayList<>(a);
        result.add(false);
        for (Boolean val : b) {
            result.add(!val);
        }
        return result;
    }

    ArrayList<Boolean> buildChecksum(ArrayList<Boolean> a) {
        if (a.size() % 2 != 0) throw new IllegalArgumentException("odd length of input!");

        ArrayList<Boolean> result = new ArrayList<>();
        boolean even = true;
        boolean prevVal = false;
        for (Boolean val : a) {
            if (even)
                prevVal = val;
            else
                result.add(prevVal == val);
            even = !even;
        }
        return result;
    }

    String toStr(ArrayList<Boolean> a) {
        StringBuilder b = new StringBuilder();

        for (Boolean val : a) {
            b.append(val ? "1" : "0");
        }

        return b.toString();
    }

    public void afterParse() {
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
