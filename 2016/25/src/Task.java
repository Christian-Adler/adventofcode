import java.util.*;

public class Task {
    Map<String, Long> registers = new HashMap<>();

    ArrayList<Instruction> instructions = new ArrayList<>();
    ArrayList<String> instructionStr = new ArrayList<>();
    int actInstructionIdx = 0;
    boolean toggler = true;
    int toggleCounter = 0;
    boolean oneMiss = false;

    public void init() {
        for (String regName : Arrays.asList("a", "b", "c", "d")) {
            registers.put(regName, 0L);
        }
    }

    long get(String register) {
        return registers.get(register);
    }

    void set(String register, long value) {
        registers.put(register, value);
    }

    public void addLine(String input) {
        instructionStr.add(input);
        boolean printOuts = false;
        String[] parts = input.split(" ");
        String cmd = parts[0];
        String x = parts[1];
        String y = parts.length == 3 ? parts[2] : null;
        if (cmd.startsWith("cpy")) {
            instructions.add(() -> {
                if (printOuts) out(input);
                Long value = null;
                try {
                    value = Long.parseLong(x);
                } catch (Exception ignored) {
                }

                set(y, Objects.requireNonNullElseGet(value, () -> get(x)));
                actInstructionIdx++;
            });
        } else if (cmd.startsWith("inc")) {
            instructions.add(() -> {
                if (printOuts) out(input);
                set(x, get(x) + 1);
                actInstructionIdx++;
            });
        } else if (cmd.startsWith("dec")) {
            instructions.add(() -> {
                if (printOuts) out(input);
                set(x, get(x) - 1);
                actInstructionIdx++;
            });
        } else if (cmd.startsWith("jnz")) {
            if (y == null) throw new IllegalStateException("y = 0!!");

            instructions.add(() -> {
                if (printOuts) out(input);
                long value;
                try {
                    value = Long.parseLong(x);
                } catch (Exception ignored) {
                    value = get(x);
                }
                int offset;
                try {
                    offset = Integer.parseInt(y);
                } catch (Exception ignored) {
                    offset = (int) get(y);
                }
                if (value != 0)
                    actInstructionIdx += offset;
                else
                    actInstructionIdx++;
            });
        } else if (cmd.startsWith("out")) {

            instructions.add(() -> {
                if (printOuts) out(input);
                long value;
                try {
                    value = Long.parseLong(x);
                } catch (Exception ignored) {
                    value = get(x);
                }

                out("out", value);
                if (value < 0 || value > 1) {
                    toggleCounter = -1;
                    return;
                }
                if (toggleCounter == 0) { // beim ersten Auftreten den Toggler setzen
                    toggler = (value == 1);
                    toggleCounter++;
                } else if (value == 0 && toggler || value == 1 && !toggler) {
                    toggler = !toggler;
                    toggleCounter++;
                } else {
                    toggleCounter = -1;
                }

                actInstructionIdx++;
            });
        }
    }

    public void afterParse() {
        long testValue = 0L;
        while (true) {
            out("testValue", testValue);
            init();
            registers.put("a", testValue);
            actInstructionIdx = 0;
            toggler = true;
            oneMiss = false;
            toggleCounter = 0;
//            out(registers);
            long counter = 0;
            while (actInstructionIdx >= 0 && actInstructionIdx < instructions.size() && toggleCounter >= 0 && toggleCounter < 10) {
                counter++;
//            out("----", counter, " actIdx:", actInstructionIdx, ", instruction:", instructionStr.get(actInstructionIdx));
//                out(counter, instructionStr.get(actInstructionIdx));
//                if (counter % 1000 == 0)
//                    out(registers);
                instructions.get(actInstructionIdx).exec();
//                out(registers);
            }

//            out(registers);
            if (toggleCounter < 0)
                out("xxx");
            else {
                out("found start:", testValue); // 341,682 to high
                break;
            }
            out("----");
            testValue++;

        }
    }

    public void out(Object... str) {
        StringBuilder out = new StringBuilder();
        for (Object o : str) {
            if (out.length() > 0)
                out.append(" ");
            out.append(o);
        }
        System.out.println(out);
    }

    @FunctionalInterface
    private interface Instruction {
        void exec();
    }

    @Override
    public String toString() {
        return "";
    }

}
