import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task {
    Set<Integer> tape = new HashSet<>();

    Map<String, State> states = new HashMap<>();
    String startStateShouldBe = "";
    int checksumAfterSteps = 0;
    State currentParsingState = null;
    boolean isCurrentValue0 = true;

    public void init() {
    }

    public void addLine(String input) {
        if (input.trim().isEmpty()) return;

        if (input.contains("Begin in state"))
            startStateShouldBe = input.substring(15, 16);
        else if (input.contains("Perform a diagnostic"))
            checksumAfterSteps = Integer.parseInt(input.replace("Perform a diagnostic checksum after", "").replace("steps.", "").trim());
        else if (input.contains("In state")) {
            currentParsingState = new State(input.substring(9, 10));
            states.put(currentParsingState.state, currentParsingState);
        } else if (input.contains("If the current value is")) {
            isCurrentValue0 = input.contains("0");
        } else if (input.contains("-")) {
            Instruction instruction = isCurrentValue0 ? currentParsingState.instruction0 : currentParsingState.instruction1;
            if (input.contains("Write the value"))
                instruction.writeValue = input.contains("1");
            else if (input.contains("Move one slot"))
                instruction.move = input.contains("left") ? -1 : 1;
            else if (input.contains("Continue with"))
                instruction.nextState = input.trim().substring(22, 23);
        }
    }

    public void afterParse() {
//        out("startStateShouldBe", startStateShouldBe);

//        out(states);


        int actIdx = 0;

        State actState = states.get(startStateShouldBe);
        for (int i = 0; i < checksumAfterSteps; i++) {
            int currentValue = tape.contains(actIdx) ? 1 : 0;
            Instruction instruction = currentValue == 0 ? actState.instruction0 : actState.instruction1;

            if (instruction.writeValue)
                tape.add(actIdx);
            else tape.remove(actIdx);

            actIdx += instruction.move;

            actState = states.get(instruction.nextState);
        }

        out("diagnostic checksum", tape.size());
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
