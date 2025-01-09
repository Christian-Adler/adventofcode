package aoc.computer.instructions;

import aoc.computer.Computer;

public class Mult extends Instruction {
  public static final int OP_CODE = 2;

  public Mult(Computer computer, InstructionMetaInfo opCodeAndInstructionMode) {
    super(computer, opCodeAndInstructionMode, 2, true);
  }

  @Override
  public long exec() {

    // Parameters
    long value1 = getParameter1Value();
    long value2 = getParameter2Value();
    long res = value1 * value2;

    writeValueToProgram(res);

    return instructionMetaInfo.instructionPointer + getInstructionLength();

//        out("\r\n", this.getClass().getSimpleName());
//        if (value1Idx == 1 || value1Idx == 2)
//            out("idx1", value1Idx, "val", value1);
//        else
//            out(" val1", value1);
//        if (value2Idx == 1 || value2Idx == 2)
//            out("idx2", value2Idx, "val", value2);
//        else
//            out(" val2", value2);
//
//        if (targetIdx == 1 || targetIdx == 2)
//            out("targetIdx", targetIdx, "res", res);
//        else
//            out(" res", res);
  }
}
