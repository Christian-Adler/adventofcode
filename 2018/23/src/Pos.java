import java.util.Objects;

public class Pos {

    int x = 0;
    int y = 0;
    int z = 0;
    String color = null;

    public Pos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Pos(int x, int y, int z, String color) {
        this(x, y, z);
        this.color = color;
    }

    void add(Pos add) {
        this.x += add.x;
        this.y += add.y;
    }

    Pos addToNew(int x, int y, int z) {
        return new Pos(this.x + x, this.y + y, this.z + z);
    }

    Pos addToNew(Pos other) {
        return addToNew(other.x, other.y, other.z);
    }


    Pos copy() {
        return new Pos(x, y, z, color);
    }


    /**
     * https://en.wikipedia.org/wiki/Taxicab_geometry
     *
     * @return
     */
    int manhattanDistance() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    int manhattanDistance(Pos other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
    }


    @Override
    public String toString() {
        return "Pos{" + "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
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
}
