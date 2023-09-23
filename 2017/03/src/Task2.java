import java.util.HashMap;
import java.util.Map;

public class Task2 {

    Map<Pos, Integer> map = new HashMap<>();
    int value = 0;

    public void init() {
    }

    public void addLine(String input) {
        value = Integer.parseInt(input);
    }

    public void afterParse() {
        int stepX = 1;
        int stepY = 0;

        Pos p = new Pos(0, 0);
        map.put(p, 1);

        Pos actPos = p.copy();

        int sumNeighbors = 0;
        while (sumNeighbors <= value) {
//            counter++;
            actPos = actPos.addToNew(stepX, stepY);

            sumNeighbors = 0;
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0) continue;

                    sumNeighbors += map.getOrDefault(actPos.addToNew(x, y), 0);
                }
            }

            map.put(actPos, sumNeighbors);

            if (stepX > 0 && !map.containsKey(actPos.addToNew(0, 1))) {
                stepX = 0;
                stepY = 1;
            } else if (stepY > 0 && !map.containsKey(actPos.addToNew(-1, 0))) {
                stepX = -1;
                stepY = 0;
            } else if (stepX < 0 && !map.containsKey(actPos.addToNew(0, -1))) {
                stepX = 0;
                stepY = -1;
            } else if (stepY < 0 && !map.containsKey(actPos.addToNew(1, 0))) {
                stepX = 1;
                stepY = 0;
            }

//            out(map, stepX, stepY);
        }

        out(map);
        out(sumNeighbors, actPos);

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
