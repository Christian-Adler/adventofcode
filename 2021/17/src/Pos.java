import java.util.Objects;

public class Pos {
    public int x = 0;
    public int y = 0;
    public int step = 0;

    public Pos(int x, int y, int step) {
        this.x = x;
        this.y = y;
        this.step = step;
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
                ", step=" + step +
                '}';
    }
}
