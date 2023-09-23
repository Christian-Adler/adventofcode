import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task {

    long checkSum = 0;
    long checkSum2 = 0;

    public void init() {
    }

    public void addLine(String input) {
        List<Integer> sortedInts = new ArrayList<>(Arrays.stream(input.split("\\s+")).mapToInt(Integer::parseInt).sorted().boxed().toList());
        int min = sortedInts.get(0);
        int max = sortedInts.get(sortedInts.size() - 1);
        int diff = max - min;
        checkSum += diff;

        Collections.reverse(sortedInts);

        boolean foundEvenDiv = false;
        for (int i = 0; i < sortedInts.size() - 1; i++) {
            int biggerVal = sortedInts.get(i);
            for (int i2 = i + 1; i2 < sortedInts.size(); i2++) {
                int smallerVal = sortedInts.get(i2);
                if (biggerVal % smallerVal == 0) {
                    checkSum2 += biggerVal / smallerVal;
                    foundEvenDiv = true;
                    break;
                }
            }
            if (foundEvenDiv) break;
        }
    }

    public void afterParse() {
        out("CheckSum 1", checkSum);

        out("CheckSum 2", checkSum2);
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
