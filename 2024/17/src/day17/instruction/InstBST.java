package day17.instruction;

public class InstBST extends Instruction {
  public InstBST() {
    super(2);
  }

  @Override
  protected void execImpl() {
    int res = getComboOperandValue() % 8;
    computer.setRegB(res);
  }
}
