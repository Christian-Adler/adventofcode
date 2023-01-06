import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {

    List<Ingredient> ingredients = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        String cleaned = cleanFrom(input, ": capacity", ", durability", ", flavor", ", texture", ", calories");
        Ingredient ingredient = new Ingredient(cleaned);
        ingredients.add(ingredient);
    }

    public void afterParse() {
        out(ingredients);

        Map<Ingredient, Integer> map = new HashMap<>();
//        Iterator<Ingredient> it = ingredients.iterator();
//        while (it.hasNext()) {
//            Ingredient in = it.next();
//            if (in.name.startsWith("B"))
//                map.put(in, 44);
//            else map.put(in, 56);
//        }
//        out(calcRecipeScore(map));

        out(findMaxRecipeScore(map, 0));
    }

    long findMaxRecipeScore(Map<Ingredient, Integer> map, int startIdx) {
        int amountSpoons = map.values().stream().mapToInt(In -> In).sum();
//        if (amountSpoons == 100)
//            out(amountSpoons);
//
//        // optimierungen
//        // 75 Teile und negativ? Dann abbruch
//        for (Map.Entry<Ingredient, Integer> entry : map.entrySet()) {
//            if (entry.getValue() >= 75) {
////                Ingredient in = entry.getKey();
////                if (in.capacity < 0 || in.durability < 0 || in.flavor < 0 || in.texture < 0)
//                return 0;
//            }
//        }

        if (amountSpoons == 100) {
            long score = calcRecipeScore(map);
            if (score == 3600000000l)
                out(map);
            return score;
        }

        long maxScore = 0;

        for (int i = startIdx; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);

            HashMap<Ingredient, Integer> nextMap = new HashMap<>(map);
            nextMap.put(ingredient, nextMap.getOrDefault(ingredient, 0) + 1);
            long score = findMaxRecipeScore(nextMap, i);
            maxScore = Math.max(score, maxScore);
        }

        return maxScore;
    }

    long calcRecipeScore(Map<Ingredient, Integer> map) {
        if (map.values().stream().mapToInt(In -> In).sum() != 100)
            throw new IllegalArgumentException("Not 100 spoons!");

        long capacity = 0;
        long durability = 0;
        long flavor = 0;
        long texture = 0;
        long calories = 0;

        for (Map.Entry<Ingredient, Integer> entry : map.entrySet()) {
            long count = entry.getValue();
            Ingredient ingredient = entry.getKey();
            capacity += ingredient.capacity * count;
            durability += ingredient.durability * count;
            flavor += ingredient.flavor * count;
            texture += ingredient.texture * count;
        }

        if (capacity < 0 || durability < 0 || flavor < 0 || texture < 0)
            return 0;
        long score = capacity * durability * flavor * texture;

        return score;
    }

    public void out(Object... str) {
        String out = "";
        for (Object o : str) {
            if (out.length() > 0)
                out += " ";
            out += o;
        }
        System.out.println(out);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            result = result.replace(string, "");
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
