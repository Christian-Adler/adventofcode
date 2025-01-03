package aoc.util;

public record Range(long from, long to) {

  public static Range parse(String input) {
    String[] split = input.replace("..", ",").split(",");
    long from = Long.parseLong(split[0]);
    long to = Long.parseLong(split[1]);
    return new Range(from, to);
  }

  public long size() {
    return Math.abs(to - from) + 1;
  }

  public boolean greaterThan(Range other) {
    return from > other.to;
  }

  public boolean lessThan(Range other) {
    return to < other.from;
  }

  public boolean contains(long val) {
    return from <= val && val <= to;
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

}
