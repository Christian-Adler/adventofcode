import java.util.HashMap;
import java.util.Map;

public class Task {

    final int ROCK = 1;
    final int SAND = 2;

    int yMin = 0;
    int yMax = 0;
    int xMin = Integer.MAX_VALUE;
    int xMax = Integer.MIN_VALUE;

    Pos fallingSand = null;

    Map<Pos, Integer> map = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split(" -> ");
        Pos actPos = null;
        for (int i = 0; i < split.length; i++) {
            Pos pos = new Pos(split[i]);
            if (actPos != null)
                createRocks(actPos, pos);

            actPos = pos;
            yMax = Math.max(yMax, pos.y);
            xMin = Math.min(xMin, pos.x);
            xMax = Math.max(xMax, pos.x);
        }
    }

    void createRocks(Pos p1, Pos p2) {
        if (p1.x == p2.x) {
            for (int y = Math.min(p1.y, p2.y); y <= Math.max(p1.y, p2.y); y++) {
                map.put(new Pos(p1.x, y), ROCK);
            }
        } else if (p1.y == p2.y) {
            for (int x = Math.min(p1.x, p2.x); x <= Math.max(p1.x, p2.x); x++) {
                map.put(new Pos(x, p1.y), ROCK);
            }
        }
    }

    public void afterParse() {
        int sandUnits = 0;
        boolean sandMoving = true;
        fallingSand = new Pos(500, 0);
        while (sandMoving) {
            // Ueber yMax?
            if (fallingSand.y > yMax)
                sandMoving = false;

            // Falling Sand step
            Pos checkPos = new Pos(fallingSand.x, fallingSand.y + 1);

            if (map.get(checkPos) == null) {
                fallingSand.y++;
                continue;
            }
            checkPos = new Pos(fallingSand.x - 1, fallingSand.y + 1);
            if (map.get(checkPos) == null) {
                fallingSand.x--;
                fallingSand.y++;
                continue;
            }
            checkPos = new Pos(fallingSand.x + 1, fallingSand.y + 1);
            if (map.get(checkPos) == null) {
                fallingSand.x++;
                fallingSand.y++;
                continue;
            }

            // Sand kann sich nicht mehr bewegen
            map.put(fallingSand, SAND);

            // Neuer Sand
            fallingSand = new Pos(500, 0);
            sandUnits++;
//            out(sandUnits);
//
//            out(toString());
//            out("");
        }

        out("Beendet nach sandUnits: " + sandUnits);
    }


    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

//        builder.append("xMin: ").append(xMin);
//        builder.append("\r\nxMax: ").append(xMax);

        for (int y = 0; y <= yMax; y++) {
            builder.append("\r\n ").append(y).append(" ");
            for (int x = xMin; x <= xMax; x++) {
                // Fallender Sand > ueberlagern
                if (fallingSand != null && fallingSand.x == x && fallingSand.y == y)
                    builder.append("+");
                else {
                    Integer val = map.get(new Pos(x, y));
                    if (val == null)
                        builder.append(".");
                    else if (val == ROCK)
                        builder.append("#");
                    else if (val == SAND)
                        builder.append("o");
                }
            }
        }

        return builder.toString();
    }

    public String toStringSVG() {
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y <= yMax; y++) {
            builder.append("\r\n ");
            for (int x = xMin; x <= xMax; x++) {

//                if (fallingSand != null && fallingSand.x == x && fallingSand.y == y)
//                    builder.append("+");
//                else {
                Integer val = map.get(new Pos(x, y));
                if (val == null)
                    builder.append("");
                else if (val == ROCK)
                    builder.append("<rect style=\"fill:#808080;\" width=\"1\" height=\"1\" x=\"" + x + "\" y=\"" + y + "\" />");
                else if (val == SAND)
                    builder.append("<rect style=\"fill:#EFE4B0;\" width=\"1\" height=\"1\" x=\"" + x + "\" y=\"" + y + "\" />");
//                }
            }
        }

        return builder.toString();
    }
}
