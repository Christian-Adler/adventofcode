package computer.instructions;

import computer.IOutput;

import java.util.concurrent.LinkedBlockingQueue;

public class LessThan extends Instruction {
    public static final int OP_CODE = 7;

    public LessThan(InstructionMetaInfo opCodeAndInstructionModes) {
        super(opCodeAndInstructionModes, 2, true);
    }

    @Override
    public int exec(int[] program, LinkedBlockingQueue<Integer> input, IOutput output) {

        // Parameters
        int value1 = getParameter1Value(program);
        int value2 = getParameter2Value(program);

        writeValueToOutput((value1 < value2) ? 1 : 0, program);

        return instructionMetaInfo.instructionPointer + getInstructionLength();
    }
}
