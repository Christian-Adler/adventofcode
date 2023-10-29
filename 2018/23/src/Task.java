import java.util.*;

public class Task {
    ArrayList<Nanobot> nanobots = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        int[] ints = Arrays.stream(Util.cleanFrom(input, "pos=<", ">", " r=").split(",")).mapToInt(Integer::parseInt).toArray();
        if (ints.length != 4) throw new IllegalArgumentException("Invalid input");
        Nanobot n = new Nanobot(new Pos(ints[0], ints[1], ints[2]), ints[3]);
        nanobots.add(n);
    }

    public void afterParse() {
        Nanobot strongest = nanobots.stream().max(Comparator.comparingInt(o -> o.range)).orElse(null);

        long count = nanobots.stream().filter(n -> strongest.pos.manhattanDistance(n.pos) <= strongest.range).count();
        out("Part1", "count", count);

        // Part 2

        // getUnionCuboid
        Cuboid union = null;
        for (Nanobot nanobot : nanobots) {
            Cuboid c = nanobot.getCuboid();
            if (union == null)
                union = c;
            else
                union = union.union(c);
        }

        out(union);

        int maxMiniCubeIntersections = 0;

        Set<Cuboid> miniCubes = new HashSet<>();

        PriorityQueue<Cuboid> workList = new PriorityQueue<>((c1, c2) -> Integer.compare(c1.nanobotsInRange, c2.nanobotsInRange) * -1);
        workList.add(union);

        while (!workList.isEmpty()) {

            Cuboid actCube = workList.poll();
            // Seitenlaegne 1? Dann muessen nun alle Ecken geprueft werden.
            if (actCube.isMiniCube()) {
                miniCubes.add(actCube);
                if (actCube.nanobotsInRange > maxMiniCubeIntersections)
                    maxMiniCubeIntersections = actCube.nanobotsInRange;
                continue;
            }

            List<Cuboid> toCheckList = actCube.split2();
            for (Cuboid checkCuboid : toCheckList) {
                for (Nanobot nanobot : nanobots) {
                    if (nanobot.isInRange(checkCuboid))
                        checkCuboid.nanobotsInRange++;
                }

                if (checkCuboid.nanobotsInRange >= maxMiniCubeIntersections)
                    workList.add(checkCuboid);
            }

        }


//        out(miniCubes.size());
//        out("miniCubes", miniCubes);

        // Pos zu Anzahl in Range fuer alle Ecken der 1x1x1 Cubes ermitteln
        Map<Pos, Long> pos2InRangeOf = new HashMap<>();
        for (Cuboid cuboid : miniCubes) {
            for (Pos corner : cuboid.getAllCorners()) {
                long inRangeCount = nanobots.stream().filter(n -> n.isInRange(corner)).count();
                pos2InRangeOf.put(corner, inRangeCount);
            }
        }

        // Maximale in Range Positions
        long maxInRange = 0;
        Set<Pos> maxInRangePositions = new HashSet<>();
        for (Map.Entry<Pos, Long> entry : pos2InRangeOf.entrySet()) {
            Long inRange = entry.getValue();
            if (inRange > maxInRange) {
                maxInRange = inRange;
                maxInRangePositions.clear();
            }
            if (inRange == maxInRange)
                maxInRangePositions.add(entry.getKey());
        }

        out("maxInRange", maxInRange);

        // Kleineste Distanz finden
        int minManhattenDist = Integer.MAX_VALUE;
        for (Pos position : maxInRangePositions) {
            int dist = position.manhattanDistance();
            if (dist < minManhattenDist)
                minManhattenDist = dist;
        }

        out("minManhattenDist", minManhattenDist);
    }

    private Nanobot getRandomNanobot() {
        int rndIdx = getRandomNumberUsingNextInt(0, nanobots.size());
        Nanobot nano = nanobots.get(rndIdx);
        return nano;
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


    private void part2primitive() {
        // maxima finden
        int xMin = Integer.MAX_VALUE;
        int yMin = Integer.MAX_VALUE;
        int zMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMax = Integer.MIN_VALUE;
        int zMax = Integer.MIN_VALUE;

        for (Nanobot nanobot : nanobots) {
            xMin = Math.min(xMin, nanobot.pos.x);
            xMax = Math.max(xMax, nanobot.pos.x);
            yMin = Math.min(yMin, nanobot.pos.y);
            yMax = Math.max(yMax, nanobot.pos.y);
            zMin = Math.min(zMin, nanobot.pos.z);
            zMax = Math.max(zMax, nanobot.pos.z);
        }

        out(xMin, xMax);
        out(yMin, yMax);
        out(zMin, zMax);


        Map<Pos, Long> pos2InRangeOf = new HashMap<>();
        for (int z = zMin; z <= zMax; z++) {
//            if (z % 10 == 0)
            out(z, "/", zMin, "-", zMax);
            for (int y = yMin; y <= yMax; y++) {
                for (int x = xMin; x <= xMax; x++) {
                    Pos p = new Pos(x, y, z);
                    long inRangeCount = nanobots.stream().filter(n -> n.isInRange(p)).count();
                    if (inRangeCount > 1)
                        pos2InRangeOf.put(p, inRangeCount);
                }
            }
        }

        long maxInRange = 0;
        Set<Pos> positions = new HashSet<>();
        for (Map.Entry<Pos, Long> entry : pos2InRangeOf.entrySet()) {
            Long inRange = entry.getValue();
            if (inRange > maxInRange) {
                maxInRange = inRange;
                positions.clear();
            }
            if (inRange == maxInRange)
                positions.add(entry.getKey());
        }

        out("maxInRange", maxInRange);
        out(positions);

        int minManhattenDist = Integer.MAX_VALUE;
        for (Pos position : positions) {
            int dist = position.manhattanDistance();
            if (dist < minManhattenDist)
                minManhattenDist = dist;
        }
        out("minManhattenDist", minManhattenDist);
    }

    public void out(Object... str) {
        Util.out(str);
    }
}
