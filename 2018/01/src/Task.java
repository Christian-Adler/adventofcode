public class Task {
    int f = 0;

    public void init() {
    }

    public void addLine(String input) {
        f += Integer.parseInt(input);
    }

    public void afterParse() {
        out("f", f);
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
