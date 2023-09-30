import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
    Map<String, Long> registers = new HashMap<>();
    List<Instruction> instructions = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        instructions.add(new Instruction(input.split("\\s")));
    }

    public void afterParse() {
        out(instructions);
        long mulCounter = 0;
        int actIdx = 0;
        while (actIdx >= 0 && actIdx < instructions.size()) {
//            out("actIdx", actIdx);
//            out(registers);
            Instruction instruction = instructions.get(actIdx);
            String cmd = instruction.cmd();
            String register = instruction.register();

            if (cmd.equals("set")) {
                String y = instruction.value();
                Long yAsLong = tryParse(y);
                if (yAsLong != null)
                    registers.put(register, yAsLong);
                else
                    registers.put(register, registers.get(y));
            } else if (cmd.equals("sub")) {
                Long soFarValue = registers.getOrDefault(register, 0L);
                String y = instruction.value();
                Long yAsLong = tryParse(y);
                if (yAsLong != null)
                    registers.put(register, soFarValue - yAsLong);
                else
                    registers.put(register, soFarValue - registers.get(y));
            } else if (cmd.equals("mul")) {
                Long soFarValue = registers.getOrDefault(register, 0L);
                String y = instruction.value();
                Long yAsLong = tryParse(y);
                if (yAsLong != null)
                    registers.put(register, soFarValue * yAsLong);
                else
                    registers.put(register, soFarValue * registers.get(y));
                mulCounter++;
            } else if (cmd.equals("jnz")) {
                Long registerAsInt = tryParse(register);
                Long soFarValue = (registerAsInt != null ? registerAsInt : registers.getOrDefault(register, 0L));

                if (soFarValue != 0) {
                    String y = instruction.value();
                    Long yAsLong = tryParse(y);
                    Long offset = (yAsLong != null) ? yAsLong : registers.getOrDefault(y, 0L);

                    actIdx += offset;
                    continue;
                }
            } else throw new IllegalStateException("unknown cmd " + cmd);

            actIdx++;
        }

        out("Part 1 mulCounter", mulCounter);
    }

    public void out(Object... str) {
        Util.out(str);
    }

    private static Long tryParse(String val) {
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    private static class Instruction {
        final String[] instruction;

        private Instruction(String[] instruction) {
            this.instruction = instruction;
        }

        @Override
        public String toString() {
            return "[" + String.join(" ", instruction) + "]";
        }

        public String cmd() {
            return instruction[0];
        }

        public String register() {
            return instruction[1];
        }

        public String value() {
            return instruction[2];
        }
    }
}
