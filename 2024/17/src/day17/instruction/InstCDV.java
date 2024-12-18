package day17.instruction;

public class InstCDV extends Instruction {
  public InstCDV() {
    super(7);
  }

  @Override
  protected void execImpl() {
    long numerator = computer.getRegA();
    double denominator = Math.pow(2, getComboOperandValue(getOperand()));
    long res = (long) (numerator / denominator);
    computer.setRegC(res);
  }
}
