package instructions;

import d16.Registers;

public class InstructionGtri extends Instruction {

    public static final String OPT_CODE = "gtri";

    public InstructionGtri(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = registers.getRegister(getInputA()) > getInputB() ? 1 : 0;
        registers.setRegister(getOutput(), resultValue);
    }
}
