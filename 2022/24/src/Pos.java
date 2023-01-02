import java.util.Objects;

public class Pos {
    int x = 0;
    int y = 0;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void add(Pos add) {
        this.x += add.x;
        this.y += add.y;
    }

    Pos addTo(int x, int y) {
        return new Pos(this.x + x, this.y + y);
    }

    Pos copy() {
        return new Pos(x, y);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pos{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
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
}
