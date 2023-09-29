import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
    Map<Pos, String> map = new HashMap<>();
    int y = 0;
    Pos startPos = null;

    List<Pos> path = new ArrayList<>();
    List<String> letters = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split("");
        for (int x = 0; x < split.length; x++) {
            String s = split[x].trim();
            if (!s.isEmpty()) {
                Pos pos = new Pos(x, y);
                map.put(pos, s);
                if (y == 0)
                    startPos = pos.copy();
            }
        }
        y++;
    }

    public void afterParse() {
        if (startPos == null) throw new IllegalStateException("invalid start pos");

        Direction direction = Direction.DOWN;
        Pos actPos = startPos.copy();
        while (actPos != null) {
            path.add(actPos.copy());

            switch (direction) {
                case DOWN -> actPos = actPos.addToNew(0, 1);

                case UP -> actPos = actPos.addToNew(0, -1);

                case RIGHT -> actPos = actPos.addToNew(1, 0);

                case LEFT -> actPos = actPos.addToNew(-1, 0);
            }

            String s = map.get(actPos);
            if (s == null) {
                out("finished");
                actPos = null;
                break;
            }
            ;

            if (s.equals("|") || s.equals("-")) {
                // nothing to do
            } else if (s.equals("+")) {
                // Change direction
                if (direction.equals(Direction.DOWN) || direction.equals(Direction.UP)) {
                    direction = map.get(actPos.addToNew(-1, 0)) == null ? Direction.RIGHT : Direction.LEFT;
                } else {
                    direction = map.get(actPos.addToNew(0, -1)) == null ? Direction.DOWN : Direction.UP;
                }
            } else {
                letters.add(s);
            }
        }

        out(String.join("", letters));
        out("steps", path.size());
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {
        SVG svg = new SVG();
        for (Pos pos : path) {
            svg.add(pos);
        }
        return svg.toSVGStringAged();
    }
}
