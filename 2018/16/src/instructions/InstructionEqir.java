package instructions;

import d16.Registers;

public class InstructionEqir extends Instruction {

    public static final String OPT_CODE = "eqir";

    public InstructionEqir(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = getInputA() == registers.getRegister(getInputB()) ? 1 : 0;
        registers.setRegister(getOutput(), resultValue);
    }
}
