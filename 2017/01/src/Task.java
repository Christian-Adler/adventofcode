public class Task {

    String input = "";

    public void init() {
    }

    public void addLine(String input) {
        this.input = input;
    }

    public void afterParse() {
        long sum = 0;
        int prevVal = -1;
        for (int i = 0; i < input.length(); i++) {
            int val = Integer.parseInt(String.valueOf(input.charAt(i)));
            if (prevVal == val) {
                sum += val;
            }
            prevVal = val;
        }
        // letzes und erstes
        if (input.charAt(0) == input.charAt(input.length() - 1))
            sum += Integer.parseInt(String.valueOf(input.charAt(0)));

        out("Sum 1", sum);

        // Teil 2
        int steps = input.length() / 2;

        sum = 0;
        for (int i = 0; i < input.length(); i++) {
            int val1 = Integer.parseInt(String.valueOf(input.charAt(i)));
            int val2 = Integer.parseInt(String.valueOf(input.charAt((i + steps) % input.length())));
            if (val1 == val2) {
                sum += val1;
            }
        }
        out("Sum 2", sum);
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
