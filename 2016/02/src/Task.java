import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {

    Map<String, Pos> directionMap = new HashMap<>();

    Pos actPos = new Pos(0, 0); // [(-1,-1),(1,1)]

    List<Integer> numbers = new ArrayList<>();

    public void init() {
        directionMap.put("U", new Pos(0, -1));
        directionMap.put("D", new Pos(0, 1));
        directionMap.put("L", new Pos(-1, 0));
        directionMap.put("R", new Pos(1, 0));
    }

    public void addLine(String input) {
        ArrayList<String> instructions = Util.str2List(input);
        for (String instruction : instructions) {
            actPos = addToPosLimited(actPos, directionMap.get(instruction));
        }
        numbers.add(getNoFromPos(actPos));
    }

    int getNoFromPos(Pos pos) {
        //        1 2 3
        //        4 5 6
        //        7 8 9
        return 3 * (pos.y + 1) + pos.x + 2;
    }

    Pos addToPosLimited(Pos pos, Pos add) {
        Pos tmp = pos.addToNew(add);
        return new Pos(limited(tmp.x), limited(tmp.y));
    }

    int limited(int i) {
        return Math.max(-1, Math.min(1, i));
    }

    public void afterParse() {
        out(numbers);
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
