import java.util.ArrayList;
import java.util.List;

public class Task {

    public void init() {
    }

    public void addLine(String input) {
        List<Integer> act = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            act.add(Integer.parseInt(input.substring(i, i + 1)));
        }

        for (int i = 0; i < 50; i++) {
            act = lookAndSay(act);
        }
        out(act.size());
    }

    private List<Integer> lookAndSay(List<Integer> input) {
        List<Integer> result = new ArrayList<>();

        int sayCounter = 0;
        int prevC = input.get(0);

        for (int i = 0; i < input.size(); i++) {
            int c = input.get(i);
            if (prevC == c) {
                sayCounter++;
            } else {
                result.add(sayCounter);
                result.add(prevC);

                sayCounter = 1;
                prevC = c;
            }
        }
        result.add(sayCounter);
        result.add(prevC);

//        out(result);
        return result;
    }


    public void afterParse() {
    }

    public void out(Object... str) {
        String out = "";
        for (Object o : str) {
            if (out.length() > 0)
                out += " ";
            out += o;
        }
        System.out.println(out);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            result = result.replace(string, "");
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
