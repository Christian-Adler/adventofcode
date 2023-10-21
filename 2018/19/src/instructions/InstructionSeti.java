package instructions;

import d19.Registers;

public class InstructionSeti extends Instruction {

    public static final String OPT_CODE = "seti";

    public InstructionSeti(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = getInputA();
        registers.setRegister(getOutput(), resultValue);
    }
}
