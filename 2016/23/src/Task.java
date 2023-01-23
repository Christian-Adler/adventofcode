import java.util.*;

public class Task {
    Map<String, Long> registers = new HashMap<>();

    ArrayList<Instruction> instructions = new ArrayList<>();
    ArrayList<String> instructionStr = new ArrayList<>();
    int actInstructionIdx = 0;


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
        } else if (cmd.startsWith("tgl")) {

            instructions.add(() -> {
                if (printOuts) out(input);
                int offset = (int) get(x);

                int toggleIdx = actInstructionIdx + offset;

                if (toggleIdx < 0 || toggleIdx >= instructions.size()) {
                    actInstructionIdx++;
                    return;
                }

                Instruction soFarInstruction = instructions.get(toggleIdx);
                String instruction = instructionStr.get(toggleIdx);

                String[] tglParts = instruction.split(" ");
                String tglCmd = tglParts[0];
                String tglX = tglParts[1];
                String tglY = tglParts.length == 3 ? tglParts[2] : null;

                //one-argument
                if (tglY == null) {
                    if (tglCmd.equals("inc"))
                        tglCmd = "dec";
                    else
                        tglCmd = "inc";
                } else {
                    if (tglCmd.equals("jnz"))
                        tglCmd = "cpy";
                    else
                        tglCmd = "jnz";
                }

                // >>>
                String toggledInstruction = tglCmd + " " + tglX + (tglY != null ? " " + tglY : "");
                instructionStr.set(toggleIdx, toggledInstruction);

                if (tglCmd.startsWith("cpy")) {
                    instructions.set(toggleIdx, () -> {
                        if (printOuts) out(instruction, toggledInstruction);
                        Long value = null;
                        try {
                            value = Long.parseLong(tglX);
                        } catch (Exception ignored) {
                        }

                        set(tglY, Objects.requireNonNullElseGet(value, () -> get(tglX)));
                        actInstructionIdx++;
                    });
                } else if (tglCmd.startsWith("inc")) {
                    instructions.set(toggleIdx, () -> {
                        if (printOuts) out(instruction, toggledInstruction);
                        set(tglX, get(tglX) + 1);
                        actInstructionIdx++;
                    });
                } else if (tglCmd.startsWith("dec")) {
                    instructions.set(toggleIdx, () -> {
                        if (printOuts) out(instruction, toggledInstruction);
                        set(tglX, get(tglX) - 1);
                        actInstructionIdx++;
                    });
                } else if (tglCmd.startsWith("jnz")) {
                    if (tglY == null) throw new IllegalStateException("y = 0!!");

                    instructions.set(toggleIdx, () -> {
                        if (printOuts) out(instruction, toggledInstruction);
                        long value;
                        try {
                            value = Long.parseLong(tglX);
                        } catch (Exception ignored) {
                            value = get(tglX);
                        }
                        int tglOffset;
                        try {
                            tglOffset = Integer.parseInt(tglY);
                        } catch (Exception ignored) {
                            tglOffset = (int) get(tglY);
                        }
                        if (value != 0)
                            actInstructionIdx += tglOffset;
                        else
                            actInstructionIdx++;
                    });
                }
                // <<<

                actInstructionIdx++;
            });
        }
    }

    public void afterParse(long cStart) {
        registers.put("a", cStart);
        out(registers);
        long counter = 0;
        while (actInstructionIdx >= 0 && actInstructionIdx < instructions.size()) {
            counter++;
//            out("----", counter, " actIdx:", actInstructionIdx, ", instruction:", instructionStr.get(actInstructionIdx));
            if (counter % 1000 == 0)
                out(registers);
            instructions.get(actInstructionIdx).exec();
//            out(registers);
        }
        out("----");
        out(registers);
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
