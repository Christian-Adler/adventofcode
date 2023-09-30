import java.util.HashSet;
import java.util.Set;

public class Task {
    Set<Pos> grid = new HashSet<>();

    int initialGridWidth = Integer.MAX_VALUE;
    int addLineY = Integer.MAX_VALUE;

    public void init() {
    }

    public void addLine(String input) {
        if (initialGridWidth == Integer.MAX_VALUE) {
            initialGridWidth = input.length();
            addLineY = (initialGridWidth - 1) / 2;
        }

        String[] split = input.split("");
        for (int x = 0; x < split.length; x++) {
            String s = split[x];
            if (s.equals("#")) {
                grid.add(new Pos(x - (initialGridWidth - 1) / 2, addLineY));
            }
        }
        addLineY--;
    }

    public void afterParse() {
        long infectionCounter = 0;
        Pos virusCarrier = new Pos(0, 0);
        Pos direction = new Pos(0, 1);

        for (int i = 0; i < 10000; i++) {
            boolean infected = grid.contains(virusCarrier);
            if (infected) {
                direction = direction.rotate90DegToNew(false);
            } else {
                direction = direction.rotate90DegToNew(true);
            }

            if (infected)
                grid.remove(virusCarrier.copy());
            else {
                grid.add(virusCarrier.copy());
                infectionCounter++;
            }

            virusCarrier.add(direction);

        }
        out("infectionCounter", infectionCounter);
        out("virusCarrier", virusCarrier);
//        out(toString());
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        int xMin = 0, yMin = 0, xMax = 0, yMax = 0;
        for (Pos pos : grid) {
            xMin = Math.min(xMin, pos.x);
            yMin = Math.min(yMin, pos.y);
            xMax = Math.max(xMax, pos.x);
            yMax = Math.max(yMax, pos.y);
        }
        StringBuilder builder = new StringBuilder();
        for (int y = yMax; y >= yMin; y--) {
            for (int x = xMin; x <= xMax; x++) {
                builder.append(grid.contains(new Pos(x, y)) ? "#" : ".");
            }
            builder.append("\r\n");
        }
        return builder.toString();
    }

    public String toStringSVG() {
        SVG svg = new SVG();
        return svg.toSVGStringAged();
    }
}
