package computer.instructions;

import computer.IOutput;

import java.util.concurrent.LinkedBlockingQueue;

public class StoreToOutputFromProgram extends Instruction {
    public static final int OP_CODE = 4;

    public StoreToOutputFromProgram(InstructionMetaInfo opCodeAndInstructionMode) {
        super(opCodeAndInstructionMode, 1, false);
    }

    @Override
    public int exec(int[] program, LinkedBlockingQueue<Integer> input, IOutput output) {

        // Parameters
        int value1 = getParameter1Value(program);

        output.out(value1);

        return instructionMetaInfo.instructionPointer + getInstructionLength();
    }
}
