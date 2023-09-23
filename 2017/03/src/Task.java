public class Task {

    int value = 0;

    public void init() {
    }

    public void addLine(String input) {
        value = Integer.parseInt(input);
    }

    public void afterParse() {
        int stepX = 1;
        int stepY = 0;
        int steps = 1;

        Pos p = new Pos(0, 0);

        Pos actPos = p.copy();

        int counter = 1;
        while (counter < value) {
            if (counter + steps > value) {
                steps = value - counter;
                counter = value;
            } else
                counter += steps;


            actPos = actPos.addToNew(stepX * steps, stepY * steps);

            if (stepX > 0) {
                stepX = 0;
                stepY = 1;
            } else if (stepY > 0) {
                stepX = -1;
                stepY = 0;
                steps++;
            } else if (stepX < 0) {
                stepX = 0;
                stepY = -1;
            } else { // if (stepY < 0) {
                stepX = 1;
                stepY = 0;
                steps++;
            }
        }

        out("manhattenDist", counter, actPos, actPos.manhattanDistance());
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
