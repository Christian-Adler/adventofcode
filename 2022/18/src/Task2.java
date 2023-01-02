import java.util.*;

public class Task2 {

    Set<Pos> positions = new HashSet<>();
    Map<Pos, Cube> cubes = new HashMap<>();

    long xMin = Long.MAX_VALUE;
    long xMax = Long.MIN_VALUE;
    long yMin = Long.MAX_VALUE;
    long yMax = Long.MIN_VALUE;
    long zMin = Long.MAX_VALUE;
    long zMax = Long.MIN_VALUE;

    public void init() {
    }

    public void addLine(String input) {
        positions.add(new Pos(input));
    }

    public void afterParse() {
        for (Pos pos : positions) {
            Cube cube = new Cube(pos);
            cubes.put(pos, cube);

            xMin = Math.min(xMin, pos.x);
            xMax = Math.max(xMax, pos.x);
            yMin = Math.min(yMin, pos.y);
            yMax = Math.max(yMax, pos.y);
            zMin = Math.min(zMin, pos.z);
            zMax = Math.max(zMax, pos.z);
        }

        long amountVoxels = (xMax - xMin + 2) * (yMax - yMin + 2) * (zMax - zMin + 2);
        out("amount voxels: " + amountVoxels);
        long step = 0;

        ArrayList<Pos> workList = new ArrayList<>();
        Set<Pos> alreadyAdded = new HashSet<>();

        Pos air1 = new Pos(0, 0, 0);
        workList.add(air1); // air
        alreadyAdded.add(air1);

        long sides = 0;

        while (!workList.isEmpty()) {
            step++;
            if (step % 100 == 0)
                out(step + " / " + amountVoxels);

            Pos air = workList.remove(0);

            checkForNextAirNeighbors(workList, alreadyAdded, air);

            sides += air.surroundedCount(positions);
        }

        out("Surface: " + sides);
    }

    private void checkForNextAirNeighbors(ArrayList<Pos> workList, Set<Pos> alreadyAdded, Pos pos) {
        Set<Pos> possibleNeighbors = new HashSet<>();
        possibleNeighbors.add(new Pos(pos.x + 1, pos.y, pos.z));
        possibleNeighbors.add(new Pos(pos.x - 1, pos.y, pos.z));
        possibleNeighbors.add(new Pos(pos.x, pos.y + 1, pos.z));
        possibleNeighbors.add(new Pos(pos.x, pos.y - 1, pos.z));
        possibleNeighbors.add(new Pos(pos.x, pos.y, pos.z + 1));
        possibleNeighbors.add(new Pos(pos.x, pos.y, pos.z - 1));

        for (Pos possibleNeighbor : possibleNeighbors) {
            if (possibleNeighbor.x >= xMin - 1 && possibleNeighbor.x <= xMax + 1
                    && possibleNeighbor.y >= yMin - 1 && possibleNeighbor.y <= yMax + 1
                    && possibleNeighbor.z >= zMin - 1 && possibleNeighbor.z <= zMax + 1)
                if (!positions.contains(possibleNeighbor) && !alreadyAdded.contains(possibleNeighbor)) {
                    alreadyAdded.add(possibleNeighbor);
                    workList.add(possibleNeighbor);
                }
        }
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
