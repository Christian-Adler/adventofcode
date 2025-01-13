package aoc.computer.instructions;

import aoc.computer.Computer;

public class AdjustRelativeBase extends Instruction {
  public static final int OP_CODE = 9;

  public AdjustRelativeBase(Computer computer, InstructionMetaInfo opCodeAndInstructionMode) {
    super(computer, opCodeAndInstructionMode, 1, false);
  }

  @Override
  public long exec() {

    // Parameters
    long value1 = getParameter1Value();

    computer.getRelativeBase().set(computer.getRelativeBase().get() + value1);

    return instructionMetaInfo.instructionPointer + getInstructionLength();
  }
}
