package instructions;

import d16.Registers;

public class InstructionGtir extends Instruction {

    public static final String OPT_CODE = "gtir";

    public InstructionGtir(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = getInputA() > registers.getRegister(getInputB()) ? 1 : 0;
        registers.setRegister(getOutput(), resultValue);
    }
}
