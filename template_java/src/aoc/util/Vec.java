package aoc.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Vec implements Comparable<Vec> {
  public static final Vec ZERO = new Vec(0, 0);

  public static final Vec DOWN = new Vec(0, 1);
  public static final Vec UP = new Vec(0, -1);
  public static final Vec RIGHT = new Vec(1, 0);
  public static final Vec LEFT = new Vec(-1, 0);

  public static final Set<Vec> adjacent;
  public static final Set<Vec> adjacentDiagonal;
  public static final Set<Vec> adjacent8;

  static {
    adjacent = new HashSet<>(Arrays.asList(DOWN, UP, RIGHT, LEFT));
    adjacentDiagonal = new HashSet<>(Arrays.asList(new Vec(1, 1), new Vec(-1, 1), new Vec(-1, -1), new Vec(1, -1)));
    adjacent8 = new HashSet<>();
    adjacent8.addAll(adjacent);
    adjacent8.addAll(adjacentDiagonal);
  }

  public final int x;
  public final int y;
  /**
   * color could be java.awt.Color or int or string
   */
  public final Object color;

  public Vec(String input) {
    int[] arr = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    if (arr.length != 2)
      throw new IllegalArgumentException("Unexpected input");
    x = arr[0];
    y = arr[1];
    color = null;
  }

  /**
   * @param input >, <, ^, v
   * @return Vec
   */
  public static Vec parseDir(String input) {
    return switch (input) {
      case ">" -> Vec.RIGHT;
      case "<" -> Vec.LEFT;
      case "^" -> Vec.UP;
      case "v" -> Vec.DOWN;
      default -> throw new IllegalStateException("Unexpected value: " + input);
    };
  }

  public Vec(int x, int y) {
    this(x, y, null);
  }

  public Vec(int x, int y, Object color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  public Vec copy() {
    return new Vec(x, y, color);
  }


  public Vec add(int x, int y) {
    return new Vec(this.x + x, this.y + y);
  }

  public Vec add(Vec other) {
    return add(other.x, other.y);
  }

  public Vec multiply(int val) {
    return new Vec(this.x * val, this.y * val);
  }

  public Vec withX(int x) {
    return new Vec(x, this.y);
  }

  public Vec withX(Vec other) {
    return withX(other.x);
  }

  public Vec withY(int y) {
    return new Vec(this.x, y);
  }

  public Vec withY(Vec other) {
    return withY(other.y);
  }

  public boolean isIn(int minX, int minY, int maxX, int maxY) {
    return x >= minX && x <= maxX && y >= minY && y <= maxY;
  }

  /**
   * Um Ursprung um 90° drehen
   *
   * @param turnLeft - turn left or right
   */
  @SuppressWarnings("SuspiciousNameCombination")
  public Vec rotate90DegToNew(boolean turnLeft) {
    if (turnLeft)
      return new Vec(-y, x);
    else
      return new Vec(y, -x);
  }

  /**
   * <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">...</a>
   */
  public int manhattanDistance() {
    return manhattanDistance(new Vec(0, 0));
  }

  public int manhattanDistance(Vec other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y);
  }

  public List<Vec> pathBetween(Vec other) {
    List<Vec> path = new ArrayList<>();
    int maxDist = Math.max(Math.abs(other.x - x), Math.abs(other.y - y));
    double xStep = (double) (other.x - x) / maxDist;
    double yxStep = (double) (other.y - y) / maxDist;

    for (int i = 0; i <= maxDist; i++) {
      Vec step = add((int) (i * xStep), (int) (i * yxStep));
      if (!step.equals(this) && !step.equals(other))
        path.add(step);
    }

    return path;
  }

  @Override
  public String toString() {
    return "(" + x + "," + y + ')';
  }

  public String toDirString() {
    if (equals(Vec.DOWN)) return "v";
    if (equals(Vec.UP)) return "^";
    if (equals(Vec.RIGHT)) return ">";
    if (equals(Vec.LEFT)) return "<";
    return "?";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Vec vec = (Vec) o;
    return x == vec.x && y == vec.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public int compareTo(Vec other) {
    int compare = Integer.compare(this.x, other.x);
    if (compare != 0) return compare;
    compare = Integer.compare(this.y, other.y);
    return compare;
  }

  /**
   * For map parsing
   *
   * @param lines   input
   * @param visitor called for every char in lines
   * @return max x-y-Vec
   */
  public static Vec mapInputVisitor(List<String> lines, BiConsumer<Vec, String> visitor) {
    int maxY = -1;
    int maxX = -1;

    for (String line : lines) {
      maxY++;
      maxX = line.length() - 1;
      int x = -1;
      for (String s : Util.str2List(line)) {
        x++;
        visitor.accept(new Vec(x, maxY), s);
      }
    }

    return new Vec(maxX, maxY);
  }

  public static void xyVisitor(Vec v1, Consumer<Vec> visitor) {
    xyVisitor(v1, Vec.ZERO, visitor);
  }

  public static void xyVisitor(Vec v1, Vec v2, Consumer<Vec> visitor) {
    int maxY = Math.max(v1.y, v2.y);
    int minY = Math.min(v1.y, v2.y);
    int maxX = Math.max(v1.x, v2.x);
    int minX = Math.min(v1.x, v2.x);

    for (int y = minY; y <= maxY; y++) {
      for (int x = minX; x <= maxX; x++) {
        visitor.accept(new Vec(x, y));
      }
    }
  }
}
