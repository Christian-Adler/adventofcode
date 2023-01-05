import java.util.ArrayList;

public class Task {

    public void init() {
    }

    public void addLine(String input) {
        String act = input;
        for (int i = 0; i < 40; i++) {
            act = lookAndSay(act);

        }
        out(act.length());
    }

    private String lookAndSay(String input) {
        String result = "";
        ArrayList<String> list = Util.str2List(input);
//        out(list);

        String prevC = "_";
        for (int i = 0; i < list.size(); i++) {
            String c = list.get(i);
            if (prevC.equals(c))
                continue;
            prevC = c;
            int sequence = numSequence(i, list);
            result += sequence + c;
        }

//        out(result);
        return result;
    }

    int numSequence(int idx, ArrayList<String> list) {
        String c = list.get(idx);
        int i = idx + 1;
        while (i < list.size()) {
            if (!list.get(i).equals(c)) break;
            i++;
        }
        return i - idx;
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
