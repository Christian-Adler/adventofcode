package aoc.interpreter.instructions;

import java.util.concurrent.atomic.AtomicLong;

public class Jmp extends Instruction {
  private final int offset;

  public Jmp(int offset) {
    this.offset = offset;
  }


  @Override
  public int exec(AtomicLong accumulator) {
    return offset;
  }
}
