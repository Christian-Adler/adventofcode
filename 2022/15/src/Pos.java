import java.util.Objects;

public class Pos {
    int x = -1;
    int y = -1;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos(String str) {
        String[] split = str.replace("x=", "").replace(" y=", "").split(",");
        this.x = Integer.parseInt(split[0]);
        this.y = Integer.parseInt(split[1]);
    }

    public int distance(Pos p) {
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
