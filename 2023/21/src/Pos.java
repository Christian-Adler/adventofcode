import java.util.*;

public class Pos {
  public static final Pos DOWN = new Pos(0, 1);
  public static final Pos UP = new Pos(0, -1);
  public static final Pos RIGHT = new Pos(1, 0);
  public static final Pos LEFT = new Pos(-1, 0);

  public static final Set<Pos> adjacent;
  public static final Set<Pos> adjacentDiagonal;
  public static final Set<Pos> adjacent8;

  static {
    adjacent = new HashSet<>(Arrays.asList(DOWN, UP, RIGHT, LEFT));
    adjacentDiagonal = new HashSet<>(Arrays.asList(new Pos(1, 1), new Pos(-1, 1), new Pos(-1, -1), new Pos(1, -1)));
    adjacent8 = new HashSet<>();
    adjacent8.addAll(adjacent);
    adjacent8.addAll(adjacentDiagonal);
  }

  long x = 0;
  long y = 0;
  String color = null;

  public Pos(long x, long y) {
    this.x = x;
    this.y = y;
  }

  public Pos(long x, long y, String color) {
    this(x, y);
    this.color = color;
  }

  void add(Pos add) {
    this.x += add.x;
    this.y += add.y;
  }

  Pos addToNew(long x, long y) {
    return new Pos(this.x + x, this.y + y);
  }

  Pos addToNew(Pos other) {
    return addToNew(other.x, other.y);
  }

  Pos multToNew(long mult) {
    return new Pos(this.x * mult, this.y * mult);
  }

  Pos copy() {
    return new Pos(x, y, color);
  }

  /**
   * Um Ursprung um 90° drehen
   *
   * @param turnLeft
   * @return
   */
  Pos rotate90DegToNew(boolean turnLeft) {
    if (turnLeft)
      return new Pos(-y, x);
    else
      return new Pos(y, -x);
  }

  /**
   * https://en.wikipedia.org/wiki/Taxicab_geometry
   *
   * @return
   */
  long manhattanDistance() {
    return Math.abs(x) + Math.abs(y);
  }

  long manhattanDistance(Pos other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y);
  }

  List<Pos> pathBetween(Pos other) {
    List<Pos> path = new ArrayList<>();
    long maxDist = Math.max(Math.abs(other.x - x), Math.abs(other.y - y));
    long xStep = (other.x - x) / maxDist;
    long yxStep = (other.y - y) / maxDist;

    for (long i = 0; i <= maxDist; i++) {
      Pos step = addToNew(i * xStep, i * yxStep);
      if (!step.equals(this) && !step.equals(other))
        path.add(step);
    }

    return path;
  }

  @Override
  public String toString() {
    return "Pos{" + "x=" + x +
        ", y=" + y +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pos pos = (Pos) o;
    return x == pos.x && y == pos.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
