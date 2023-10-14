package d16;

import instructions.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Task {
    List<Sample> samples = new ArrayList<>();
    List<String> programInput = new ArrayList<>();
    Sample actParseSample = null;
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

        if (actParseSample == null) {
            if (input.startsWith("Before")) {
                actParseSample = new Sample();
                actParseSample.before = input;
            } else {
                programInput.add(input);
            }
        } else {
            if (input.startsWith("After")) {
                actParseSample.after = input;
                samples.add(actParseSample);
                actParseSample = null;
            } else actParseSample.instruction = input;
        }
    }

    public void afterParse() {
        int match3Counter = 0;

        Map<String, Integer> optCode2UniqueValue = new HashMap<>();
        Map<String, Set<Integer>> optCode2possibleValues = new HashMap<>();

        for (Sample sample : samples) {
            Registers regStart = new Registers(sample.before);
            Registers regTarget = new Registers(sample.after);

            List<Instruction> instructions = Instruction.getAllInstructions(sample.instruction);

            int matchCounter = 0;

            int optCodeNr = instructions.get(0).getRegister(0);
            List<String> optCodes = new LinkedList<>();

            for (Instruction instruction : instructions) {
                Registers copyStart = regStart.copy();
                instruction.exec(copyStart);
                if (copyStart.equals(regTarget)) {
//                    out("matches:", instruction);
                    matchCounter++;
                    optCodes.add(instruction.getOptCode());
                }
            }

//            out("matchCounter", matchCounter);
            if (matchCounter >= 3)
                match3Counter++;

            if (optCodes.size() == 1) {
                optCode2UniqueValue.put(optCodes.get(0), optCodeNr);
            } else if (optCodes.size() > 1) {
                for (String optCode : optCodes) {
                    Set<Integer> possibleValues = optCode2possibleValues.computeIfAbsent(optCode, k -> new HashSet<>());
                    possibleValues.add(optCodeNr);
                }
            }
        }

        out("part 1", "match3Counter", match3Counter);
        out();
        out("optCode2UniqueValue", optCode2UniqueValue);
        out("optCode2possibleValues", optCode2possibleValues);

        // remove all unique values
        Set<String> toRemoveKeys = new HashSet<>(optCode2UniqueValue.keySet());
        Set<Integer> toRemoveValues = new HashSet<>(optCode2UniqueValue.values().stream().toList());
        for (String toRemoveKey : toRemoveKeys) {
            optCode2possibleValues.remove(toRemoveKey);
        }
        for (Set<Integer> values : optCode2possibleValues.values()) {
            values.removeAll(toRemoveValues);
        }

        while (!optCode2possibleValues.isEmpty()) {
            toRemoveKeys = new HashSet<>();
            toRemoveValues = new HashSet<>();
            Set<Map.Entry<String, Set<Integer>>> singleValueEntries = optCode2possibleValues.entrySet().stream().filter(e -> e.getValue().size() == 1).collect(Collectors.toSet());
            for (Map.Entry<String, Set<Integer>> singleValueEntry : singleValueEntries) {
                Integer value = singleValueEntry.getValue().iterator().next();
                String optCode = singleValueEntry.getKey();

                optCode2UniqueValue.put(optCode, value);

                toRemoveKeys.add(optCode);
                toRemoveValues.add(value);
            }

            if (toRemoveKeys.isEmpty())
                throw new IllegalStateException("Found no remove key");

            for (String toRemoveKey : toRemoveKeys) {
                optCode2possibleValues.remove(toRemoveKey);
            }
            for (Set<Integer> values : optCode2possibleValues.values()) {
                values.removeAll(toRemoveValues);
            }
        }
        out();
        out("optCode2UniqueValue", optCode2UniqueValue);

        Map<Integer, String> uniqueValue2OptCode = optCode2UniqueValue.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        Registers registers = new Registers();

        for (String inp : programInput) {
            int optCodeNr = Integer.parseInt(inp.split("\\s+")[0]);
            String optCode = uniqueValue2OptCode.get(optCodeNr);

            Function<String, Instruction> instructionFactory = optCode2Instruction.get(optCode);
            Instruction instruction = instructionFactory.apply(inp);
            instruction.exec(registers);
        }

        int reg0Value = registers.getRegister(0);
        out("Part 2", "reg0Value", reg0Value);
    }

    private void simpleTest() {
        Registers regStart = new Registers("Before: [3, 2, 1, 1]");
        Registers regTarget = new Registers("After:  [3, 2, 2, 1]");

        List<Instruction> instructions = Instruction.getAllInstructions("9 2 1 2");

        int matchCounter = 0;

        for (Instruction instruction : instructions) {
            Registers copyStart = regStart.copy();
            instruction.exec(copyStart);
            if (copyStart.equals(regTarget)) {
                out("matches:", instruction);
                matchCounter++;
            }
        }

        out("matchCounter", matchCounter); // < 835
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
