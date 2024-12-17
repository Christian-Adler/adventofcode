package day17.instruction;

public class InstBXC extends Instruction {
  public InstBXC() {
    super(4);
  }

  @Override
  protected void execImpl() {
    int res = computer.getRegB() ^ computer.getRegC();
    computer.setRegB(res);
  }
}
