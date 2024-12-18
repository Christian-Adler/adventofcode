package day17.instruction;

public class InstADV extends Instruction {
  public InstADV() {
    super(0);
  }

  @Override
  protected void execImpl() {
    long numerator = computer.getRegA();
    double denominator = Math.pow(2, getComboOperandValue(getOperand()));
    long res = (long) (numerator / denominator);
    computer.setRegA(res);
  }
}
