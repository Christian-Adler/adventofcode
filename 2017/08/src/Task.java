import java.util.ArrayList;
import java.util.List;

public class Task {

    List<Instruction> instructionList = new ArrayList<>();

    public void init() {
        Registers.clear();
    }

    public void addLine(String input) {
        instructionList.add(new Instruction(input));
    }

    public void afterParse() {

        for (Instruction instruction : instructionList) {
            instruction.exec();
//            out(Registers.toStr());
        }

        out("largest register value", Registers.largestValue());
        out("largest all time register value", Registers.allTimeMax);
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
