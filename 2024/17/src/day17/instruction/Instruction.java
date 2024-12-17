package day17.instruction;

import day17.Computer;

public abstract class Instruction {
  public final int OPCODE;
  protected Computer computer;

  public Instruction(int opcode) {
    OPCODE = opcode;
  }

  public void setComputer(Computer computer) {
    this.computer = computer;
  }

  protected int getLiteralOperandValue() {
    return getOperand();
  }

  protected int getComboOperandValue() {
    return getComboOperandValue(getOperand());
  }

  protected int getComboOperandValue(int operand) {
    if (operand <= 3) return operand;
    if (operand == 4) return computer.getRegA();
    if (operand == 5) return computer.getRegB();
    if (operand == 6) return computer.getRegC();
    throw new IllegalStateException("Non valid program: operand 7");
  }

  protected int getOperand() {
    return computer.getOperand();
  }

  public final void exec() {
    execImpl();
    updateInstructionPointer();
  }

  protected abstract void execImpl();

  protected void updateInstructionPointer() {
    computer.setInstructionPointer(computer.getInstructionPointer() + 2);
  }
}
