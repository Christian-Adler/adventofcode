import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Task2 {
    ArrayList<Monkey> monkeys = new ArrayList<>();

    Monkey actParsing = null;

    private static class Monkey {
        int id = 0;
        List<Long> startingItems = new LinkedList<>();

        BiFunction<Long, Monkey, Long> operation = null;

        int operationValue = 0;

        int testValue = 0;
        int testTrueMonkeyId = -1;
        int testFalseMonkeyId = -1;


        public Monkey() {
        }

        @Override
        public String toString() {
            return "Monkey{" +
                    "id=" + id +
                    ", startingItems=" + startingItems +
                    ", operationValue=" + operationValue +
                    ", testValue=" + testValue +
                    ", testTrueMonkeyId=" + testTrueMonkeyId +
                    ", testFalseMonkeyId=" + testFalseMonkeyId +
                    '}';
        }
    }


    public void init() {

    }

    public void addLine(String input) {
        String in = input.trim();
        if (in.isEmpty()) return;

        if (in.startsWith("Monkey")) {
            actParsing = new Monkey();
            actParsing.id = monkeys.size();
            monkeys.add(actParsing);
        } else if (in.startsWith("Starting")) {
            actParsing.startingItems = new ArrayList<>(Arrays.stream(in.replace("Starting items: ", "").split(",")).map(String::trim).mapToLong(Long::parseLong).boxed().collect(Collectors.toList()));
        } else if (in.startsWith("Operation")) {
            boolean isAdd = false;
            boolean isIntValue = false;
            int opIdx = in.indexOf("+");
            if (opIdx > 0) isAdd = true;
            else
                opIdx = in.indexOf("*");

            String secondVal = in.substring(opIdx + 1);
            if (!secondVal.contains("old")) {
                isIntValue = true;
                actParsing.operationValue = Integer.parseInt(secondVal.trim());
            }

            if (isAdd) {
                if (isIntValue)
                    actParsing.operation = (opInput, m) -> opInput + m.operationValue;
                else
                    actParsing.operation = (opInput, m) -> opInput + opInput;
            } else {
                if (isIntValue)
                    actParsing.operation = (opInput, m) -> opInput * m.operationValue;
                else
                    actParsing.operation = (opInput, m) -> opInput * opInput;
            }

            out(actParsing.id + " - test op: " + actParsing.operation.apply(1L, actParsing));

        } else if (in.startsWith("Test:")) {
            actParsing.testValue = Integer.parseInt(in.replace("Test: divisible by", "").trim());
        } else if (in.startsWith("If true:")) {
            actParsing.testTrueMonkeyId = Integer.parseInt(in.replace("If true: throw to monkey", "").trim());
        } else if (in.startsWith("If false:")) {
            actParsing.testFalseMonkeyId = Integer.parseInt(in.replace("If false: throw to monkey", "").trim());
        }
    }

    public void afterParse() {
        out(toString());

        Map<Integer, Integer> inspectionTimes = new HashMap<>();

        int numRounds = 20;
        for (int i = 0; i < numRounds; i++) {

            for (Monkey monkey : monkeys) {
                inspectionTimes.compute(monkey.id, (k, v) -> ((v == null) ? 0 : v) + monkey.startingItems.size());
                while (!monkey.startingItems.isEmpty()) {
                    long worryLevel = monkey.startingItems.remove(0);
                    worryLevel = monkey.operation.apply(worryLevel, monkey);
                    worryLevel = Math.floorDiv(worryLevel, 3);
                    boolean testResult = worryLevel % monkey.testValue == 0;
                    int throwToMonkeyId = testResult ? monkey.testTrueMonkeyId : monkey.testFalseMonkeyId;
                    monkeys.get(throwToMonkeyId).startingItems.add(worryLevel);
                }
            }

        }

        printWorryLevels();
        out(inspectionTimes);
        List<Integer> sortedInspectionTimes = inspectionTimes.values().stream().sorted(Comparator.reverseOrder()).toList();
        out(sortedInspectionTimes);

        long monkeyBusiness = (long) sortedInspectionTimes.get(0) * (long) sortedInspectionTimes.get(1);
        out(monkeyBusiness);
    }


    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }

    void printWorryLevels() {
        for (Monkey monkey :
                monkeys) {
            out(monkey.id + ": " + monkey.startingItems);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Monkeys: ").append(monkeys);

        return builder.toString();
    }
}
