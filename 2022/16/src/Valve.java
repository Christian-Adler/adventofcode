import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Valve {
    final String name;
    int flowRate = 0;
    Set<Valve> neighbors = new HashSet<>();

    public Valve(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valve valve = (Valve) o;
        return name.equals(valve.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Valve{" +
                "name='" + name + '\'' +
                ", flowRate=" + flowRate +
                ", neighbors=" + neighbors.size() +
                '}';
    }
}
