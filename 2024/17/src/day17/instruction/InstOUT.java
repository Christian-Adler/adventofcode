package day17.instruction;

public class InstOUT extends Instruction {
  public InstOUT() {
    super(5);
  }

  @Override
  protected void execImpl() {
    int res = getComboOperandValue() % 8;
    computer.addOutput(res);
  }
}
