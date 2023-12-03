import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {
    private final String regexNumbers = "(\\d+)";
    final String regexSymbols = "([^\\d.])";
    //    final String regexSymbols = "([^\\d ])";
    private final Pattern patternNumbers = Pattern.compile(regexNumbers, Pattern.MULTILINE);
    private final Pattern patternSymbols = Pattern.compile(regexSymbols, Pattern.MULTILINE);

    Map<Pos, String> pos2NumberStr = new HashMap<>();
    private final List<Pos> numbersPositionsOrder = new ArrayList<>();
    Map<Pos, String> pos2Symbol = new HashMap<>();

    private int actY = 0;

    public void init() {
    }

    public void addLine(String input) {
        int actIdx = 0;
        Matcher matcherNumbers = patternNumbers.matcher(input);

        while (matcherNumbers.find()) {
            for (int i = 1; i <= matcherNumbers.groupCount(); i++) {
                String numberStr = matcherNumbers.group(i);
//                System.out.println(numberStr);
                int foundAt = input.indexOf(numberStr, actIdx);
                Pos pos = new Pos(foundAt, actY);
                pos2NumberStr.put(pos, numberStr);
                numbersPositionsOrder.add(pos);
                actIdx = foundAt + numberStr.length();
            }
        }

        actIdx = 0;
        Matcher matcherSymbols = patternSymbols.matcher(input);

        while (matcherSymbols.find()) {
            for (int i = 1; i <= matcherSymbols.groupCount(); i++) {
                String symbol = matcherSymbols.group(i);
//                System.out.println(symbol);
                int foundAt = input.indexOf(symbol, actIdx);
                pos2Symbol.put(new Pos(foundAt, actY), symbol);
                actIdx = foundAt + 1;
            }
        }

        actY++;
    }

    public void afterParse() {
//        out(pos2NumberStr);
//        out(pos2Symbol);
        out(new HashSet<>(pos2Symbol.values()));

        // Find Numbers with adjacent symbols and sum them up
        long sum = 0;

        for (Pos pos : numbersPositionsOrder) {
            String numberStr = pos2NumberStr.get(pos);

            boolean foundAdjacentSymbol = false;

            for (int i = 0; i < numberStr.length(); i++) {
                if (foundAdjacentSymbol)
                    break;
                Pos checkPos = pos.addToNew(i, 0);
                for (Pos adj : Pos.adjacent) {
                    Pos checkSymbolPos = checkPos.addToNew(adj);
                    if (pos2Symbol.containsKey(checkSymbolPos)) {
                        foundAdjacentSymbol = true;
                        break;
                    }
                }
            }

//            out(numberStr, "found?", foundAdjacentSymbol);

            if (foundAdjacentSymbol)
                sum += Long.parseLong(numberStr);
        }


        out("Part 1", "sum", sum); // > 395887 > 560663
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
