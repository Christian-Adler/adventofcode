package instructions;

import d16.Registers;

public class InstructionSetr extends Instruction {

    public static final String OPT_CODE = "setr";

    public InstructionSetr(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = registers.getRegister(getInputA());
        registers.setRegister(getOutput(), resultValue);
    }
}
