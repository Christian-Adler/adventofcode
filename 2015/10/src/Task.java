public class Task {

    public void init() {
    }

    public void addLine(String input) {
        String act = input;
        for (int i = 0; i < 40; i++) {
            long t1 = System.currentTimeMillis();
            act = lookAndSay(act);
            long t2 = System.currentTimeMillis();
            out(t2 - t1, "ms");

        }
        out(act.length());
    }

    private String lookAndSay(String input) {
        String result = "";

        int sayCounter = 0;
        char prevC = input.charAt(0);

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (prevC == c) {
                sayCounter++;
            } else {
                result += String.valueOf(sayCounter) + String.valueOf(prevC);
                sayCounter = 1;
                prevC = c;
            }
        }
        result += String.valueOf(sayCounter) + String.valueOf(prevC);

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
