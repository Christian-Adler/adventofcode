package instructions;

import d16.Registers;

import java.util.ArrayList;
import java.util.List;

public class Instruction extends Registers {
    protected String optCode = "?";

    public Instruction(String input) {
        super(input);
    }

    public Instruction(String optCode, String input) {
        super(input);
        this.optCode = optCode;
    }


    int getInputA() {
        return regis.get(1);
    }

    int getInputB() {
        return regis.get(2);
    }

    int getOutput() {
        return regis.get(3);
    }

    public String getOptCode() {
        return optCode;
    }

    public void exec(Registers registers) {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("instructions.Instruction{");
        sb.append("optCode='").append(optCode).append('\'');
        sb.append(", registers=").append(regis);
        sb.append('}');
        return sb.toString();
    }

    public static List<Instruction> getAllInstructions(String input) {
        List<Instruction> list = new ArrayList<>();

        list.add(new InstructionAddi(input));
        list.add(new InstructionAddr(input));
        list.add(new InstructionBani(input));
        list.add(new InstructionBanr(input));
        list.add(new InstructionBori(input));
        list.add(new InstructionBorr(input));
        list.add(new InstructionEqir(input));
        list.add(new InstructionEqri(input));
        list.add(new InstructionEqrr(input));
        list.add(new InstructionGtir(input));
        list.add(new InstructionGtri(input));
        list.add(new InstructionGtrr(input));
        list.add(new InstructionMuli(input));
        list.add(new InstructionMulr(input));
        list.add(new InstructionSeti(input));
        list.add(new InstructionSetr(input));

        return list;
    }
}

