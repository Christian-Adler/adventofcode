package computer.instructions;

import computer.IOutput;

import java.util.concurrent.LinkedBlockingQueue;

public class Add extends Instruction {
    public static final int OP_CODE = 1;

    public Add(InstructionMetaInfo opCodeAndInstructionModes) {
        super(opCodeAndInstructionModes, 2, true);
    }

    @Override
    public int exec(int[] program, LinkedBlockingQueue<Integer> input, IOutput output) {

        // Parameters
        int value1 = getParameter1Value(program);
        int value2 = getParameter2Value(program);
        int res = value1 + value2;

        writeValueToOutput(res, program);

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
