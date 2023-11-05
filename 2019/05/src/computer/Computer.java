package computer;

import computer.instructions.Instruction;
import computer.instructions.InstructionMetaInfo;

import java.util.Arrays;

public class Computer {
    private final int[] program;
    private final Variable variable = new Variable();

    public Computer(int[] program) {
        this.program = program.clone(); // clone only works with primitives as a true copy!
    }

    public void exec() {
        int instructionPointer = 0;
        InstructionMetaInfo instructionMetaInfo = new InstructionMetaInfo(instructionPointer, program);
//        out(this);
        while (instructionMetaInfo.opCode != 99) {
            Instruction instruction = Instruction.getInstruction(instructionMetaInfo);
            instructionPointer = instruction.exec(program, variable);
            instructionMetaInfo = new InstructionMetaInfo(instructionPointer, program);
//            out(this);
        }
//        out(this);
    }

    public void setProgramValue(int idx, int value) {
        program[idx] = value;
    }

    public int getProgramValue(int idx) {
        return program[idx];
    }

    public void setVariable(int value) {
        variable.value = value;
    }

    public int getVariable() {
        return variable.value;
    }

    public int getOutput() {
        return getProgramValue(0);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("computer.Computer{");
        sb.append("program=").append(Arrays.toString(program));
        sb.append("variable=").append(variable);
        sb.append('}');
        return sb.toString();
    }

    public static int[] parseProgram(String input) {
        return Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }
}
