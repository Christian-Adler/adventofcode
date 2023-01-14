import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {

    final String regex = "\\(\\d+x\\d+\\)";

    final Pattern pattern = Pattern.compile(regex);


    public void init() {
    }

    public void addLine(String input) {
        // Liste von ersetzungsregeln + index
        // nach jeder ersetzun

        long decompressed = 0;
        StringBuilder compressed = new StringBuilder(input);


        long turns = 0;
        int idxBracket = input.indexOf("(");
        while (idxBracket >= 0) {
            turns++;
            if (turns % 10000 == 0) {
                final Matcher matcher = pattern.matcher(compressed);
                long counter = 0;
                while (matcher.find()) {
                    counter++;
                }
                out("markers:", counter, "decompressed:", decompressed);
            }

            decompressed += idxBracket;
            compressed = new StringBuilder(compressed.substring(idxBracket));
            int idxBracketClose = compressed.indexOf(")");
            List<Integer> parts = Arrays.stream(compressed.substring(1, idxBracketClose).split("x")).mapToInt(Integer::parseInt).boxed().toList();
            int numLetters = parts.get(0);
            int numRepeats = parts.get(1);

            compressed = new StringBuilder(compressed.substring(idxBracketClose + 1));
            String toRepeat = compressed.substring(0, numLetters);
            String rest = compressed.substring(numLetters);
            compressed = new StringBuilder();
            compressed.append(toRepeat.repeat(Math.max(0, numRepeats)));
            compressed.append(rest);

            idxBracket = compressed.indexOf("(");

        }
        decompressed += compressed.length();

        out(input, ">", decompressed);
        out("decompressed >", decompressed);
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
