package computer.instructions;

public class Mult extends Instruction {
    public Mult() {
        super(2, 3);
    }

    @Override
    public int exec(int[] program, int instructionPointer) {

        // Parameters
        int value1Idx = getParameter1Idx(program, instructionPointer);
        int value2Idx = getParameter2Idx(program, instructionPointer);
        int targetIdx = getParameter3Idx(program, instructionPointer);

        int value1 = getValueAt(program, value1Idx);
        int value2 = getValueAt(program, value2Idx);
        int res = value1 * value2;

        setValueAt(program, targetIdx, res);

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

        return instructionPointer + getStepsToNextOpCode();
    }
}
