package aoc.computer.instructions;

import aoc.computer.Computer;

public class JumpIfFalse extends Instruction {
  public static final int OP_CODE = 6;

  public JumpIfFalse(Computer computer, InstructionMetaInfo opCodeAndInstructionMode) {
    super(computer, opCodeAndInstructionMode, 2, false);
  }

  @Override
  public long exec() {

    // Parameters
    long value1 = getParameter1Value();
    long value2 = getParameter2Value();

    if (value1 == 0)
      return value2;

    return instructionMetaInfo.instructionPointer + getInstructionLength();
  }
}
