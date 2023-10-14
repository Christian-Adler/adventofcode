package instructions;

import d16.Registers;

public class InstructionBanr extends Instruction {

    public static final String OPT_CODE = "banr";

    public InstructionBanr(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = registers.getRegister(getInputA()) & registers.getRegister(getInputB());
        registers.setRegister(getOutput(), resultValue);
    }
}
