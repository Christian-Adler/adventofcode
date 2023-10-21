package instructions;

import d19.Registers;

public class InstructionAddr extends Instruction {

    public static final String OPT_CODE = "addr";

    public InstructionAddr(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = registers.getRegister(getInputA()) + registers.getRegister(getInputB());
        registers.setRegister(getOutput(), resultValue);
    }
}
