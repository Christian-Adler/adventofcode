package computer.instructions;

import computer.IOutput;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public abstract class Instruction {
    private static final Map<Integer, Function<InstructionMetaInfo, Instruction>> factory = new HashMap<>();

    public static void registerInstructionCreator(int opCode, Function<InstructionMetaInfo, Instruction> creatorFnc) {
        factory.put(opCode, creatorFnc);
    }

    public static Instruction getInstruction(InstructionMetaInfo opCodeAndInstructionModes) {
        Function<InstructionMetaInfo, Instruction> opCodeAndInstructionModesInstructionFunction = factory.get(opCodeAndInstructionModes.opCode);
        if (opCodeAndInstructionModesInstructionFunction == null)
            throw new IllegalArgumentException("Got null instead of creator fnc for " + opCodeAndInstructionModes);
        return opCodeAndInstructionModesInstructionFunction.apply(opCodeAndInstructionModes);
    }

    public final InstructionMetaInfo instructionMetaInfo;
    protected final int numGetParameters;
    protected final boolean hasOutput;

    public Instruction(InstructionMetaInfo instructionMetaInfo, int numGetParameters, boolean hasOutput) {
        this.instructionMetaInfo = instructionMetaInfo;
        this.numGetParameters = numGetParameters;
        this.hasOutput = hasOutput;
    }

    /**
     * @return new instruction pointer address
     */
    public abstract int exec(int[] program, LinkedBlockingQueue<Integer> input, IOutput output) throws InterruptedException;

    public int getInstructionLength() {
        return numGetParameters + (hasOutput ? 1 : 0) + 1;
    }

    protected int getParameter1Value(int[] program) {
        return getParameterValue(program, 1);
    }

    protected int getParameter2Value(int[] program) {
        return getParameterValue(program, 2);
    }

    protected int getParameter3Value(int[] program) {
        return getParameterValue(program, 3);
    }

    private int getParameterValue(int[] program, int parameterNo) {
        int paramValueOrAddress = getProgramValueAtInstructionPointerPlus(program, parameterNo);

        ParameterMode instructionMode = instructionMetaInfo.getParameterModeForParameterNo(parameterNo);
        if (instructionMode.equals(ParameterMode.IMMEDIATE_MODE)) // program value is already the value
            return paramValueOrAddress;

//        if(instructionMode.equals(ParameterMode.POSITION_MODE)) // program value is the address of the value
        return program[paramValueOrAddress];
    }

    private int getProgramValueAtInstructionPointerPlus(int[] program, int plus) {
        return program[instructionMetaInfo.instructionPointer + plus];
    }

    protected void writeValueToOutput(int value, int[] program) {
        if (!hasOutput) throw new IllegalStateException("Instruction has no Output");
        int writeToAddress = getProgramValueAtInstructionPointerPlus(program, numGetParameters + 1);
        program[writeToAddress] = value;
    }

    @Override
    public String toString() {
        return "Instruction{" + "instructionMetaInfo=" + instructionMetaInfo +
                ", numParameters=" + numGetParameters +
                ", hasOutput=" + hasOutput +
                '}';
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
