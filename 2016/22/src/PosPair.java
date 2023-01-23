import java.util.Objects;

public class PosPair {
    final Pos p1;
    final Pos p2;

    public PosPair(Pos p1, Pos p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PosPair posPair = (PosPair) o;
        if (p1.equals(posPair.p1))
            return p2.equals(posPair.p2);

        if (p1.equals(posPair.p2))
            return p2.equals(posPair.p1);

        return false;
    }

    @Override
    public int hashCode() {
        if (p1.compareTo(p2) <= 0)
            return Objects.hash(p1, p2);
        return Objects.hash(p2, p1);
    }

    @Override
    public String toString() {
        return p1 + " <-> " + p2;
    }
}
