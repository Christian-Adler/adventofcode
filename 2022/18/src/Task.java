import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task {

    Set<Pos> positions = new HashSet<>();
    Map<Pos, Cube> cubes = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        positions.add(new Pos(input));
    }

    public void afterParse() {
        for (Pos pos : positions) {
            Cube cube = new Cube(pos);
            cubes.put(pos, cube);
        }
        for (Cube cube : cubes.values()) {
            cube.findNeighbors(cubes);
        }

        long sides = 0;
        for (Cube cube : cubes.values()) {
            sides += (6 - cube.neighbors.size());
        }
        out("sides: " + sides);
    }

    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            input = input.replace(string, "");
        }
        return input;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

//        for (Pos position : positions) {
//            builder.append("\r\n").append(position);
//        }
//        for (Cube cube : cubes.values()) {
//            builder.append("\r\n").append(cube);
//        }

        return builder.toString();
    }

    public String toStringSVG() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
