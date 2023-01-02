import java.util.HashSet;
import java.util.Set;

public class Task {
    Set<Pos> tailPositions = new HashSet<>();

    Pos head = new Pos(0, 0);
    Pos tail = new Pos(0, 0);

    public void init() {
        calcTailPos();
    }

    private void calcTailPos() {
        // In Touch?
        if (isTailInTouchWithHead()) {
            // Nothing to do
        } else if (tail.x == head.x) {// Selbe Spalte
            tail = new Pos(tail.x, tail.y + (head.y > tail.y ? 1 : -1));
        } else if (tail.y == head.y) {// Selbe Zeile
            tail = new Pos(tail.x + (head.x > tail.x ? 1 : -1), tail.y);
        } else { // Diagonal versetzt
            tail = new Pos(tail.x + (head.x > tail.x ? 1 : -1), tail.y + (head.y > tail.y ? 1 : -1));
        }
        tailPositions.add(tail);
//        out(tail);
    }

    private boolean isTailInTouchWithHead() {
        return Math.abs(tail.x - head.x) <= 1 && Math.abs(tail.y - head.y) <= 1;
    }

    public void addLine(String input) {
        String[] split = input.split(" ");
        String direction = split[0];
        int xStep = 0;
        int yStep = 0;
        if (direction.startsWith("R"))
            xStep = 1;
        else if (direction.startsWith("L"))
            xStep = -1;
        else if (direction.startsWith("U"))
            yStep = 1;
        else if (direction.startsWith("D"))
            yStep = -1;

        int steps = Integer.parseInt(split[1]);
        for (int i = 0; i < steps; i++) {
            head = new Pos(head.x + xStep, head.y + yStep);
            calcTailPos();
        }
    }

    public void afterParse() {

    }


    public void out(String str) {
        System.out.println(str);
    }

    public void out(Object str) {
        System.out.println(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Num tail pos: ").append(tailPositions.size());

        return builder.toString();
    }
}
