import computer.Computer;
import computer.instructions.*;

public class Task {
    int[] programInitial = new int[0];

    public void init() {
        Instruction.registerInstructionCreator(Add.OP_CODE, (Add::new));
        Instruction.registerInstructionCreator(Mult.OP_CODE, (Mult::new));
        Instruction.registerInstructionCreator(StoreToProgramFromVariable.OP_CODE, (StoreToProgramFromVariable::new));
        Instruction.registerInstructionCreator(StoreToVariableFromProgram.OP_CODE, (StoreToVariableFromProgram::new));
        Instruction.registerInstructionCreator(JumpIfTrue.OP_CODE, (JumpIfTrue::new));
        Instruction.registerInstructionCreator(JumpIfFalse.OP_CODE, (JumpIfFalse::new));
        Instruction.registerInstructionCreator(LessThan.OP_CODE, (LessThan::new));
        Instruction.registerInstructionCreator(Equals.OP_CODE, (Equals::new));
    }

    public void addLine(String input) {
        programInitial = Computer.parseProgram(input);
    }

    public void afterParse() {
        Computer computer = new Computer(programInitial);
        computer.setVariable(1);
//        computer.setProgramValue(1, 12);
//        computer.setProgramValue(2, 2);
//        out(computer);
        computer.exec();

//        out(computer);
//        out("Part 1:", computer.getOutput());
        out("Part 1:", computer.getVariable());

        // Part 2
        computer = new Computer(programInitial);
        computer.setVariable(5);
        computer.exec();
        out("Part 2:", computer.getVariable());
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
