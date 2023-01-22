import java.util.Objects;

public class Range implements Comparable<Range> {
    final long from;
    final long to;

    public Range(long from, long to) {
        this.from = from;
        this.to = to;
    }

    public long size() {
        return from - to + 1;
    }

    public boolean greaterThan(Range other) {
        return from > other.to;
    }

    public boolean lessThan(Range other) {
        return to < other.from;
    }

    public boolean covers(Range other) {
        return from <= other.from && to >= other.to;
    }

    public boolean intersects(Range other) {
        return !(from > other.to || to < other.from);
    }

    public Range combine(Range other) {
        if (intersects(other))
            return new Range(Math.min(from, other.from), Math.max(to, other.to));

        if (lessThan(other) && to == other.from - 1) // Direkt angrenzend
            return new Range(from, other.to);
        if (greaterThan(other) && from == other.to + 1) // Direkt angrenzend
            return new Range(other.from, to);

        return null;
    }

    @Override
    public String toString() {
        return "[" + from + "," + to + "]";
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

    @Override
    public int compareTo(Range o) {
        return Long.compare(from, o.from);
    }
}
