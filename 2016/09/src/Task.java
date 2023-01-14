import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Task {

    public void init() {
    }

    public void addLine(String input) {
        StringBuilder builder = new StringBuilder();
        int idx = 0;
        int idxBracket = input.indexOf("(", idx);
        if (idxBracket > 0)
            builder.append(input, idx, idxBracket);
        while (idxBracket >= 0) {
            int idxBracketClose = input.indexOf(")", idxBracket);
            List<Integer> parts = Arrays.stream(input.substring(idxBracket + 1, idxBracketClose).split("x")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            int numLetters = parts.get(0);
            int numRepeats = parts.get(1);
            String toRepeat = input.substring(idxBracketClose + 1, idxBracketClose + 1 + numLetters);
            builder.append(toRepeat.repeat(Math.max(0, numRepeats)));
            idx = idxBracketClose + 1 + numLetters;
            idxBracket = input.indexOf("(", idx);
            if (idxBracket > 0)
                builder.append(input, idx, idxBracket);
        }
        builder.append(input.substring(idx));

        out(builder.toString());
        out("length:", builder.toString().length());
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
