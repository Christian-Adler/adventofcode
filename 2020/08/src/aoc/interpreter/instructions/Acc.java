package aoc.interpreter.instructions;

import java.util.concurrent.atomic.AtomicLong;

public class Acc extends Instruction {
  private final long accValue;

  public Acc(long accValue) {
    this.accValue = accValue;
  }

  @Override
  public int exec(AtomicLong accumulator) {
    accumulator.addAndGet(accValue);
    return 1;
  }
}
