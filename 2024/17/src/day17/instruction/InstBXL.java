package day17.instruction;

public class InstBXL extends Instruction {
  public InstBXL() {
    super(1);
  }

  @Override
  protected void execImpl() {
    int res = computer.getRegB() ^ getLiteralOperandValue();
    computer.setRegB(res);
  }
}
