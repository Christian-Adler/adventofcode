public class Task {
    int row = 3010;
    int column = 3019;
//     row = 1;
//     column = 6;

    public void init() {
    }


    public void afterParse() {
        // Position berechnen
        long startRow = row + column - 1;
        out("StartRow", startRow);

        long steps = 0;
        if (startRow % 2 == 0) {
            steps = (startRow + 1) * startRow / 2 - startRow + column;
        } else {
            long tmp = startRow - 1;
            steps = (tmp + 1) * tmp / 2 + column; // 4+3+2+1 = (4+1)+(3+2) = (4+1)*4/2 = (n+1)*n/2
        }
        out(steps);

        long value = 20151125;
        for (int i = 1; i < steps; i++) {
            value *= 252533;
            value %= 33554393;
        }
        out(value); // 15514188 to high

    }

    public void out(Object... str) {
        StringBuilder out = new StringBuilder();
        for (Object o : str) {
            if (out.length() > 0)
                out.append(" ");
            out.append(o);
        }
        System.out.println(out);
    }
}
