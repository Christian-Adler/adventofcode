import java.util.Objects;

public class Pos {
    double x = 0;
    double y = 0;

    public Pos(double x, double y) {
        this.x = x;
        this.y = y;
    }


    void add(Pos add) {
        this.x += add.x;
        this.y += add.y;
    }

    Pos addToNew(double x, double y) {
        return new Pos(this.x + x, this.y + y);
    }

    Pos addToNew(Pos other) {
        return addToNew(other.x, other.y);
    }


    Pos copy() {
        return new Pos(x, y);
    }

    double distToZero() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
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
