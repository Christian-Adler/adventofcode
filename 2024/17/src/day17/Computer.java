package day17;

import day17.instruction.*;

import java.util.*;
import java.util.stream.Collectors;

public class Computer {
  private int regA = 0;
  private int regB = 0;
  private int regC = 0;
  private List<Integer> program = new ArrayList<>();
  private int instructionPointer = 0;
  private final List<Integer> output = new ArrayList<>();

  private static final Map<Integer, Instruction> instructionMap = new HashMap<>();

  static {
    for (Instruction instruction : Arrays.asList(new InstADV(), new InstBXL(), new InstBST(), new InstJNZ(), new InstBXC(), new InstOUT(), new InstBDV(), new InstCDV())) {
      instructionMap.put(instruction.OPCODE, instruction);
    }
  }

  public Computer() {
    instructionMap.values().forEach(i -> i.setComputer(this));
  }

  public void setRegA(int regA) {
    this.regA = regA;
  }

  public void setRegB(int regB) {
    this.regB = regB;
  }

  public void setRegC(int regC) {
    this.regC = regC;
  }

  public int getRegA() {
    return regA;
  }

  public int getRegB() {
    return regB;
  }

  public int getRegC() {
    return regC;
  }

  public int getOpcode() {
    return program.get(instructionPointer);
  }

  public int getOperand() {
    return program.get(instructionPointer + 1);
  }

  public int getInstructionPointer() {
    return instructionPointer;
  }

  public void setInstructionPointer(int instructionPointer) {
    this.instructionPointer = instructionPointer;
  }

  public void setProgram(List<Integer> program) {
    this.program = program;
  }

  public void addOutput(int val) {
    output.add(val);
  }

  public String run() {
    // System.out.println(this);
    while (instructionPointer < program.size()) {
      Instruction instruction = instructionMap.get(program.get(instructionPointer));
      instruction.exec();
      // System.out.println(this);
    }
    // System.out.println(output);
    return output.stream().map(String::valueOf).collect(Collectors.joining(","));
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
