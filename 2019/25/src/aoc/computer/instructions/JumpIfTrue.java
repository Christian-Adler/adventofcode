package aoc.computer.instructions;

import aoc.computer.Computer;

public class JumpIfTrue extends Instruction {
  public static final int OP_CODE = 5;

  public JumpIfTrue(Computer computer, InstructionMetaInfo opCodeAndInstructionMode) {
    super(computer, opCodeAndInstructionMode, 2, false);
  }

  @Override
  public long exec() {

    // Parameters
    long value1 = getParameter1Value();
    long value2 = getParameter2Value();

    if (value1 != 0)
      return value2;

    return instructionMetaInfo.instructionPointer + getInstructionLength();
  }
}
