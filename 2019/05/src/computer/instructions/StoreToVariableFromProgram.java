package computer.instructions;

import computer.Variable;

public class StoreToVariableFromProgram extends Instruction {
    public static final int OP_CODE = 4;

    public StoreToVariableFromProgram(InstructionMetaInfo opCodeAndInstructionMode) {
        super(opCodeAndInstructionMode, 1, false);
    }

    @Override
    public int exec(int[] program, Variable variable) {

        // Parameters
        int value1 = getParameter1Value(program);

        variable.value = value1;

        return instructionMetaInfo.instructionPointer + getInstructionLength();
    }
}
