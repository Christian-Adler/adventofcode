import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Pos {

  long x = 0;
  long y = 0;
  long z = 0;
  String color = null;

  public Pos(long x, long y, long z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Pos(long x, long y, long z, String color) {
    this(x, y, z);
    this.color = color;
  }

  public static Pos from(String input) {
    List<Long> longs = Arrays.stream(input.split(",")).map(String::trim).mapToLong(Long::parseLong).boxed().toList();
    return new Pos(longs.get(0), longs.get(1), longs.get(2));
  }

  void add(Pos add) {
    this.x += add.x;
    this.y += add.y;
  }

  void add(long x, long y, long z) {
    this.x += x;
    this.y += y;
    this.z += z;
  }

  Pos addToNew(long x, long y, long z) {
    return new Pos(this.x + x, this.y + y, this.z + z);
  }

  Pos addToNew(Pos other) {
    return addToNew(other.x, other.y, other.z);
  }


  Pos copy() {
    return new Pos(x, y, z, color);
  }


  /**
   * https://en.wikipedia.org/wiki/Taxicab_geometry
   *
   * @return
   */
  long manhattanDistance() {
    return Math.abs(x) + Math.abs(y) + Math.abs(z);
  }

  long manhattanDistance(Pos other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
  }

  long manhattanDistanceXY(Pos other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y);
  }


  @Override
  public String toString() {
    return "Pos{" + "x=" + x +
        ", y=" + y +
        ", z=" + z +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pos pos = (Pos) o;
    return x == pos.x && y == pos.y && z == pos.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }
}
