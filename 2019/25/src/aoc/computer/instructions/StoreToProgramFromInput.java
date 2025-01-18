package aoc.computer.instructions;

import aoc.computer.Computer;

public class StoreToProgramFromInput extends Instruction {
  public static final int OP_CODE = 3;

  public StoreToProgramFromInput(Computer computer, InstructionMetaInfo opCodeAndInstructionMode) {
    super(computer, opCodeAndInstructionMode, 0, true);
  }

  @Override
  public long exec() throws InterruptedException {
    long res = computer.getInput().get();
    // System.out.println("read: " + res);
    writeValueToProgram(res);

    return instructionMetaInfo.instructionPointer + getInstructionLength();
  }
}
