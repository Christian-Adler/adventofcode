import java.util.Objects;

public class Moon {
    private static int idProvider;

    final int id = ++idProvider;
    Pos pos;
    Pos vel = new Pos(0, 0, 0);

    public Moon(Pos pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id=").append(id);
        sb.append(", pos=").append(pos);
        sb.append(", vel=").append(vel);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moon moon = (Moon) o;
        return id == moon.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
