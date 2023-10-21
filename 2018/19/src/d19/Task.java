package d19;

import instructions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Task {
    int instructionPointerRegister = 0;
    ArrayList<Instruction> programInput = new ArrayList<>();
    Map<String, Function<String, Instruction>> optCode2Instruction = new HashMap<>();

    public void init() {
        optCode2Instruction.put(InstructionAddi.OPT_CODE, InstructionAddi::new);
        optCode2Instruction.put(InstructionAddr.OPT_CODE, InstructionAddr::new);
        optCode2Instruction.put(InstructionBani.OPT_CODE, InstructionBani::new);
        optCode2Instruction.put(InstructionBanr.OPT_CODE, InstructionBanr::new);
        optCode2Instruction.put(InstructionBori.OPT_CODE, InstructionBori::new);
        optCode2Instruction.put(InstructionBorr.OPT_CODE, InstructionBorr::new);
        optCode2Instruction.put(InstructionEqir.OPT_CODE, InstructionEqir::new);
        optCode2Instruction.put(InstructionEqri.OPT_CODE, InstructionEqri::new);
        optCode2Instruction.put(InstructionEqrr.OPT_CODE, InstructionEqrr::new);
        optCode2Instruction.put(InstructionGtir.OPT_CODE, InstructionGtir::new);
        optCode2Instruction.put(InstructionGtri.OPT_CODE, InstructionGtri::new);
        optCode2Instruction.put(InstructionGtrr.OPT_CODE, InstructionGtrr::new);
        optCode2Instruction.put(InstructionMuli.OPT_CODE, InstructionMuli::new);
        optCode2Instruction.put(InstructionMulr.OPT_CODE, InstructionMulr::new);
        optCode2Instruction.put(InstructionSeti.OPT_CODE, InstructionSeti::new);
        optCode2Instruction.put(InstructionSetr.OPT_CODE, InstructionSetr::new);
    }

    public void addLine(String input) {
        if (input.trim().isEmpty()) return;

        if (input.startsWith("#ip")) {
            instructionPointerRegister = Integer.parseInt(Util.cleanFrom(input, "#ip").trim());
            return;
        }

        String[] split = input.split("\\s+", 2);
        Function<String, Instruction> factory = optCode2Instruction.get(split[0]);
        Instruction instruction = factory.apply(split[1]);
        programInput.add(instruction);
    }

    public void afterParse() {
        Registers registers = new Registers();

        runProgram(registers);

        int reg0Value = registers.getRegister(0);
        out("Part 1", "reg0Value", reg0Value);

        // Part 2
        // adapted from https://github.com/tmrd993/advent-of-code-solutions/blob/master/src/main/java/2k18/aoc18/Day19.java

        registers = new Registers();
        registers.setRegister(0, 1);

        int count = 0;

        int instructionPointer = 0;
        while (instructionPointer < programInput.size() && count < 5) {
            // set instruction pointer register value
            registers.setRegister(instructionPointerRegister, instructionPointer);

            Instruction instruction = programInput.get(instructionPointer);
            instruction.exec(registers);

            // read instruction pointer register value
            instructionPointer = registers.getRegister(instructionPointerRegister);
            // increment instruction pointer
            instructionPointer += 1;

            if (registers.getRegister(0) == 0) {
                count++;
                out(registers);
            }
        }

        out(registers);

        int reg1Value = registers.getRegister(1);
        out("reg1Value", reg1Value);
        out("Part 2", "sumOfFactors", sumOfFactors(reg1Value));
    }

    private void runProgram(Registers registers) {
        int instructionPointer = 0;
        while (instructionPointer < programInput.size()) {
            // set instruction pointer register value
            registers.setRegister(instructionPointerRegister, instructionPointer);

            Instruction instruction = programInput.get(instructionPointer);
            instruction.exec(registers);

            // read instruction pointer register value
            instructionPointer = registers.getRegister(instructionPointerRegister);
            // increment instruction pointer
            instructionPointer += 1;
        }
    }

    // returns the sum of the factors of the given integer
    public static long sumOfFactors(int num) {
        long sumOfFactors = 0;
        int i = 1;
        while (i <= num) {
            if (num % i == 0) {
                sumOfFactors += num / i;
            }
            i++;
        }
        return sumOfFactors;
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
