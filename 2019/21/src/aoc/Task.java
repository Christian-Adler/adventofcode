package aoc;

import aoc.computer.Computer;
import aoc.util.Util;

import java.util.Map;

public class Task {
  Map<Long, Long> program;

  public void init() {
  }

  public void addLine(String input) {
    program = Computer.parseProgram(input);

  }

  public void afterParse() throws Exception {
    part1();
    part2();
  }

  private void part1() throws InterruptedException {
    Computer computer = new Computer(program, value -> {
      if (value > 0xFF) {
        System.out.println(value);
      } else {
        System.out.print((char) value.intValue());
      }
    });

    // !A || !C && D
    // Jump if hole
    computer.addInput("NOT A T", true);
    computer.addInput("NOT C J", true);
    computer.addInput("AND D J", true);
    computer.addInput("OR T J", true);
    computer.addInput("WALK", true);
    computer.exec();
    computer.join();
  }


  private void part2() throws InterruptedException {

    Computer computer = new Computer(program, value -> {
      if (value > 0xFF) {
        System.out.println(value);
      } else {
        System.out.print((char) value.intValue());
      }
    });


    // !C && D && H
    // Jump as early as possible
    // If you have to jump on island and next after first landing is a hole -> H as well land
    // @
    // ### ## ##
    computer.addInput("NOT C T", true);
    computer.addInput("AND D T", true);
    computer.addInput("AND H T", true);
    computer.addInput("OR T J", true);

    // || !B && D
    // jump if possible
    computer.addInput("NOT B T", true);
    computer.addInput("AND D T", true);
    computer.addInput("OR T J", true);

    // || !A
    // have to jump if next step is a hole
    computer.addInput("NOT A T", true);
    computer.addInput("OR T J", true);

    computer.addInput("RUN", true);
    computer.exec();
    computer.join();
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
