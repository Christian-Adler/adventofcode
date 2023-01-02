import java.util.Objects;

public class Pos {
    public int x = -1;
    public int y = -1;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos(String line) {
        this(line.split(","));
    }

    private Pos(String[] split) {
        this(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
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
