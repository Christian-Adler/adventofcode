package computer.instructions;

import computer.Variable;

public class StoreToProgramFromVariable extends Instruction {
    public static final int OP_CODE = 3;

    public StoreToProgramFromVariable(InstructionMetaInfo opCodeAndInstructionMode) {
        super(opCodeAndInstructionMode, 0, true);
    }

    @Override
    public int exec(int[] program, Variable variable) {

        int res = variable.value;

        writeValueToOutput(res, program);

        return instructionMetaInfo.instructionPointer + getInstructionLength();
    }
}
