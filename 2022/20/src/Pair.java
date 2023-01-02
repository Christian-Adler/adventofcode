import java.util.Objects;

public class Pair {
    int idx = 0;
    long number = 0;

    public Pair(int idx, long number) {
        this.idx = idx;
        this.number = number;
    }

    @Override
    public String toString() {
        if (true)
            return String.valueOf(number);
        final StringBuilder sb = new StringBuilder("Pair{");
        sb.append("idx=").append(idx);
        sb.append(", number=").append(number);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return idx == pair.idx;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx);
    }
}
