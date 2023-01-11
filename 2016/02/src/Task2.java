import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task2 {

    Map<String, Pos> directionMap = new HashMap<>();

    Pos actPos = new Pos(-2, 0); // [(-1,-1),(1,1)]

    List<String> numbers = new ArrayList<>();

    public void init() {
        directionMap.put("U", new Pos(0, -1));
        directionMap.put("D", new Pos(0, 1));
        directionMap.put("L", new Pos(-1, 0));
        directionMap.put("R", new Pos(1, 0));
    }

    public void addLine(String input) {
        out("===============", input);
        out("actPos:", getNoFromPos(actPos), actPos);
        ArrayList<String> instructions = Util.str2List(input);
        for (String instruction : instructions) {
            out("instruction", instruction);
            actPos = addToPosLimited(actPos, directionMap.get(instruction));
            out("actPos:", getNoFromPos(actPos), actPos);
        }
        numbers.add(getNoFromPos(actPos));
        out("actPos after full row:", getNoFromPos(actPos), actPos);
    }

    String getNoFromPos(Pos pos) {
        //        1
        //      2 3 4
        //    5 6 7 8 9
        //      A B C
        //        D
        if (pos.y == -2) return String.valueOf(1);
        if (pos.y == -1) return String.valueOf(pos.x + 3);
        if (pos.y == 0) return String.valueOf(pos.x + 7);
        if (pos.y == 1) {
            if (pos.x == -1) return "A";
            if (pos.x == 0) return "B";
            if (pos.x == 1) return "C";
        }
        if (pos.y == 2) return "D";

        return "<INVALID>";
    }

    Pos addToPosLimited(Pos pos, Pos add) {
        int minLimit = 0;
        int maxLimit = 0;
        if (add.x != 0) {
            // if (pos.y == -2 || pos.y == 2) { /**/}
            if (pos.y == -1 || pos.y == 1) {
                minLimit = -1;
                maxLimit = 1;
            } else if (pos.y == 0) {
                minLimit = -2;
                maxLimit = 2;
            }
            Pos tmp = pos.addToNew(add);
            return new Pos(limited(tmp.x, minLimit, maxLimit), tmp.y);
        } else {
            // if (pos.x == -2 || pos.x == 2) { /**/}
            if (pos.x == -1 || pos.x == 1) {
                minLimit = -1;
                maxLimit = 1;
            } else if (pos.x == 0) {
                minLimit = -2;
                maxLimit = 2;
            }
            Pos tmp = pos.addToNew(add);
            return new Pos(tmp.x, limited(tmp.y, minLimit, maxLimit));
        }
    }

    int limited(int i, int min, int max) {
        return Math.max(min, Math.min(max, i));
    }

    public void afterParse() {
        out(numbers.stream().collect(Collectors.joining()));
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
