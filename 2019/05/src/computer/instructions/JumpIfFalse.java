package computer.instructions;

import computer.Variable;

public class JumpIfFalse extends Instruction {
    public static final int OP_CODE = 6;

    public JumpIfFalse(InstructionMetaInfo opCodeAndInstructionMode) {
        super(opCodeAndInstructionMode, 2, false);
    }

    @Override
    public int exec(int[] program, Variable variable) {

        // Parameters
        int value1 = getParameter1Value(program);
        int value2 = getParameter2Value(program);

        if (value1 == 0)
            return value2;

        return instructionMetaInfo.instructionPointer + getInstructionLength();
    }
}
