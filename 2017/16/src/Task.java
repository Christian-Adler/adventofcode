import java.util.ArrayList;
import java.util.List;

public class Task {
    List<String> programs = new ArrayList<>();
    List<String> dance = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        if (programs.isEmpty())
            programs.addAll(List.of(input.split("")));
        else
            dance.addAll(List.of(input.split(",")));
    }

    public void afterParse() {
        String startPositions = String.join("", programs);
        out(programs);
//        out(dance);

        for (String danceStep : dance) {
            execDanceStep(danceStep);
        }
        out("Part 1", String.join("", programs));

        // part 2

        int i = 1; // erster Schritt war ja schon Part 1
        int targetStep = 1000000000;
        while (i < targetStep) {
            i++;
            for (String danceStep : dance) {
                execDanceStep(danceStep);
            }
            String actPositions = String.join("", programs);
//            out(i, actPositions);
            if (actPositions.equals(startPositions)) {
                out("Found start position again at", i);
                int repeatAfterSteps = i;
                out(repeatAfterSteps);
//                while (i + repeatAfterSteps < targetStep)
//                    i += repeatAfterSteps;
                i = (1000000000 / (i)) * i;
                out("i", i);
            }
        }
        out("Part 2", String.join("", programs));
    }

    private void execDanceStep(String danceStep) {
        String instruction = danceStep.substring(1);
        if (danceStep.startsWith("s")) {
            int spinSize = Integer.parseInt(instruction);
            List<String> sub = new ArrayList<>(programs.subList(programs.size() - spinSize, programs.size()));
            programs.removeAll(sub);
            programs.addAll(0, sub);
        } else if (danceStep.startsWith("x")) {
            String[] split = instruction.split("/");
            int swapIdx1 = Integer.parseInt(split[0]);
            int swapIdx2 = Integer.parseInt(split[1]);
            String swapC1 = programs.get(swapIdx1);
            String swapC2 = programs.get(swapIdx2);
            programs.set(swapIdx1, swapC2);
            programs.set(swapIdx2, swapC1);
        } else if (danceStep.startsWith("p")) {
            String[] split = instruction.split("/");
            String swapC1 = split[0];
            String swapC2 = split[1];
            int swapIdx1 = programs.indexOf(swapC1);
            int swapIdx2 = programs.indexOf(swapC2);
            programs.set(swapIdx1, swapC2);
            programs.set(swapIdx2, swapC1);
        } else throw new IllegalStateException("unknown dance step");
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
