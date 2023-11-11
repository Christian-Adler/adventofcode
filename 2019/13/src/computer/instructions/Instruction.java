package computer.instructions;

import computer.Computer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class Instruction {
    //    private static final Map<Integer, Function<InstructionMetaInfo, Instruction>> factory = new HashMap<>();
    private static final Map<Long, BiFunction<Computer, InstructionMetaInfo, Instruction>> factory = new HashMap<>();

    public static void registerInstructionCreator(long opCode, BiFunction<Computer, InstructionMetaInfo, Instruction> creatorFnc) {
        factory.put(opCode, creatorFnc);
    }

    public static Instruction getInstruction(Computer computer, InstructionMetaInfo instructionMetaInfo) {
        BiFunction<Computer, InstructionMetaInfo, Instruction> instructionFunction = factory.get(instructionMetaInfo.opCode);
        if (instructionFunction == null)
            throw new IllegalArgumentException("Got null instead of creator fnc for " + instructionMetaInfo);
        return instructionFunction.apply(computer, instructionMetaInfo);
    }

    protected final Computer computer;
    public final InstructionMetaInfo instructionMetaInfo;
    protected final int numGetParameters;
    protected final boolean modifiesProgram;

    public Instruction(Computer computer, InstructionMetaInfo instructionMetaInfo, int numGetParameters, boolean modifiesProgram) {
        this.computer = computer;
        this.instructionMetaInfo = instructionMetaInfo;
        this.numGetParameters = numGetParameters;
        this.modifiesProgram = modifiesProgram;
    }

    /**
     * @return new instruction pointer address
     */
    public abstract long exec() throws InterruptedException;

    public int getInstructionLength() {
        return numGetParameters + (modifiesProgram ? 1 : 0) + 1;
    }

    protected Map<Long, Long> getProgram() {
        return computer.getProgram();
    }

    protected long getParameter1Value() {
        return getParameterValue(1);
    }

    protected long getParameter2Value() {
        return getParameterValue(2);
    }

    protected long getParameter3Value() {
        return getParameterValue(3);
    }

    private long getParameterValue(int parameterNo) {
        long paramValueOrAddress = getProgramValueAtInstructionPointerPlus(parameterNo);

        ParameterMode instructionMode = instructionMetaInfo.getParameterModeForParameterNo(parameterNo);
        if (instructionMode.equals(ParameterMode.IMMEDIATE_MODE)) // program value is already the value
            return paramValueOrAddress;

        if (instructionMode.equals(ParameterMode.RELATIVE_MODE)) // as position mode but relative to relative base
            return getProgram().getOrDefault(computer.getRelativeBase().get() + paramValueOrAddress, 0L);

//        if(instructionMode.equals(ParameterMode.POSITION_MODE)) // program value is the address of the value
        return getProgram().getOrDefault(paramValueOrAddress, 0L);
    }

    private long getProgramValueAtInstructionPointerPlus(long plus) {
        return getProgram().getOrDefault(instructionMetaInfo.instructionPointer + plus, 0L);
    }

    protected void writeValueToProgram(long value) {
        if (!modifiesProgram) throw new IllegalStateException("Instruction has no modify program flag!");

        ParameterMode instructionMode = instructionMetaInfo.getParameterModeForParameterNo(numGetParameters + 1);
        if (instructionMode.equals(ParameterMode.IMMEDIATE_MODE))
            throw new IllegalStateException("No immediate mode for write instruction allowed");

        long writeToAddress = getProgramValueAtInstructionPointerPlus(numGetParameters + 1);
        if (instructionMode.equals(ParameterMode.RELATIVE_MODE)) // as position mode but relative to relative base
            getProgram().put(computer.getRelativeBase().get() + writeToAddress, value);
        else // POSITION_MODE
            getProgram().put(writeToAddress, value);
    }

    @Override
    public String toString() {
        return "Instruction{" + "instructionMetaInfo=" + instructionMetaInfo +
                ", numParameters=" + numGetParameters +
                ", hasOutput=" + modifiesProgram +
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
