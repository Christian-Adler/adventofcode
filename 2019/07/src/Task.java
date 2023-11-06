import computer.Computer;
import computer.instructions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    int[] programInitial = new int[0];

    public void init() {
        Instruction.registerInstructionCreator(Add.OP_CODE, (Add::new));
        Instruction.registerInstructionCreator(Mult.OP_CODE, (Mult::new));
        Instruction.registerInstructionCreator(StoreToProgramFromInput.OP_CODE, (StoreToProgramFromInput::new));
        Instruction.registerInstructionCreator(StoreToOutputFromProgram.OP_CODE, (StoreToOutputFromProgram::new));
        Instruction.registerInstructionCreator(JumpIfTrue.OP_CODE, (JumpIfTrue::new));
        Instruction.registerInstructionCreator(JumpIfFalse.OP_CODE, (JumpIfFalse::new));
        Instruction.registerInstructionCreator(LessThan.OP_CODE, (LessThan::new));
        Instruction.registerInstructionCreator(Equals.OP_CODE, (Equals::new));
    }

    public void addLine(String input) {
        programInitial = Computer.parseProgram(input);
    }

    public void afterParse() throws InterruptedException {
        int[] phases = {1, 2, 3, 4, 0};
        ArrayList<ArrayList<Integer>> phasesPermutations = getAllPermutations(phases.length, phases);

        int maxThrusterValue = Integer.MIN_VALUE;
        String phaseSequence = "";
        for (ArrayList<Integer> phasesPermutation : phasesPermutations) {
            AtomicInteger result = new AtomicInteger();
            calcResultForPhases(phasesPermutation, result);
            if (result.get() > maxThrusterValue) {
                maxThrusterValue = result.get();
                phaseSequence = phasesPermutation.toString();
            }
        }

        out("Part 1", "maxThrusterValue", maxThrusterValue);
        out("phaseSequence", phaseSequence);


        phases = new int[]{5, 6, 7, 8, 9};
        phasesPermutations = getAllPermutations(phases.length, phases);

        maxThrusterValue = Integer.MIN_VALUE;
        phaseSequence = "";
        for (ArrayList<Integer> phasesPermutation : phasesPermutations) {
            AtomicInteger result = new AtomicInteger();
            calcResultForPhases2(phasesPermutation, result);
            if (result.get() > maxThrusterValue) {
                maxThrusterValue = result.get();
                phaseSequence = phasesPermutation.toString();
            }
        }

        out("Part 2", "maxThrusterValue", maxThrusterValue);
        out("phaseSequence", phaseSequence);
    }

    private void calcResultForPhases(ArrayList<Integer> phasesPermutation, AtomicInteger result) throws InterruptedException {
        Computer ampE = new Computer(programInitial, result::set);
        ampE.addInput(phasesPermutation.get(4));// current phase setting
        Computer ampD = new Computer(programInitial, ampE::addInput);
        ampD.addInput(phasesPermutation.get(3));// current phase setting
        Computer ampC = new Computer(programInitial, ampD::addInput);
        ampC.addInput(phasesPermutation.get(2));// current phase setting
        Computer ampB = new Computer(programInitial, ampC::addInput);
        ampB.addInput(phasesPermutation.get(1));// current phase setting
        Computer ampA = new Computer(programInitial, ampB::addInput);
        ampA.addInput(phasesPermutation.get(0)); // current phase setting
        ampA.addInput(0); // first amp input value is 0

        ampA.exec();
        ampB.exec();
        ampC.exec();
        ampD.exec();
        ampE.exec();

        ampE.join();
    }

    private void calcResultForPhases2(ArrayList<Integer> phasesPermutation, AtomicInteger result) throws InterruptedException {
        Computer ampE = new Computer(programInitial, result::set);
        ampE.addInput(phasesPermutation.get(4));// current phase setting
        Computer ampD = new Computer(programInitial, ampE::addInput);
        ampD.addInput(phasesPermutation.get(3));// current phase setting
        Computer ampC = new Computer(programInitial, ampD::addInput);
        ampC.addInput(phasesPermutation.get(2));// current phase setting
        Computer ampB = new Computer(programInitial, ampC::addInput);
        ampB.addInput(phasesPermutation.get(1));// current phase setting
        Computer ampA = new Computer(programInitial, ampB::addInput);
        ampA.addInput(phasesPermutation.get(0)); // current phase setting
        ampA.addInput(0); // first amp input value is 0

        ampE.setOutput(value -> {
            result.set(value);
            ampA.addInput(value);
        });

        ampA.exec();
        ampB.exec();
        ampC.exec();
        ampD.exec();
        ampE.exec();

        ampE.join();
    }

    public void out(Object... str) {
        Util.out(str);
    }

    public static ArrayList<ArrayList<Integer>> getAllPermutations(int n, int[] elements) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (n == 1) {
            result.add(new ArrayList<>((Arrays.stream(elements).boxed().toList())));
        } else {
            for (int i = 0; i < n - 1; i++) {
                result.addAll(getAllPermutations(n - 1, elements));
                if (n % 2 == 0) {
                    swap(elements, i, n - 1);
                } else {
                    swap(elements, 0, n - 1);
                }
            }
            result.addAll(getAllPermutations(n - 1, elements));
        }
        return result;
    }

    private static void swap(int[] elements, int a, int b) {
        int tmp = elements[a];
        elements[a] = elements[b];
        elements[b] = tmp;
    }
}
