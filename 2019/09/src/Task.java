import computer.Computer;

import java.util.HashMap;
import java.util.Map;

public class Task {
    Map<Long, Long> programInitial = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        programInitial = Computer.parseProgram(input);
    }

    public void afterParse() throws InterruptedException {
        Computer computer = new Computer(programInitial, value -> out("Part 1", "Output", value));
        computer.addInput(1L);
        computer.exec();
        computer.join();

        computer = new Computer(programInitial, value -> out("Part 2", "Output", value));
        computer.addInput(2L);
        computer.exec();
        computer.join();
    }

    public void out(Object... str) {
        Util.out(str);
    }
}
