import java.util.Objects;

public class Pos {
    long x = -1;
    long y = -1;

    public Pos(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long distance(Pos p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return x == pos.x && y == pos.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
