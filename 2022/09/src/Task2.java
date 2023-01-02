import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task2 {
    Set<Pos> tailPositions = new HashSet<>();

    Pos head = new Pos(0, 0, "Head");

    List<Pos> tails = new ArrayList<>();


    public void init() {
        tails.add(new Pos(0, 0, 1));
        tails.add(new Pos(0, 0, 2));
        tails.add(new Pos(0, 0, 3));
        tails.add(new Pos(0, 0, 4));
        tails.add(new Pos(0, 0, 5));
        tails.add(new Pos(0, 0, 6));
        tails.add(new Pos(0, 0, 7));
        tails.add(new Pos(0, 0, 8));
        tails.add(new Pos(0, 0, 9));

        calcTailPos();
    }

    private void calcTailPos() {

        Pos prev = head;

        for (int i = 0; i < tails.size(); i++) {
            Pos tail = tails.get(i);

            calcTailPos(prev, tail);

            if (i == tails.size() - 1)
                tailPositions.add(new Pos(tail.x, tail.y));

            prev = tail;
        }
    }

    void calcTailPos(Pos prev, Pos tail) {
        // In Touch?
        if (isInTouch(prev, tail)) {
            // Nothing to do
        } else if (tail.x == prev.x) {// Selbe Spalte
            tail.y = tail.y + (prev.y > tail.y ? 1 : -1);
        } else if (tail.y == prev.y) {// Selbe Zeile
            tail.x = tail.x + (prev.x > tail.x ? 1 : -1);
        } else { // Diagonal versetzt
            tail.y = tail.y + (prev.y > tail.y ? 1 : -1);
            tail.x = tail.x + (prev.x > tail.x ? 1 : -1);
        }
    }

    private boolean isInTouch(Pos pos1, Pos pos2) {
        return Math.abs(pos1.x - pos2.x) <= 1 && Math.abs(pos1.y - pos2.y) <= 1;
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
            head.x += xStep;
            head.y += yStep;

//            out("\r\n" + direction + (i + 1));

//            StringBuilder builder = new StringBuilder();
//            builder.append("\r\nBeforeCalc\r\nH:").append(head);
//            for (int j = 0; j < tails.size(); j++) {
//                builder.append("\r\nT").append(j + 1).append(":").append(tails.get(j));
//            }
//            out(builder.toString());

            calcTailPos();

//            builder = new StringBuilder();
//            builder.append("\r\nAfterCalc & H step\r\nH:").append(head);
//            for (int j = 0; j < tails.size(); j++) {
//                builder.append("\r\nT").append(j + 1).append(":").append(tails.get(j));
//            }
//            out(builder.toString());
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
