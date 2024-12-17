package day17.instruction;

public class InstBDV extends Instruction {
  public InstBDV() {
    super(6);
  }

  @Override
  protected void execImpl() {
    int numerator = computer.getRegA();
    double denominator = Math.pow(2, getComboOperandValue(getOperand()));
    int res = (int) (numerator / denominator);
    computer.setRegB(res);
  }
}
