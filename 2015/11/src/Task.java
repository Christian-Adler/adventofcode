import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Task {
    int xMin = 0;
    int xMax = 10;
    int yMin = 0;
    int yMax = 10;

    public void init() {
    }

    public void addLine(String input) {
        out(input);

        String password = input;
        while (!checkIncreasingStraight(password) || !checkTwoPairs(password) || !checkNoInvalidCharacters(password)) {
            password = increase(password);
        }
        out(password);

        password = increase(password);
        while (!checkIncreasingStraight(password) || !checkTwoPairs(password) || !checkNoInvalidCharacters(password)) {
            password = increase(password);
        }
        out(password);
    }

    boolean checkNoInvalidCharacters(String input) {
        return !input.contains("i") && !input.contains("l") && !input.contains("o");
    }

    boolean checkIncreasingStraight(String input) {
        if (input.length() < 3) return false;
        char[] chars = input.toCharArray();
        char c0 = chars[0];
        char c1 = chars[1];
        for (int i = 2; i < chars.length; i++) {
            char c = chars[i];
            if (c1 == c0 + 1 && c == c1 + 1)
                return true;
            c0 = c1;
            c1 = c;
        }
        return false;
    }

    boolean checkTwoPairs(String input) {
        if (input.length() < 4) return false;
        int foundPairs = 0;
        char[] chars = input.toCharArray();
        char c0 = chars[0];
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (c == c0) {
                foundPairs++;
                c0 = '1';

                if (foundPairs >= 2)
                    return true;
                continue;
            }
            c0 = c;
        }
        return foundPairs >= 2;
    }

    String increase(String input) {
        ArrayList<String> list = Util.str2List(input);
        Collections.reverse(list); // umdrehen damit von vorne gearbeitet werden kann
        boolean overflow = true;
        int idx = -1;
        while (overflow) {
            idx++;
            if (idx == list.size()) {
                list.add("a");
                break;
            }
            IncreasedLetter increasedLetter = increaseLetter(list.get(idx));
            overflow = increasedLetter.overflow;
            list.set(idx, increasedLetter.letter);
        }
        Collections.reverse(list);
        return list.stream().collect(Collectors.joining(""));
    }

    IncreasedLetter increaseLetter(String letter) {
        char c = letter.charAt(0);
        if (c == 'z')
            return new IncreasedLetter("a", true);
        c++;
        while (c == 'i' || c == 'l' || c == 'o') c++;
        String s = String.valueOf(c);
        return new IncreasedLetter(s, false);
    }

    record IncreasedLetter(String letter, boolean overflow) {
    }

    public void afterParse() {
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
