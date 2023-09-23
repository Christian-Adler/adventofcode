import java.util.ArrayList;

public class Task {
    ArrayList<Integer> jumpList = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        jumpList.add(Integer.parseInt(input));
    }

    public void afterParse() {
//        out(jumpList);

        int counter = 0;
        int actIdx = 0;

        while (actIdx < jumpList.size()) {
            counter++;
            Integer steps = jumpList.get(actIdx);
            if (steps >= 3)
                jumpList.set(actIdx, steps - 1);
            else
                jumpList.set(actIdx, steps + 1);
            actIdx += steps;
        }

//        out(jumpList);
        out(counter);

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
