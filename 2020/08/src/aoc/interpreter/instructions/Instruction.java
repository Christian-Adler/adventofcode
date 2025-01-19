package aoc.interpreter.instructions;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Instruction {
  public abstract int exec(AtomicLong accumulator);

  public static Instruction parse(String instruction) {
    String[] split = instruction.split(" ");
    return switch (split[0]) {
      case "nop" -> new NOp();
      case "acc" -> new Acc(Long.parseLong(split[1]));
      case "jmp" -> new Jmp(Integer.parseInt(split[1]));
      default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
    };
  }
}
