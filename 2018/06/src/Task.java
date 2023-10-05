import java.util.*;

public class Task {
    List<Pos> coordinates = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split(",");
        Pos p = new Pos(Integer.parseInt(split[0].trim()), Integer.parseInt(split[1].trim()));
        coordinates.add(p);
    }

    public void afterParse(int part2SumDist) {
        int xMin = Integer.MAX_VALUE;
        int yMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMax = Integer.MIN_VALUE;

        for (Pos pos : coordinates) {
            xMin = Math.min(xMin, pos.x);
            xMax = Math.max(xMax, pos.x);
            yMin = Math.min(yMin, pos.y);
            yMax = Math.max(yMax, pos.y);
        }
        xMin--;
        yMin--;
        xMax++;
        yMax++;
        out(xMin, yMin, xMax, yMax);

        Map<Pos, Integer> posToCoordIdx = new HashMap<>();
        Set<Integer> infiniteAreasCoordIdx = new HashSet<>();

        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                Pos p = new Pos(x, y);
                boolean addToInfinite = y == 0 || x == 0 || y == yMax || x == xMax;

                int minDist = Integer.MAX_VALUE;
                int minDistCoordIdx = -1;

                for (int i = 0; i < coordinates.size(); i++) {
                    Pos coordinate = coordinates.get(i);
                    int dist = coordinate.manhattanDistance(p);
                    if (dist < minDist) {
                        minDist = dist;
                        minDistCoordIdx = i;
                    } else if (dist == minDist)
                        minDistCoordIdx = -1; // gleicher Abstand zu 2 - also nix
                }

                posToCoordIdx.put(p, minDistCoordIdx);

                if (minDistCoordIdx >= 0 && addToInfinite)
                    infiniteAreasCoordIdx.add(minDistCoordIdx);
            }
        }

        Map<Integer, Integer> mapCoordIdx2Count = new HashMap<>();
        for (Integer coordIdx : posToCoordIdx.values()) {
            mapCoordIdx2Count.put(coordIdx, mapCoordIdx2Count.getOrDefault(coordIdx, 0) + 1);
        }

        // infinite entfernen
        for (Integer coordIdx : infiniteAreasCoordIdx) {
            mapCoordIdx2Count.remove(coordIdx);
        }

//        out(mapCoordIdx2Count);
        Integer maxFiniteArea = mapCoordIdx2Count.values().stream().max(Integer::compareTo).orElse(-1);
        out("Part 1 - maxFiniteArea", maxFiniteArea);

        // Part 2
        int countSumDistancesLess = 0;
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                Pos p = new Pos(x, y);
                int sumDist = 0;
                for (Pos coordinate : coordinates) {
                    int dist = coordinate.manhattanDistance(p);
                    sumDist += dist;
                }

                if (sumDist < part2SumDist)
                    countSumDistancesLess++;
            }
        }

        out("Part 2: region size:", countSumDistancesLess);
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
