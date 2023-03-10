import java.util.Objects;

public class Pos {
    String name = "";
    int x = 0;
    int y = 0;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos(int x, int y, Object name) {
        this(x, y);
        this.name = "" + name;
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
                ", name=" + name +
                '}';
    }
}
