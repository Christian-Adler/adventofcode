package aoc.computer.instructions;

import aoc.computer.Computer;

public class Equals extends Instruction {
  public static final int OP_CODE = 8;

  public Equals(Computer computer, InstructionMetaInfo opCodeAndInstructionModes) {
    super(computer, opCodeAndInstructionModes, 2, true);
  }

  @Override
  public long exec() {

    // Parameters
    long value1 = getParameter1Value();
    long value2 = getParameter2Value();

    writeValueToProgram((value1 == value2) ? 1 : 0);

    return instructionMetaInfo.instructionPointer + getInstructionLength();
  }
}
