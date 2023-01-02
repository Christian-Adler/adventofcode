import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Task {

    int numStacks = -1;

    ArrayList<Stack<String>> stacks = new ArrayList<>();

    public void addLine(String input) {

        String trimmed = input.trim();
        // first line
        if (numStacks < 0) {
            numStacks = (input.length() + 1) / 4;
            for (int i = 0; i < numStacks; i++) {
                stacks.add(new Stack<>());
            }
        }

        if (trimmed.isEmpty() || trimmed.startsWith("1")) {
//            System.out.println(toString());
            return;
        }

        if (input.startsWith("move")) {
            // move 1 from 9 to 2
//            System.out.println(input);
            int[] in = Arrays.stream(input.replace("move ", "").replace("from ", "").replace("to ", "").split(" ")).mapToInt(x -> Integer.parseInt(x)).toArray();

//            System.out.println(Arrays.toString(in));
            int num = in[0];
            Stack<String> from = stacks.get(in[1] - 1);
            Stack<String> to = stacks.get(in[2] - 1);

            // Aufgabe 1
//            for (int i = 0; i < num; i++) {
//                to.push(from.pop());
//            }

            // Aufgabe 2
            Stack<String> tmp = new Stack<>();
            for (int i = 0; i < num; i++) {
                tmp.push(from.pop());
            }
            for (int i = 0; i < num; i++) {
                to.push(tmp.pop());
            }

//            System.out.println(toString());
        } else if (input.length() > 0) {
            for (int i = 0; i < numStacks; i++) {
                int idx = i * 4;
                String crate = input.substring(idx, idx + 3).trim().replace("[", "").replace("]", "");
                if (!crate.isEmpty())
                    stacks.get(i).add(0, crate);
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numStacks; i++) {
            builder.append(stacks.get(i));
            builder.append("\r\n");
        }

        builder.append("\r\n");
        for (int i = 0; i < numStacks; i++) {
            builder.append(stacks.get(i).peek());
        }


        return builder.toString();
    }
}
