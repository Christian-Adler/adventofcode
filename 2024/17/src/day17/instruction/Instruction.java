package day17.instruction;

import day17.Computer;

public abstract class Instruction {
  public final long OPCODE;
  protected Computer computer;

  public Instruction(long opcode) {
    OPCODE = opcode;
  }

  public void setComputer(Computer computer) {
    this.computer = computer;
  }

  protected long getLiteralOperandValue() {
    return getOperand();
  }

  protected long getComboOperandValue() {
    return getComboOperandValue(getOperand());
  }

  protected long getComboOperandValue(long operand) {
    if (operand <= 3) return operand;
    if (operand == 4) return computer.getRegA();
    if (operand == 5) return computer.getRegB();
    if (operand == 6) return computer.getRegC();
    throw new IllegalStateException("Non valid program: operand 7");
  }

  protected long getOperand() {
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
