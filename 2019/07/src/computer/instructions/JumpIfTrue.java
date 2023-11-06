package computer.instructions;

import computer.IOutput;

import java.util.concurrent.LinkedBlockingQueue;

public class JumpIfTrue extends Instruction {
    public static final int OP_CODE = 5;

    public JumpIfTrue(InstructionMetaInfo opCodeAndInstructionMode) {
        super(opCodeAndInstructionMode, 2, false);
    }

    @Override
    public int exec(int[] program, LinkedBlockingQueue<Integer> input, IOutput output) {

        // Parameters
        int value1 = getParameter1Value(program);
        int value2 = getParameter2Value(program);

        if (value1 != 0)
            return value2;

        return instructionMetaInfo.instructionPointer + getInstructionLength();
    }
}
