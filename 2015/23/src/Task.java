import java.util.ArrayList;

public class Task {
    long a = 0;
    long b = 0;

    ArrayList<Instruction> instructions = new ArrayList<>();
    int actInstructionIdx = 0;


    public void init(int aStart) {
        a = aStart;
    }

    long get(String register) {
        if (register.equals("a"))
            return a;
        return b;
    }

    void set(String register, long value) {
        long val = value;
        if (val < 0) {
            val = 0;
        }
        if (register.equals("a"))
            a = val;
        else b = val;
    }

    public void addLine(String input) {
        String register = input.substring(4, 5);
        if (input.startsWith("hlf")) {
            instructions.add(() -> {
                out(input);
                set(register, get(register) / 2);
                actInstructionIdx++;
            });
        } else if (input.startsWith("tpl")) {
            instructions.add(() -> {
                out(input);
                set(register, get(register) * 3);
                actInstructionIdx++;
            });
        } else if (input.startsWith("inc")) {
            instructions.add(() -> {
                out(input);
                set(register, get(register) + 1);
                actInstructionIdx++;
            });
        } else if (input.startsWith("jmp")) {
            int offset = Integer.parseInt(input.substring(4));
            instructions.add(() -> {
                out(input);
                actInstructionIdx += offset;
            });
        } else if (input.startsWith("jie")) {
            int offset = Integer.parseInt(input.substring(7));
            instructions.add(() -> {
                out(input);
                if (get(register) % 2 == 0)
                    actInstructionIdx += offset;
                else
                    actInstructionIdx++;
            });
        } else if (input.startsWith("jio")) {
            int offset = Integer.parseInt(input.substring(7));
            instructions.add(() -> {
                out(input);
                if (get(register) == 1)
                    actInstructionIdx += offset;
                else
                    actInstructionIdx++;
            });
        }
    }

    public void afterParse() {
        out("a:", a);
        out("b:", b);
        long counter = 0;
        while (actInstructionIdx >= 0 && actInstructionIdx < instructions.size()) {
            counter++;
            out("----", counter, " actIdx: ", actInstructionIdx);
            instructions.get(actInstructionIdx).exec();
            out("a:", a);
            out("b:", b);
        }
        out("----");
        out("a:", a);
        out("b:", b);

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
    private static interface Instruction {
        void exec();
    }

    @Override
    public String toString() {
        return "";
    }

}
