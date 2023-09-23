import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task {

    int counterTask1 = 0;
    int counterTask2 = 0;

    public void init() {
    }

    public void addLine(String input) {
        String[] phrases = input.split(" ");
        Set<String> set = new HashSet<>(List.of(phrases));
        if (phrases.length == set.size())
            counterTask1++;

        Set<String> set2 = new HashSet<>();
        for (String phrase : phrases) {
            set2.add(sortString(phrase));
        }
        if (phrases.length == set2.size())
            counterTask2++;
    }

    // To sort a string alphabetically
    public static String sortString(String inputString) {
        // Converting input string to character array
        char[] tempArray = inputString.toCharArray();

        // Sorting temp array using
        Arrays.sort(tempArray);

        // Returning new sorted string
        return new String(tempArray);
    }

    public void afterParse() {
        out(counterTask1);
        out(counterTask2);
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
