package computer;

import computer.instructions.Instruction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Computer {
    private static final Map<Integer, Instruction> instructions = new HashMap<>();

    public static void addInstruction(Instruction instruction) {
        instructions.put(instruction.opCode, instruction);
    }

    private final int[] program;

    public Computer(int[] program) {
        this.program = program.clone(); // clone only works with primitives as a true copy!
    }

    public void exec() {
        int instructionPointer = 0;
        int actOpCode = program[0];
        while (actOpCode != 99) {
            Instruction instruction = instructions.get(actOpCode);
            if (instruction == null) throw new IllegalStateException("Null instead instruction for " + actOpCode);

            instructionPointer = instruction.exec(program, instructionPointer);

            actOpCode = program[instructionPointer];
        }
    }

    public void setProgramValue(int idx, int value) {
        program[idx] = value;
    }

    public int getProgramValue(int idx) {
        return program[idx];
    }

    public int getOutput() {
        return getProgramValue(0);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("computer.Computer{");
        sb.append("program=").append(Arrays.toString(program));
        sb.append('}');
        return sb.toString();
    }
}
