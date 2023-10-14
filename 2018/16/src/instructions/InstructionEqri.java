package instructions;

import d16.Registers;

public class InstructionEqri extends Instruction {

    public static final String OPT_CODE = "eqri";

    public InstructionEqri(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = registers.getRegister(getInputA()) == getInputB() ? 1 : 0;
        registers.setRegister(getOutput(), resultValue);
    }
}
