import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Task {

    long sum = 0;

    public void init() {
    }

    public void addLine(String input) {
        long val = convertFromSnafuToBase10(input);
        sum += val;
    }

    public void afterParse() {
        out("Sum " + sum);
        out("SNAFU: " + convertFromBase10ToSnafu(sum));
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 2022, 123456, 314159265);
//        for (Integer integer : list) {
//            out(integer);
//            String test = convertFromBase10ToSnafu(integer);
//            out(test);
//            long testL = convertFromSnafuToBase10(test);
//            out(testL);
//            out("------------");
//        }
    }

    public String convertFromBase10ToSnafu(long input) {
        String base5 = Long.toString(input, 5);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < base5.length(); i++) {
            list.add("" + base5.charAt(i));
        }

        // reverse
        Collections.reverse(list);

        List<String> resultList = new ArrayList<>();

        boolean uebertrag = false;

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            int val = Integer.parseInt(s);
            if (uebertrag)
                val++;

            if (val == 3) {
                resultList.add("=");
                uebertrag = true;
            } else if (val == 4) {
                resultList.add("-");
                uebertrag = true;
            } else if (val == 5) {
                resultList.add("0");
                uebertrag = true;
            } else {
                resultList.add(String.valueOf(val));
                uebertrag = false;
            }
        }
        if (uebertrag)
            resultList.add("1");

        Collections.reverse(resultList);

        String res = resultList.stream().collect(Collectors.joining());

        return res;
    }

    public long convertFromSnafuToBase10(String inp) {

        long result = 0;

        String input = reverseString(inp);

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '1') {
                long add = (long) Math.pow(5, i);
                result += add;
            } else if (c == '2') {
                long add = 2 * (long) Math.pow(5, i);
                result += add;
            } else if (c == '-') {
                long add = (long) Math.pow(5, i);
                result -= add;
            } else if (c == '=') {
                long add = 2 * (long) Math.pow(5, i);
                result -= add;
            }
        }
        return result;
    }

    public static String reverseString(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }

    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            input = input.replace(string, "");
        }
        return input;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {


        StringBuilder builder = new StringBuilder();


        return builder.toString();
    }
}
