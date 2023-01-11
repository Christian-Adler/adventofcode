import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {
    int possibleTriangles = 0;
    final String regex = "\\d+";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);


    public void init() {
    }

    public void addLine(String input) {
        final Matcher matcher = pattern.matcher(input);
        List<Integer> sides = new ArrayList<>();
        while (matcher.find()) {
            sides.add(Integer.parseInt(matcher.group(0)));
        }
        checkOnPossibleTriangle(sides);
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

        if (isTriangle)
            possibleTriangles++;
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
