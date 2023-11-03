package computer.instructions;

public abstract class Instruction {
    public final int opCode;
    protected final int numParameters;

    public Instruction(int opCode, int numParameters) {
        this.opCode = opCode;
        this.numParameters = numParameters;
    }

    /**
     * @return next instructionPointer (index)
     */
    public abstract int exec(int[] program, int instructionPointer);

    protected int getStepsToNextOpCode() {
        return numParameters + 1;
    }

    protected static int getParameter1Idx(int[] program, int instructionPointer) {
        return program[instructionPointer + 1];
    }

    protected static int getParameter2Idx(int[] program, int instructionPointer) {
        return program[instructionPointer + 2];
    }

    protected static int getParameter3Idx(int[] program, int instructionPointer) {
        return program[instructionPointer + 3];
    }

    protected static int getValueAt(int[] program, int idx) {
        return program[idx];
    }

    protected static void setValueAt(int[] program, int idx, int value) {
        program[idx] = value;
    }

    public static void out(Object... str) {
        StringBuilder out = new StringBuilder();
        for (Object o : str) {
            if (!out.isEmpty())
                out.append(" ");
            out.append(o);
        }
        System.out.println(out);
    }
}
