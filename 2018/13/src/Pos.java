import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pos {
    int x = 0;
    int y = 0;
    String color = null;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos(int x, int y, String color) {
        this(x, y);
        this.color = color;
    }

    void add(Pos add) {
        this.x += add.x;
        this.y += add.y;
    }

    Pos addToNew(int x, int y) {
        return new Pos(this.x + x, this.y + y);
    }

    Pos addToNew(Pos other) {
        return addToNew(other.x, other.y);
    }

    Pos multToNew(int mult) {
        return new Pos(this.x * mult, this.y * mult);
    }

    Pos copy() {
        return new Pos(x, y);
    }

    /**
     * Um Ursprung um 90° drehen
     *
     * @param turnLeft
     * @return
     */
    Pos rotate90DegToNew(boolean turnLeft) {
        if (turnLeft)
            return new Pos(y, -x);
        else
            return new Pos(-y, x);
    }

    /**
     * https://en.wikipedia.org/wiki/Taxicab_geometry
     *
     * @return
     */
    int manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    List<Pos> pathBetween(Pos other) {
        List<Pos> path = new ArrayList<>();
        int maxDist = Math.max(Math.abs(other.x - x), Math.abs(other.y - y));
        int xStep = (other.x - x) / maxDist;
        int yxStep = (other.y - y) / maxDist;

        for (int i = 0; i <= maxDist; i++) {
            Pos step = addToNew(i * xStep, i * yxStep);
            if (!step.equals(this) && !step.equals(other))
                path.add(step);
        }

        return path;
    }

    @Override
    public String toString() {
        return "Pos{" + "x=" + x +
                ", y=" + y +
                '}';
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
