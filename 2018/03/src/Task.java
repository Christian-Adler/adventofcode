import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
    Map<Pos, Integer> map = new HashMap<>();
    List<String> inputs = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        inputs.add(input);
        String[] splitAt = input.split("@");
        String[] splitCollon = splitAt[1].split(":");
        String[] splitNumsPos = splitCollon[0].split(",");
        String[] splitNumsSize = splitCollon[1].split("x");
        int x = Integer.parseInt(splitNumsPos[0].trim());
        int y = Integer.parseInt(splitNumsPos[1].trim());
        int w = Integer.parseInt(splitNumsSize[0].trim());
        int h = Integer.parseInt(splitNumsSize[1].trim());
        for (int hY = 0; hY < h; hY++) {
            for (int wX = 0; wX < w; wX++) {
                Pos p = new Pos(wX + x, hY + y);
                map.put(p, map.getOrDefault(p, 0) + 1);
            }
        }
    }

    public void afterParse() {
        long overlaps = map.values().stream().filter(v -> v > 1).count();
        out("Part1 overlaps", overlaps);

        // run all inputs again
        for (String input : inputs) {
            String[] splitAt = input.split("@");
            String[] splitCollon = splitAt[1].split(":");
            String[] splitNumsPos = splitCollon[0].split(",");
            String[] splitNumsSize = splitCollon[1].split("x");
            int x = Integer.parseInt(splitNumsPos[0].trim());
            int y = Integer.parseInt(splitNumsPos[1].trim());
            int w = Integer.parseInt(splitNumsSize[0].trim());
            int h = Integer.parseInt(splitNumsSize[1].trim());
            boolean foundOnly1s = true;
            for (int hY = 0; hY < h; hY++) {
                if (!foundOnly1s) break;
                for (int wX = 0; wX < w; wX++) {
                    Pos p = new Pos(wX + x, hY + y);
                    Integer value = map.getOrDefault(p, 0);
                    if (value != 1) {
                        foundOnly1s = false;
                        break;
                    }
                }
            }

            if (foundOnly1s) {
                out("Part 2 ID", splitAt[0]);
                break;
            }
        }
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
