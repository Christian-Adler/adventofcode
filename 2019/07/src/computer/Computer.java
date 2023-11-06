package computer;

import computer.instructions.Instruction;
import computer.instructions.InstructionMetaInfo;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class Computer extends Thread {
    private final int[] program;
    private final LinkedBlockingQueue<Integer> input = new LinkedBlockingQueue<>();
    private IOutput output;

    public Computer(int[] program, IOutput output) {
        this.program = program.clone(); // clone only works with primitives as a true copy!
        this.output = output;
    }

    public void setOutput(IOutput output) {
        this.output = output;
    }

    public void exec() throws InterruptedException {
        this.start();
    }

    public void run() {
        try {
            int instructionPointer = 0;
            InstructionMetaInfo instructionMetaInfo = new InstructionMetaInfo(instructionPointer, program);
//        out(this);
            while (instructionMetaInfo.opCode != 99) {
                Instruction instruction = Instruction.getInstruction(instructionMetaInfo);
                instructionPointer = instruction.exec(program, input, output);
                instructionMetaInfo = new InstructionMetaInfo(instructionPointer, program);
//            out(this);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setProgramValue(int idx, int value) {
        program[idx] = value;
    }

    public int getProgramValue(int idx) {
        return program[idx];
    }

    public void addInput(int value) {
        input.add(value);
    }

    public static int[] parseProgram(String input) {
        return Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }
}
