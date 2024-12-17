package day17.instruction;

public class InstCDV extends Instruction {
  public InstCDV() {
    super(7);
  }

  @Override
  protected void execImpl() {
    int numerator = computer.getRegA();
    double denominator = Math.pow(2, getComboOperandValue(getOperand()));
    int res = (int) (numerator / denominator);
    computer.setRegC(res);
  }
}
