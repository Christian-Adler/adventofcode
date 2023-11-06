package computer.instructions;

import computer.IOutput;

import java.util.concurrent.LinkedBlockingQueue;

public class StoreToProgramFromInput extends Instruction {
    public static final int OP_CODE = 3;

    public StoreToProgramFromInput(InstructionMetaInfo opCodeAndInstructionMode) {
        super(opCodeAndInstructionMode, 0, true);
    }

    @Override
    public int exec(int[] program, LinkedBlockingQueue<Integer> input, IOutput output) throws InterruptedException {

        int res = input.take();

        writeValueToOutput(res, program);

        return instructionMetaInfo.instructionPointer + getInstructionLength();
    }
}
