package instructions;

import d16.Registers;

public class InstructionBori extends Instruction {

    public static final String OPT_CODE = "bori";

    public InstructionBori(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = registers.getRegister(getInputA()) | getInputB();
        registers.setRegister(getOutput(), resultValue);
    }
}
