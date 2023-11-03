import computer.Computer;
import computer.instructions.Add;
import computer.instructions.Mult;

import java.util.Arrays;

public class Task {
    int[] programInitial = new int[0];

    public void init() {
        Computer.addInstruction(new Add());
        Computer.addInstruction(new Mult());
    }

    public void addLine(String input) {
        programInitial = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public void afterParse() {


        Computer computer = new Computer(programInitial);
        computer.setProgramValue(1, 12);
        computer.setProgramValue(2, 2);
        out(computer);
        computer.exec();

        out(computer);
        out("Part 1:", computer.getOutput());

        // part 2 - brute force
        // noun macht die grossen Zahlen - verb zaehlt hinten hoch
        int noun = -1;
        int verb = -1;
        for (int n = 0; n < 100; n++) {
//            out("\r\n n:", n);
            for (int v = 0; v < 100; v++) {
                computer = new Computer(programInitial);
                computer.setProgramValue(1, n);
                computer.setProgramValue(2, v);
//                out(computer);
                computer.exec();
                int res = computer.getOutput();
//                if (String.valueOf(res).endsWith("20")) out("Ends with 20 at v", v);
                if (res == 19690720) {
                    out("Found it", n, v);
                    noun = n;
                    verb = v;
                }
//                out("Part 2 ? ", res);
            }
        }

        out("Part 2:", 100 * noun + verb);
    }

    public void out(Object... str) {
        Util.out(str);
    }

}
