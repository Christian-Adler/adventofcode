import java.util.*;

public class Task {
    Pos n;
    Pos s;
    Pos ne;
    Pos se;
    Pos nw;
    Pos sw;

    Map<String, Pos> directions = new HashMap<>();

    List<String> path;

    public void init() {
        n = new Pos(0, 1);
        s = new Pos(0, -1);

        double sin60 = Math.sin(60d / 360d * 2 * Math.PI);
        double cos60 = 0.5d; // = Math.cos(60d / 360d * 2 * Math.PI);

//        out(sin60);
//        out(cos60);

        ne = new Pos(sin60, cos60);
        se = new Pos(sin60, -cos60);
        nw = new Pos(-sin60, cos60);
        sw = new Pos(-sin60, -cos60);

        directions.put("n", n);
        directions.put("ne", ne);
        directions.put("nw", nw);
        directions.put("s", s);
        directions.put("se", se);
        directions.put("sw", sw);
//        out(directions);
    }

    public void addLine(String input) {
        path = new ArrayList<>(Arrays.stream(input.split(",")).toList());
//        out(path);
    }

    public void afterParse() {

        Pos actPos = new Pos(0, 0);
        int maxSteps = 0;
        for (String pathItem : path) {
            actPos.add(directions.get(pathItem));

            int steps = evalFewestPathBackToZero(actPos.copy());
            if (steps > maxSteps)
                maxSteps = steps;
        }
        out(actPos);

        out("end pos fewest steps", evalFewestPathBackToZero(actPos.copy()));
        out("max pos", maxSteps);
//        evalFewestPathBackToZero(maxPos.copy()); // > 1521
    }

    private int evalFewestPathBackToZero(Pos actPos) {
        List<String> shortestPathBackToStart = new ArrayList<>();

        while (actPos.distToZero() > 0.5) {
            Pos minNeighbor = null;
            String minDir = null;
            for (Map.Entry<String, Pos> entry : directions.entrySet()) {
                Pos dir = entry.getValue();
                Pos neighbor = actPos.addToNew(dir);
                if (minNeighbor == null || neighbor.distToZero() < minNeighbor.distToZero()) {
                    minNeighbor = neighbor;
                    minDir = entry.getKey();
                }
            }

            if (minDir == null) throw new IllegalStateException("Found no path");

            shortestPathBackToStart.add(minDir);
            actPos = minNeighbor;
        }

//        out(actPos);
//        out("shortestPathBackToStart", shortestPathBackToStart);
//        out(actPos.distToZero());
//        out("fewest steps:", shortestPathBackToStart.size());
        return shortestPathBackToStart.size();
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
