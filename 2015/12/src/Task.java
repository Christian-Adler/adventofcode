import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {
    Pattern quotePattern = Pattern.compile("\"\\w+\"");
    Pattern digitPattern = Pattern.compile("-?\\d+");
    public void init() {
    }

    public void addLine(String input) {
        String inp = input;
        Matcher quoteMatcher = quotePattern.matcher(inp);
        inp = quoteMatcher.replaceAll("_");

        long sum = 0;
        Matcher digitMatcher = digitPattern.matcher(inp);
        while(digitMatcher.find()){
            String grp = digitMatcher.group();
            long val = Long.parseLong(grp);
            sum+=val;
        }

        out(sum);
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
