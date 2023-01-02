import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Pos {
    long x = 0;
    long y = 0;
    long z = 0;

    public Pos(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Pos(String intput) {
        String[] split = intput.split(",");
        this.x = Integer.parseInt(split[0]);
        this.y = Integer.parseInt(split[1]);
        this.z = Integer.parseInt(split[2]);
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return x == pos.x && y == pos.y && z == pos.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    boolean isSurrounded(Map<Pos, Cube> others) {
        Set<Pos> possibleNeighbors = new HashSet<>();
        possibleNeighbors.add(new Pos(x + 1, y, z));
        possibleNeighbors.add(new Pos(x - 1, y, z));
        possibleNeighbors.add(new Pos(x, y + 1, z));
        possibleNeighbors.add(new Pos(x, y - 1, z));
        possibleNeighbors.add(new Pos(x, y, z + 1));
        possibleNeighbors.add(new Pos(x, y, z - 1));

        for (Pos possibleNeighbor : possibleNeighbors) {
            Cube neighbor = others.get(possibleNeighbor);
            if (neighbor == null)
                return false;
        }
        return true;
    }

    long surroundedCount(Set<Pos> others) {
        Set<Pos> possibleNeighbors = new HashSet<>();
        possibleNeighbors.add(new Pos(x + 1, y, z));
        possibleNeighbors.add(new Pos(x - 1, y, z));
        possibleNeighbors.add(new Pos(x, y + 1, z));
        possibleNeighbors.add(new Pos(x, y - 1, z));
        possibleNeighbors.add(new Pos(x, y, z + 1));
        possibleNeighbors.add(new Pos(x, y, z - 1));

        long count = 0;
        for (Pos possibleNeighbor : possibleNeighbors) {
            if (others.contains(possibleNeighbor))
                count++;
        }
        return count;
    }
}
