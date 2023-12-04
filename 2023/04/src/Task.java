import java.util.Arrays;
import java.util.List;

public class Task {
    long sumPoints = 0;

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split(":")[1].split("\\|");
        List<Integer> winningNumbers = Arrays.stream(split[0].trim().split("\\s+")).mapToInt(s -> Integer.parseInt(s.trim())).boxed().toList();
        List<Integer> ownNumbers = Arrays.stream(split[1].trim().split("\\s+")).mapToInt(s -> Integer.parseInt(s.trim())).boxed().toList();

        int points = 0;
        for (Integer ownNumber : ownNumbers) {
            if (winningNumbers.contains(ownNumber)) {
                if (points == 0)
                    points = 1;
                else
                    points *= 2;
            }
        }

//        out(points);
        sumPoints += points;
    }

    public void afterParse() {
        out("Part 1", sumPoints);
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
