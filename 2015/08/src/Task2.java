import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {

    long sumCharactersOfCode = 0;
    long sumCharactersData = 0;

    long diffRK = 0;
    Pattern quotePattern = Pattern.compile("\"");
    Pattern backSlashPattern = Pattern.compile("\\\\");
    Pattern xCodePattern = Pattern.compile("\\\\x[a-f\\d][a-f\\d]");

    public void init() {
    }

    public void addLine(String input) {
        int charactersOfData = input.length();

        String rkIn = input;
        Matcher xcMatcher = xCodePattern.matcher(rkIn);
        int count = 0;
        while (xcMatcher.find()) {
            count++;
        }
        rkIn = xcMatcher.replaceAll("_");
        Matcher quoteMatcher = quotePattern.matcher(rkIn);
        Matcher backMatcher = backSlashPattern.matcher(rkIn);
        while (quoteMatcher.find()) {
            count++;
        }
        while (backMatcher.find()) {
            count++;
        }

        diffRK += count + 2;

        String work = input;

        work = work.replaceAll("\\\\x[a-f\\d][a-f\\d]", "#####");

        int idx = -1;
        // \"
        idx = work.indexOf("\"");
        while (idx >= 0) {
            work = work.substring(0, idx) + "~~" + work.substring(idx + 1);
            idx = work.indexOf("\"");
        }
        // \\
        idx = work.indexOf("\\");
        while (idx >= 0) {
            work = work.substring(0, idx) + "__" + work.substring(idx + 1);
            idx = work.indexOf("\\");
        }

        int charactersOfCode = work.length() + 2; // +2 wg umschliessenden Anfuehrungszeichen

        sumCharactersOfCode += charactersOfCode;
        sumCharactersData += charactersOfData;
    }

    public void afterParse() {
        out("sumCharactersOfCode:", sumCharactersOfCode);
        out("sumCharactersData:", sumCharactersData);

        long diff = sumCharactersOfCode - sumCharactersData;
        out("diff", diff);
        out("diffRK", diffRK);
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

}
