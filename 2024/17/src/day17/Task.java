package day17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task {

  private int regA = 0;
  private int regB = 0;
  private int regC = 0;
  private List<Long> program = null;

  public void init() {
  }

  public void addLine(String input) {
    if (input.contains("A")) {
      regA = Integer.parseInt(Util.cleanFrom(input, "Register A: "));

    } else if (input.contains("B")) {
      regB = Integer.parseInt(Util.cleanFrom(input, "Register B: "));

    } else if (input.contains("C")) {
      regC = Integer.parseInt(Util.cleanFrom(input, "Register C: "));

    } else if (input.contains("Program")) {
      program = Arrays.stream(Util.cleanFrom(input, "Program: ").split(",")).mapToLong(Long::parseLong).boxed().toList();
    }
  }

  public void afterParse() throws Exception {
    Computer computer = new Computer();
    computer.setRegA(regA);
    computer.setRegB(regB);
    computer.setRegC(regC);
    computer.setProgram(program);
    String output = computer.run();
    out("part 1", "output", output);

    // part 2
    /*
     * manually check instructions.
     * Found out Reg A is only divided by 8 in every iteration.
     * Going reverse: start by 0-7 and check which numbers end in a result which equals to the "end" of expected.
     * Use these numbers multiplied by 8 for the next round.
     */
    String expected = Computer.getProgramInstruction(program);

    out();
    out("expected", expected);

    List<Long> workList = new ArrayList<>();
    addToWorkList(0, workList);

    while (!workList.isEmpty()) {
      Long checkVal = workList.removeFirst();

      computer = new Computer();
      computer.setRegA(checkVal);
      computer.setRegB(regB);
      computer.setRegC(regC);
      computer.setProgram(program);
      output = computer.run();

      if (expected.endsWith(output)) {
        // out("Reg A", checkVal, "out:", output);
        // out(computer);
        if (expected.equals(output)) {
          out("part 2", "reg A:", checkVal);
          break;
        }

        addToWorkList(checkVal * 8, workList);
      }
    }
  }

  private static void addToWorkList(long val, List<Long> workList) {
    for (int i = 0; i < 8; i++) {
      workList.add(val + i);
    }
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
