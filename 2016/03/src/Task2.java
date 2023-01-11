import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
    int possibleTriangles = 0;
    final String regex = "\\d+";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    Map<Integer, List<Integer>> lists = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {

        if (lists.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                lists.put(i, new ArrayList<>());
            }
        }

        final Matcher matcher = pattern.matcher(input);

        int listIdx = 0;
        while (matcher.find()) {
            lists.get(listIdx).add(Integer.parseInt(matcher.group(0)));
            listIdx++;
        }

        if (lists.get(0).size() == 3) {
            for (int i = 0; i < 3; i++) {
                checkOnPossibleTriangle(lists.get(i));
            }
            lists.clear();
        }

    }

    private void checkOnPossibleTriangle(List<Integer> sides) {
        if (sides.size() != 3)
            out("invalid input");

        boolean isTriangle = true;
        for (int i = 0; i < sides.size(); i++) {
            int side1 = sides.get(i);

            int side2 = sides.get((i + 1) % 3);
            int side3 = sides.get((i + 2) % 3);

            if (side2 + side3 <= side1) {
                isTriangle = false;
                break;
            }
        }

        if (isTriangle) {
            possibleTriangles++;
//            out("pos triangle: ", sides);
        }
    }

    public void afterParse() {
        out("possibleTriangles", possibleTriangles);
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
