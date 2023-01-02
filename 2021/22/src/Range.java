public class Range {
    public final int min;
    public final int max;

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static Range parse(String input) {
        String[] split = input.replace("..", ",").split(",");
        int min = Integer.parseInt(split[0]);
        int max = Integer.parseInt(split[1]);
        return new Range(min, max);
    }

    public boolean containts(int val) {
        return min <= val && val <= max;
    }

    public boolean contains(Range other) {
        return min <= other.min && max >= other.max;
    }

    @Override
    public String toString() {
        return "Range{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
