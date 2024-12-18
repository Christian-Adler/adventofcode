package day17.instruction;

public class InstOUT extends Instruction {
  public InstOUT() {
    super(5);
  }

  @Override
  protected void execImpl() {
    long res = getComboOperandValue() % 8;
    computer.addOutput(res);
  }
}
