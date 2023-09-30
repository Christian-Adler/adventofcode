import java.util.HashMap;
import java.util.Map;

public class Task2 {
    Map<Pos, String> grid = new HashMap<>();

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
                grid.put(new Pos(x - (initialGridWidth - 1) / 2, addLineY), "#");
            }
        }
        addLineY--;
    }

    public void afterParse() {
        long infectionCounter = 0;
        Pos virusCarrier = new Pos(0, 0);
        Pos direction = new Pos(0, 1);

        for (int i = 0; i < 10000000; i++) {
            String state = grid.get(virusCarrier);
            if (state == null) {
                direction = direction.rotate90DegToNew(true);
            } else if (state.equals("#")) {
                direction = direction.rotate90DegToNew(false);
            } else if (state.equals("F")) {
                direction = direction.rotate90DegToNew(true).rotate90DegToNew(true);
            }

            if (state == null)
                grid.put(virusCarrier.copy(), "W");
            else if (state.equals("W")) {
                grid.put(virusCarrier.copy(), "#");
                infectionCounter++;
            } else if (state.equals("#")) {
                grid.put(virusCarrier.copy(), "F");
            } else if (state.equals("F")) {
                grid.remove(virusCarrier.copy());
            }

            virusCarrier.add(direction);
//            out(toString());
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
        for (Pos pos : grid.keySet()) {
            xMin = Math.min(xMin, pos.x);
            yMin = Math.min(yMin, pos.y);
            xMax = Math.max(xMax, pos.x);
            yMax = Math.max(yMax, pos.y);
        }
        StringBuilder builder = new StringBuilder();
        for (int y = yMax; y >= yMin; y--) {
            for (int x = xMin; x <= xMax; x++) {
                builder.append(grid.getOrDefault(new Pos(x, y), "."));
            }
            builder.append("\r\n");
        }
        return builder.toString();
    }

    public String toStringSVG() {
        SVG svg = new SVG();
        for (Pos pos : grid.keySet()) {
//            svg.add(
//
//            );
        }
        return svg.toSVGStringAged();
    }
}
