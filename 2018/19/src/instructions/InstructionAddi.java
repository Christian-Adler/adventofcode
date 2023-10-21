package instructions;

import d19.Registers;

public class InstructionAddi extends Instruction {
    public static final String OPT_CODE = "addi";

    public InstructionAddi(String input) {
        super(OPT_CODE, input);
    }

    @Override
    public void exec(Registers registers) {
        int resultValue = registers.getRegister(getInputA()) + getInputB();
        registers.setRegister(getOutput(), resultValue);
    }
}
