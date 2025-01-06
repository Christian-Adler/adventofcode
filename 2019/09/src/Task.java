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

  public void afterParse_Day17() throws InterruptedException {
    StringBuilder builder = new StringBuilder();

    Computer computer = new Computer(programInitial, value -> {
      if (value == 35)
        builder.append("#");
      else if (value == 46)
        builder.append(".");
      else if (value == 10)
        builder.append("\r\n");
      else
        builder.append(">");
    });
    computer.exec();
    computer.join();

    out("Day 17 Input", "\r\n" + builder);
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
