import java.util.Objects;

public final class Range {
    private final long from;
    private final long to;

    public Range(long from, long to) {
        this.from = from;
        this.to = to;
    }

    public long from() {
        return from;
    }

    public long to() {
        return to;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Range) obj;
        return this.from == that.from &&
                this.to == that.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Range[" +
                "from=" + from + ", " +
                "to=" + to + ']';
    }

}
