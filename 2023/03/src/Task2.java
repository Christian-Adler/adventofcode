import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
    private final String regexNumbers = "(\\d+)";
    final String regexSymbols = "([^\\d.])";
    //    final String regexSymbols = "([^\\d ])";
    private final Pattern patternNumbers = Pattern.compile(regexNumbers, Pattern.MULTILINE);
    private final Pattern patternSymbols = Pattern.compile(regexSymbols, Pattern.MULTILINE);

    Map<Pos, String> pos2NumberStr = new HashMap<>();
    private final List<Pos> numbersPositionsOrder = new ArrayList<>();
    Map<Pos, Set<String>> symbol2Numbers = new HashMap<>();

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
                if (!symbol.equals("*"))
                    continue;
//                System.out.println(symbol);
                int foundAt = input.indexOf(symbol, actIdx);
                symbol2Numbers.put(new Pos(foundAt, actY), new HashSet<>());
                actIdx = foundAt + 1;
            }
        }

        actY++;
    }

    public void afterParse() {
//        out(pos2NumberStr);
//        out(pos2Symbol);

        // Find Numbers with adjacent symbols and sum them powered up

        for (Pos pos : numbersPositionsOrder) {
            String numberStr = pos2NumberStr.get(pos);


            for (int i = 0; i < numberStr.length(); i++) {
                Pos checkPos = pos.addToNew(i, 0);
                for (Pos adj : Pos.adjacent) {
                    Pos checkSymbolPos = checkPos.addToNew(adj);
                    if (symbol2Numbers.containsKey(checkSymbolPos)) {
                        symbol2Numbers.get(checkSymbolPos).add(numberStr);
                    }
                }
            }
        }

        long sum = 0;

        for (Set<String> strNumbers : symbol2Numbers.values()) {
            int size = strNumbers.size();
            if (size != 2) {
//                out("FOUND !=2 size " + size);
                continue;
            }

            List<String> list = new ArrayList<>(strNumbers);

            sum += Long.parseLong(list.get(0)) * Long.parseLong(list.get(1));
        }

        out("Part 2", "sum powers", sum); // > 395887 > 560663
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
