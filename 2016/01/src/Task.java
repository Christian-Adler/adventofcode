import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task {

    Pos startPos = new Pos(0, 0);
    List<Pos> visits = new ArrayList<>();
    String[] instructions = null;

    public void init() {
        visits.add(startPos);
    }

    public void addLine(String input) {
        instructions = input.split(", ");
    }

    public void afterParse() {
        Pos actPos = startPos.copy();
        Pos direction = new Pos(0, 1);
        for (String instruction : instructions) {
            boolean turnLeft = instruction.startsWith("L");
            int steps = Integer.parseInt(instruction.substring(1));

            direction = direction.rotate90DegToNew(turnLeft);
//            out(direction);
            actPos = actPos.addToNew(direction.multToNew(steps));
//            out(actPos);


            visits.add(actPos.copy());
        }

        long distance = actPos.manhattanDistance();
        out("distance", distance);
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

        int manhattanDistance = -1;
        Set<Pos> allPos = new HashSet<>();

        SVG svg = new SVG();
        Pos prev = null;
        for (Pos visit : visits) {
            if (prev != null) {
                List<Pos> path = prev.pathBetween(visit);
                // alle ins svg
                for (Pos pos : path) {
                    if (!allPos.add(pos) && manhattanDistance < 0) {
                        manhattanDistance = pos.manhattanDistance();
                        out("part 2: ", manhattanDistance);
                        svg.add(pos, "#ff0000");
                    } else
                        svg.add(pos, "#202020");
                }
            }
            svg.add(visit);
            prev = visit;
        }
        return svg.toSVGStringAged();
    }
}
