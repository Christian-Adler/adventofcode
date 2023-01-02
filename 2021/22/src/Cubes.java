import java.util.HashSet;
import java.util.Set;

public class Cubes {
    private final Set<Pos> activeCubes = new HashSet<>();
    private final Cuboid initRegion;

    public Cubes(Cuboid initRegion) {
        this.initRegion = initRegion;
    }

    public void add(String line) {
        String[] split = line.split(" ");
        boolean on = split[0].equalsIgnoreCase("on");

        Cuboid cuboid = Cuboid.parse(split[1]);

        // Inside init
        if (!initRegion.contains(cuboid))
            return;

        // System.out.println(on + " :\r\n" + cuboid.toStr());

        if (on)
            activeCubes.addAll(cuboid.toPositions());
        else
            activeCubes.removeAll(cuboid.toPositions());
    }

    public void filter(Cuboid cuboid) {
        Set<Pos> filtered = cuboid.filter(activeCubes);
        System.out.println("Filtered: " + filtered.size());
    }

    public String toString() {
        return String.valueOf(activeCubes.size());
    }

}
