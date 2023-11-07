package computer;

import computer.instructions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class Computer extends Thread {
    static {
        Instruction.registerInstructionCreator(Add.OP_CODE, (Add::new));
        Instruction.registerInstructionCreator(Mult.OP_CODE, (Mult::new));
        Instruction.registerInstructionCreator(StoreToProgramFromInput.OP_CODE, (StoreToProgramFromInput::new));
        Instruction.registerInstructionCreator(StoreToOutputFromProgram.OP_CODE, (StoreToOutputFromProgram::new));
        Instruction.registerInstructionCreator(JumpIfTrue.OP_CODE, (JumpIfTrue::new));
        Instruction.registerInstructionCreator(JumpIfFalse.OP_CODE, (JumpIfFalse::new));
        Instruction.registerInstructionCreator(LessThan.OP_CODE, (LessThan::new));
        Instruction.registerInstructionCreator(Equals.OP_CODE, (Equals::new));
        Instruction.registerInstructionCreator(AdjustRelativeBase.OP_CODE, (AdjustRelativeBase::new));
    }

    private final Map<Long, Long> program;
    private final LinkedBlockingQueue<Long> input = new LinkedBlockingQueue<>();
    private IOutput output;

    private final AtomicLong relativeBase = new AtomicLong();

    public Computer(Map<Long, Long> program, IOutput output) {
        this.program = new HashMap<>(program);
        this.output = output;
    }

    public void setOutput(IOutput output) {
        this.output = output;
    }

    public Map<Long, Long> getProgram() {
        return program;
    }

    public LinkedBlockingQueue<Long> getInput() {
        return input;
    }

    public IOutput getOutput() {
        return output;
    }

    public AtomicLong getRelativeBase() {
        return relativeBase;
    }

    public void exec() throws InterruptedException {
        this.start();
    }

    public void run() {
        try {
            long instructionPointer = 0;
            InstructionMetaInfo instructionMetaInfo = new InstructionMetaInfo(instructionPointer, program);
//        out(this);
            while (instructionMetaInfo.opCode != 99) {
                Instruction instruction = Instruction.getInstruction(this, instructionMetaInfo);
                instructionPointer = instruction.exec();
                instructionMetaInfo = new InstructionMetaInfo(instructionPointer, program);
//            out(this);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setProgramValue(long idx, long value) {
        program.put(idx, value);
    }

    public long getProgramValue(long idx) {
        return program.getOrDefault(idx, 0L);
    }

    public void addInput(long value) {
        input.add(value);
    }

    public static Map<Long, Long> parseProgram(String input) {
        long[] longs = Arrays.stream(input.split(",")).mapToLong(Long::parseLong).toArray();
        Map<Long, Long> program = new HashMap<>();
        for (int i = 0; i < longs.length; i++) {
            long anInt = longs[i];
            program.put((long) i, anInt);
        }

        return program;
    }
}
