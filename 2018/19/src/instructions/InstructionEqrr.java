package instructions;

import d19.Registers;

public class InstructionEqrr extends Instruction {

    public static final String OPT_CODE = "eqrr";

    public InstructionEqrr(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = registers.getRegister(getInputA()) == registers.getRegister(getInputB()) ? 1 : 0;
        registers.setRegister(getOutput(), resultValue);
    }
}
