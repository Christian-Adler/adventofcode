package aoc.computer.instructions;

import aoc.computer.Computer;

public class StoreToOutputFromProgram extends Instruction {
  public static final int OP_CODE = 4;

  public StoreToOutputFromProgram(Computer computer, InstructionMetaInfo opCodeAndInstructionMode) {
    super(computer, opCodeAndInstructionMode, 1, false);
  }

  @Override
  public long exec() {

    // Parameters
    long value1 = getParameter1Value();

    computer.getOutput().out(value1);

    return instructionMetaInfo.instructionPointer + getInstructionLength();
  }
}
