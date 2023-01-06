import java.util.Objects;

public class R {
    final String name;
    final int speed;
    final int flyDuration;
    final int restDuration;

    public R(String input) {
        String[] parts = input.split(" ");
        name = parts[0];
        speed = Integer.parseInt(parts[1]);
        flyDuration = Integer.parseInt(parts[2]);
        restDuration = Integer.parseInt(parts[3]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        R r = (R) o;
        return name.equals(r.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("R{");
        sb.append("name='").append(name).append('\'');
        sb.append(", speed=").append(speed);
        sb.append(", flyDuration=").append(flyDuration);
        sb.append(", restDuration=").append(restDuration);
        sb.append('}');
        return sb.toString();
    }
}
