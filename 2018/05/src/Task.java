import java.util.HashSet;
import java.util.Set;

public class Task {
    String input;
    Set<String> reducers = new HashSet<>();

    public void init() {
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String c = String.valueOf(alphabet);
            String cUpper = c.toUpperCase();
            reducers.add(c + cUpper);
            reducers.add(cUpper + c);
        }
    }

    public void addLine(String input) {
        this.input = input.trim();
    }

    public void afterParse() {
        String work = input;
        work = evalReducers(reducers, work);
        out("Part 1", work.length());

        int shortessResult = Integer.MAX_VALUE;
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String c = String.valueOf(alphabet);
            String cUpper = c.toUpperCase();
            out(c);
            work = input.replaceAll(c, "").replaceAll(cUpper, "");
            String afterReduce = evalReducers(reducers, work);
            if (afterReduce.length() < shortessResult)
                shortessResult = afterReduce.length();
        }
        out("part 2 shortest result ", shortessResult);
    }

    private static String evalReducers(Set<String> reducers, String work) {
        boolean foundReduction = true;
        while (foundReduction) {
            foundReduction = false;
            for (String reducer : reducers) {
                String afterReplace = work.replaceAll(reducer, "");
                if (afterReplace.length() < work.length()) {
                    foundReduction = true;
                    work = afterReplace;
                }
            }
        }
        return work;
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
