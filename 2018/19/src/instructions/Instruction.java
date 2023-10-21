package instructions;

import d19.Registers;

public class Instruction {
    protected final String optCode;

    private final int A;
    private final int B;
    private final int C;

    public Instruction(String optCode, String input) {
        this.optCode = optCode;
        String[] split = input.split("\\s+");
        if (split.length != 3) throw new IllegalArgumentException("invalid input " + input);
        A = Integer.parseInt(split[0]);
        B = Integer.parseInt(split[1]);
        C = Integer.parseInt(split[2]);
    }


    int getInputA() {
        return A;
    }

    int getInputB() {
        return B;
    }

    int getOutput() {
        return C;
    }

    public String getOptCode() {
        return optCode;
    }

    public void exec(Registers registers) {
    }

    @Override
    public String toString() {
        return optCode;
    }

}

