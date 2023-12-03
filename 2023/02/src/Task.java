import java.util.HashMap;
import java.util.Map;

public class Task {
    private final Map<String, Integer> maxValues = new HashMap<>();

    private long sumGameIDs = 0;
    private long sumPowers = 0;

    public void init() {
        maxValues.put("red", 12);
        maxValues.put("green", 13);
        maxValues.put("blue", 14);
    }

    public void addLine(String input) {
        String[] split1 = input.split(":");
        int gameId = Integer.parseInt(split1[0].replace("Game", "").trim());
//        out(gameId);

        boolean matchesMax = true;
        Map<String, Integer> minRequiredPerGame = new HashMap<>();
        minRequiredPerGame.put("blue", 0);
        minRequiredPerGame.put("red", 0);
        minRequiredPerGame.put("green", 0);

        String[] rounds = split1[1].split(";");
        for (String round : rounds) {
            String[] coloredCubes = round.split(",");
            Map<String, Integer> map = new HashMap<>();
            map.put("blue", 0);
            map.put("red", 0);
            map.put("green", 0);
            for (String coloredCube : coloredCubes) {
                String[] numAndColor = coloredCube.trim().split(" ");
                String color = numAndColor[1].trim();
                int value = Integer.parseInt(numAndColor[0].trim());
                map.put(color, value);

                minRequiredPerGame.put(color, Math.max(value, minRequiredPerGame.get(color)));
            }
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() > maxValues.get(entry.getKey())) {
                    matchesMax = false;
                    break;
                }
            }

        }
        if (matchesMax)
            sumGameIDs += gameId;

        long multipliedMinColorValues = 1;
        for (Integer value : minRequiredPerGame.values()) {
            multipliedMinColorValues *= value;
        }
//        out("power", multipliedMinColorValues);
        sumPowers += multipliedMinColorValues;

    }

    public void afterParse() {
        out("Part 1: sum game ids:", sumGameIDs);
        out("Part 2: sum game powers:", sumPowers);
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
