package day17;

import java.util.Arrays;

public class Task {
  private final Computer computer = new Computer();

  public void init() {
  }

  public void addLine(String input) {
    if (input.contains("A"))
      computer.setRegA(Integer.parseInt(Util.cleanFrom(input, "Register A: ")));
    else if (input.contains("B"))
      computer.setRegB(Integer.parseInt(Util.cleanFrom(input, "Register B: ")));
    else if (input.contains("C"))
      computer.setRegC(Integer.parseInt(Util.cleanFrom(input, "Register C: ")));
    else if (input.contains("Program"))
      computer.setProgram(Arrays.stream(Util.cleanFrom(input, "Program: ").split(",")).mapToInt(Integer::parseInt).boxed().toList());
  }

  public void afterParse() throws Exception {
    String output = computer.run();
    out("part 1", "output", output);
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
