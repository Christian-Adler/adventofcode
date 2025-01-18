package aoc.computer;

import aoc.computer.instructions.*;
import aoc.util.Util;

import java.util.*;
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
  private final IInput IInput;
  private IOutput output;

  private final AtomicLong relativeBase = new AtomicLong();

  public Computer(Map<Long, Long> program, IOutput output, IInput IInput) {
    this.program = new HashMap<>(program);
    this.output = output;
    this.IInput = IInput;
  }

  public void setOutput(IOutput output) {
    this.output = output;
  }

  public Map<Long, Long> getProgram() {
    return program;
  }

  public IInput getInput() {
    return IInput;
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
      while (instructionMetaInfo.opCode != 99 && !this.isInterrupted()) {
        Instruction instruction = Instruction.getInstruction(this, instructionMetaInfo);
        instructionPointer = instruction.exec();
        instructionMetaInfo = new InstructionMetaInfo(instructionPointer, program);
//            out(this);
      }
      // System.out.println("Exit computer run...");
    } catch (InterruptedException e) {
      System.out.println("Exit computer run by interrupt...");
    }
  }

  public void setProgramValue(long idx, long value) {
    program.put(idx, value);
  }

  public long getProgramValue(long idx) {
    return program.getOrDefault(idx, 0L);
  }


  public static List<Long> map2IntCode(String s, boolean appendNL) {
    List<Long> res = new ArrayList<>();
    for (String str : Util.str2List(s)) {
      res.add(mapStr2intCode(str));
    }
    if (appendNL)
      res.add(mapStr2intCode("\n"));
    return res;
  }

  public static long mapStr2intCode(String s) {
    return s.codePointAt(0);
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
