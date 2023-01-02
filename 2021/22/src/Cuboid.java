import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Cuboid {
    public final Range xRange;
    public final Range yRange;
    public final Range zRange;

    public Cuboid(Range xRange, Range yRange, Range zRange) {
        this.xRange = xRange;
        this.yRange = yRange;
        this.zRange = zRange;
    }

    public static Cuboid parse(String input) {
        List<Range> ranges = new ArrayList<>();
        String[] coordRanges = input.replace("x=", "").replace("y=", "").replace("z=", "").split(",");
        for (String coordRange : coordRanges) {
            ranges.add(Range.parse(coordRange));
        }
        return new Cuboid(ranges.get(0), ranges.get(1), ranges.get(2));
    }

    public Set<Pos> toPositions() {
        Set<Pos> positions = new HashSet<>();
        for (int x = xRange.min; x <= xRange.max; x++) {
            for (int y = yRange.min; y <= yRange.max; y++) {
                for (int z = zRange.min; z <= zRange.max; z++) {
                    positions.add(new Pos(x, y, z));
                }
            }
        }
        return positions;
    }

    public Set<Pos> filter(Set<Pos> input) {
//        AtomicInteger counter = new AtomicInteger();
        Set<Pos> filered = input.stream().filter(pos -> {
//            int c = counter.incrementAndGet();
//            if (c % 100 == 0)
//                System.out.println(c);
            return contains(pos);
        }).collect(Collectors.toSet());
        return filered;
    }

    public boolean contains(Pos pos) {
        return xRange.containts(pos.x) && yRange.containts(pos.y) && zRange.containts(pos.z);
    }

    public boolean contains(Cuboid other) {
        return xRange.contains(other.xRange) && yRange.contains(other.yRange) && zRange.contains(other.zRange);
    }

    @Override
    public String toString() {
        return "Cuboid{" +
                "x=" + xRange +
                ", y=" + yRange +
                ", z=" + zRange +
                '}';
    }

    public String toStr() {
        return new ArrayList<>(toPositions()).stream().sorted(Pos::compareTo).map(pos -> pos.toString(true)).collect(Collectors.joining("\r\n"));
    }
}
