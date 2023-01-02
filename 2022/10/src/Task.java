import java.util.ArrayList;
import java.util.List;

public class Task {
    ArrayList<Instruction> instructions = new ArrayList<>();

    private static class Instruction {
        boolean noop = false;
        int add = 0;

        public Instruction() {
            noop = true;
        }

        public Instruction(String input) {
            add = Integer.parseInt(input.split(" ")[1]);
        }

        @Override
        public String toString() {
            return noop ? "noop" : ("addx " + add);
        }
    }

    public void init() {

    }

    public void addLine(String input) {
        if (input.startsWith("no"))
            instructions.add(new Instruction());
        else instructions.add(new Instruction(input));
    }

    public void afterParse() {
        List<Instruction> workList = new ArrayList<>(instructions);
        Instruction instruction = null;
        int x = 1;
        int sumOfSignals = 0;
        for (int cycle = 0; cycle < 240; cycle++) {
//            out("Start/During of cycle " + cycle + " - X: " + x);

            if ((cycle - 20) % 40 == 0) {
                //out("# " + cycle + " Strength: " + (cycle * x));
                sumOfSignals += (cycle * x);
            }

            // New Line every 40 cycles
            if (cycle > 0 && cycle % 40 == 0) out("|");

            int pos = cycle % 40;
            if (x - 1 <= pos && x + 1 >= pos)
                ou("#");
            else
                ou(".");

            if (instruction != null) {
                x += instruction.add;

                instruction = null;
            } else if (!workList.isEmpty()) {
                instruction = workList.remove(0);
//                out(instruction);

                if (instruction.noop)
                    instruction = null;
            } else {
                break;
            }
//            out("End of cycle " + cycle + " - X: " + x);
        }
        out("|");
        out("Sum of signals strength: " + sumOfSignals);
    }


    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Instructions: ").append(instructions);

        return builder.toString();
    }
}
