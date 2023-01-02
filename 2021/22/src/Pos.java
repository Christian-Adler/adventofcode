import java.util.Objects;

public class Pos {
    public final int x;
    public final int y;
    public final int z;

    public Pos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean useShort) {
        if (useShort)
            return x + "," + y + "," + z;
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pos)) return false;
        Pos pos = (Pos) o;
        return x == pos.x && y == pos.y && z == pos.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public int compareTo(Pos other) {
        int compareValue = 0;
        compareValue = Integer.compare(x, other.x);
        if (compareValue == 0)
            compareValue = Integer.compare(y, other.y);
        if (compareValue == 0)
            compareValue = Integer.compare(z, other.z);
        return compareValue;
    }
}
