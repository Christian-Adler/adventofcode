package computer.instructions;

import java.util.HashMap;
import java.util.Map;

public class InstructionMetaInfo {
    public final int instructionPointer;
    public final int opCode;
    private final Map<Integer, ParameterMode> parameterModes = new HashMap<>();

    public InstructionMetaInfo(int instructionPointer, int[] program) {
        this.instructionPointer = instructionPointer;

        int instructionValue = program[instructionPointer];

        // opCode = last 2 digits
        this.opCode = instructionValue % 100;

        // Parameter modes from right to left
        // only Modes other than position mode (default) will be stored
        int parameterNo = 1;
        int v = instructionValue / 100;
        while (v > 0) {
            ParameterMode parameterMode = ParameterMode.from(v % 10);
            if (!parameterMode.equals(ParameterMode.POSITION_MODE))
                parameterModes.put(parameterNo, parameterMode);
            v = v / 10;
            parameterNo++;
        }
    }

    /**
     * @param no 1...x
     * @return ParameterMode
     */
    public ParameterMode getParameterModeForParameterNo(int no) {
        return parameterModes.getOrDefault(no, ParameterMode.POSITION_MODE);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InstructionMetaInfo{");
        sb.append("instructionPointer=").append(instructionPointer);
        sb.append(", opCode=").append(opCode);
        sb.append(", parameterModes(other than position mode=").append(parameterModes);
        sb.append('}');
        return sb.toString();
    }
}
