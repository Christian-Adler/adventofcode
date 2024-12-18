import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

  int x;
  int y;
  /**
   * color could be java.awt.Color or int or string
   */
  Object color = null;

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

  void add(Vec add) {
    this.x += add.x;
    this.y += add.y;
  }

  Vec addToNew(int x, int y) {
    return new Vec(this.x + x, this.y + y);
  }

  Vec addToNew(Vec other) {
    return addToNew(other.x, other.y);
  }

  Vec multToNew(int mult) {
    return new Vec(this.x * mult, this.y * mult);
  }

  Vec copy() {
    return new Vec(x, y, color);
  }

  /**
   * Um Ursprung um 90° drehen
   *
   * @param turnLeft - turn left or right
   */
  @SuppressWarnings("SuspiciousNameCombination")
  Vec rotate90DegToNew(boolean turnLeft) {
    if (turnLeft)
      return new Vec(-y, x);
    else
      return new Vec(y, -x);
  }

  /**
   * <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">...</a>
   */
  int manhattanDistance() {
    return Math.abs(x) + Math.abs(y);
  }

  int manhattanDistance(Vec other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y);
  }

  List<Vec> pathBetween(Vec other) {
    List<Vec> path = new ArrayList<>();
    int maxDist = Math.max(Math.abs(other.x - x), Math.abs(other.y - y));
    int xStep = (other.x - x) / maxDist;
    int yxStep = (other.y - y) / maxDist;

    for (int i = 0; i <= maxDist; i++) {
      Vec step = addToNew(i * xStep, i * yxStep);
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
