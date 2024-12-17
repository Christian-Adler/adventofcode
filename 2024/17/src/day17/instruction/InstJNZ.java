package day17.instruction;

public class InstJNZ extends Instruction {
  public InstJNZ() {
    super(3);
  }

  @Override
  protected void execImpl() {
    int a = computer.getRegA();
    if (a == 0) {
      super.updateInstructionPointer();
      return;
    }
    computer.setInstructionPointer(getLiteralOperandValue());
  }

  protected void updateInstructionPointer() {
    // do nothing - is done in execImpl
  }
}
