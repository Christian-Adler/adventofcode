package day17.instruction;

public class InstBDV extends Instruction {
  public InstBDV() {
    super(6);
  }

  @Override
  protected void execImpl() {
    long numerator = computer.getRegA();
    double denominator = Math.pow(2, getComboOperandValue(getOperand()));
    long res = (long) (numerator / denominator);
    computer.setRegB(res);
  }
}
