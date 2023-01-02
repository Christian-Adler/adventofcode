import java.util.Objects;

public class Range {
    final int from;
    final int to;

    public Range(int from, int to) {
        this.from = from;
        this.to = to;
    }

    boolean in(int val) {
        return val >= from && val <= to;
    }

    Range combine(Range other) {
        if (from <= other.from && to >= other.from)
            return new Range(from, Math.max(to, other.to));
        return null;
    }

    @Override
    public String toString() {
        return "[" +
                from +
                "," + to +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return from == range.from && to == range.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
