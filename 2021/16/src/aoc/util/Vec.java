package aoc.util;

import java.util.*;

public class Vec {
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

  public int x;
  public int y;
  /**
   * color could be java.awt.Color or int or string
   */
  public Object color = null;

  public Vec(String input) {
    int[] arr = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    if (arr.length != 2)
      throw new IllegalArgumentException("Unexpected input");
    x = arr[0];
    y = arr[1];
  }

  public Vec(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Vec(int x, int y, Object color) {
    this(x, y);
    this.color = color;
  }

  public void add(Vec add) {
    this.x += add.x;
    this.y += add.y;
  }

  public Vec addToNew(int x, int y) {
    return new Vec(this.x + x, this.y + y);
  }

  public Vec addToNew(Vec other) {
    return addToNew(other.x, other.y);
  }

  public Vec multToNew(int mult) {
    return new Vec(this.x * mult, this.y * mult);
  }

  public Vec copy() {
    return new Vec(x, y, color);
  }

  public boolean isInRect(int minX, int minY, int maxX, int maxY) {
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
    return Math.abs(x) + Math.abs(y);
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
      Vec step = addToNew((int) (i * xStep), (int) (i * yxStep));
      if (!step.equals(this) && !step.equals(other))
        path.add(step);
    }

    return path;
  }

  @Override
  public String toString() {
    return "(" + x + "," + y + ')';
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
}
