import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task2 {

    Map<Integer, Integer> cardCopies = new HashMap<>();
    long numCards = 0;

    public void init() {
    }

    public void addLine(String input) {
        String[] split1 = input.split(":");
        int cardNo = Integer.parseInt(split1[0].split("\\s+")[1]);
        String[] splitNumbers = split1[1].split("\\|");
        List<Integer> winningNumbers = Arrays.stream(splitNumbers[0].trim().split("\\s+")).mapToInt(s -> Integer.parseInt(s.trim())).boxed().toList();
        List<Integer> ownNumbers = Arrays.stream(splitNumbers[1].trim().split("\\s+")).mapToInt(s -> Integer.parseInt(s.trim())).boxed().toList();

        int copyCards = 0;
        for (Integer ownNumber : ownNumbers) {
            if (winningNumbers.contains(ownNumber)) {
                copyCards++;
            }
        }

        Integer numCopies = cardCopies.getOrDefault(cardNo, 1);
        numCards += numCopies;

        for (int i = 1; i <= copyCards; i++) {
            int key = cardNo + i;
            Integer soFarCopies = cardCopies.getOrDefault(key, 1);
            cardCopies.put(key, soFarCopies + numCopies);
        }

//        out(copyCards);
    }

    public void afterParse() {
        out("Part 2 - num cards", numCards);
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
