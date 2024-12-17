package day17.instruction;

public class InstADV extends Instruction {
  public InstADV() {
    super(0);
  }

  @Override
  protected void execImpl() {
    int numerator = computer.getRegA();
    double denominator = Math.pow(2, getComboOperandValue(getOperand()));
    int res = (int) (numerator / denominator);
    computer.setRegA(res);
  }
}
