import java.util.*;

public class Task {
    Map<String, Long> registers = new HashMap<>();

    ArrayList<Instruction> instructions = new ArrayList<>();
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
            int offset = Integer.parseInt(y);

            instructions.add(() -> {
                if (printOuts) out(input);
                long value;
                try {
                    value = Long.parseLong(x);
                } catch (Exception ignored) {
                    value = get(x);
                }
                if (value != 0)
                    actInstructionIdx += offset;
                else
                    actInstructionIdx++;
            });
        }
    }

    public void afterParse(long cStart) {
        registers.put("c", cStart);
        out(registers);
        long counter = 0;
        while (actInstructionIdx >= 0 && actInstructionIdx < instructions.size()) {
            counter++;
//            out("----", counter, " actIdx: ", actInstructionIdx);
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
