package computer.instructions;

import computer.Variable;

public class LessThan extends Instruction {
    public static final int OP_CODE = 7;

    public LessThan(InstructionMetaInfo opCodeAndInstructionModes) {
        super(opCodeAndInstructionModes, 2, true);
    }

    @Override
    public int exec(int[] program, Variable variable) {

        // Parameters
        int value1 = getParameter1Value(program);
        int value2 = getParameter2Value(program);

        writeValueToOutput((value1 < value2) ? 1 : 0, program);

        return instructionMetaInfo.instructionPointer + getInstructionLength();
    }
}
