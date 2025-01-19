package aoc.interpreter.instructions;

import java.util.concurrent.atomic.AtomicLong;

public class NOp extends Instruction {

  @Override
  public int exec(AtomicLong accumulator) {
    return 1;
  }
}
