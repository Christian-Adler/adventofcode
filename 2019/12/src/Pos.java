import java.util.Objects;

public class Pos {

    int x = 0;
    int y = 0;
    int z = 0;

    public Pos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    void add(Pos add) {
        this.x += add.x;
        this.y += add.y;
        this.z += add.z;
    }

    Pos addToNew(int x, int y, int z) {
        return new Pos(this.x + x, this.y + y, this.z + z);
    }

    Pos addToNew(Pos other) {
        return addToNew(other.x, other.y, other.z);
    }

    Pos copy() {
        return new Pos(x, y, z);
    }


    @Override
    public String toString() {
        return "<" + "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '>';
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
