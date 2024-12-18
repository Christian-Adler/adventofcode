package day17;

import day17.instruction.*;

import java.util.*;
import java.util.stream.Collectors;

public class Computer {
  private long regA = 0;
  private long regB = 0;
  private long regC = 0;
  private List<Long> program = new ArrayList<>();
  private long instructionPointer = 0;
  private final List<Long> output = new ArrayList<>();

  private final Map<Long, Instruction> instructionMap = new HashMap<>();


  public Computer() {
    for (Instruction instruction : Arrays.asList(new InstADV(), new InstBXL(), new InstBST(), new InstJNZ(), new InstBXC(), new InstOUT(), new InstBDV(), new InstCDV())) {
      instructionMap.put(instruction.OPCODE, instruction);
    }
    instructionMap.values().forEach(i -> i.setComputer(this));
  }

  public void setRegA(long regA) {
    this.regA = regA;
  }

  public void setRegB(long regB) {
    this.regB = regB;
  }

  public void setRegC(long regC) {
    this.regC = regC;
  }

  public long getRegA() {
    return regA;
  }

  public long getRegB() {
    return regB;
  }

  public long getRegC() {
    return regC;
  }

  public long getOpcode() {
    return program.get((int) instructionPointer);
  }

  public long getOperand() {
    return program.get((int) (instructionPointer + 1));
  }

  public long getInstructionPointer() {
    return instructionPointer;
  }

  public void setInstructionPointer(long instructionPointer) {
    this.instructionPointer = instructionPointer;
  }

  public void setProgram(List<Long> program) {
    this.program = program;
  }

  public void addOutput(long val) {

    output.add(val);
    // if (getProgramInstruction(program).startsWith(getProgramInstruction(output)))
    //   System.out.println(this);
  }

  public String run() {
    // System.out.println(this);
    while (instructionPointer < program.size()) {
      Instruction instruction = instructionMap.get(program.get((int) instructionPointer));
      instruction.exec();
      // System.out.println(this);
    }
    // System.out.println(output);
    return getProgramInstruction(output);
  }

  public static String getProgramInstruction(List<Long> list) {
    return list.stream().map(String::valueOf).collect(Collectors.joining(","));
  }


  @Override
  public String toString() {
    return "Computer{" +
        "regA=" + regA +
        ", regB=" + regB +
        ", regC=" + regC +
        ", instructionPointer=" + instructionPointer +
        ", output=" + output +
        '}';
  }
}
