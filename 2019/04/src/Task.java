import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task {

    int min = 0;
    int max = 0;

    public void init() {
    }

    public void addLine(String input) {
        int[] split = Arrays.stream(input.split("-")).mapToInt(Integer::parseInt).toArray();
        min = split[0];
        max = split[1];
    }

    public void afterParse() {
        int numPasswords1 = 0;
        int numPasswords2 = 0;
        List<Integer> passwords1 = new ArrayList<>();
        List<Integer> passwords2 = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            if (isPasswordPart1(i)) {
                numPasswords1++;
                passwords1.add(i);
            }
            if (isPasswordPart2(i)) {
                numPasswords2++;
                passwords2.add(i);
            }
        }

        passwords1.removeAll(passwords2);

        out("Part 1", numPasswords1);
        out("Part 2", numPasswords2); // > 1191
    }

    private boolean isPasswordPart1(int val) {
        ArrayList<Integer> digits = getDigits(val);
        if (digits.size() != 6) return false;

        int prevD = -1;
        boolean hasSameAdjacentDigits = false;
        for (Integer digit : digits) {
            if (digit < prevD) return false;
            if (digit == prevD) {
                hasSameAdjacentDigits = true;
            }
            prevD = digit;
        }
        if (!hasSameAdjacentDigits)
            return false;

        return true;
    }

    private boolean isPasswordPart2(int val) {
        ArrayList<Integer> digits = getDigits(val);
        if (digits.size() != 6) return false;

        int prevD = -1;
        boolean hasSameAdjacentDigits = false;
        boolean sameAdjacentDigits = false;
        int sameAdjacentDigitsDigit = -1;
        for (Integer digit : digits) {
            if (digit < prevD) return false;
            if (digit == prevD) {
                if (sameAdjacentDigitsDigit == digit)
                    sameAdjacentDigits = false;
                else
                    sameAdjacentDigits = true;
                sameAdjacentDigitsDigit = digit;
            }
            if (digit > prevD && sameAdjacentDigits) // In der Mitte 2 gleiche?
                hasSameAdjacentDigits = true;
            prevD = digit;
        }
        if (sameAdjacentDigits) // Am Ende 2 gleiche?
            hasSameAdjacentDigits = true;
        
        if (!hasSameAdjacentDigits)
            return false;

        return true;
    }

    private ArrayList<Integer> getDigits(int val) {
        ArrayList<Integer> list = new ArrayList<>();
        int v = val;
        while (v > 0) {
            list.add(v % 10);
            v /= 10;
        }
        Collections.reverse(list);
        return list;
    }

    public void out(Object... str) {
        Util.out(str);
    }
}
