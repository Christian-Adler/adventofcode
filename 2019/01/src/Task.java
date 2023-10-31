public class Task {

    long sum = 0;
    long sum2 = 0;

    public void init() {
    }

    public void addLine(String input) {
        int iInput = Integer.parseInt(input);
        int fuel = calcFuel(iInput);
//        out(fuel);
        sum += fuel;

        // part 2
        int fuel2 = fuel;
        while (fuel > 0) {
            fuel = calcFuel(fuel);
            if (fuel > 0)
                fuel2 += fuel;
        }
        sum2 += fuel2;
    }

    private static int calcFuel(int mass) {
        return (mass / 3) - 2;
    }

    public void afterParse() {
        out("Part 1 ", sum);
        out("Part 2 ", sum2);
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
