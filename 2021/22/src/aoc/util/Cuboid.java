package aoc.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  p2 ____________ p3
 *    /|          /|
 *   / |         / |
 *  /__|________/p7|
 * |p6 |        |  |
 * |   |_ _ _ _ | _|
 * |  / p0      |  / p1
 * | /          | /
 * |/___________|/
 * p4            p5
 * </pre>
 */
public class Cuboid {
  public Vec3d p0; // left bottom rear = minCorner
  public Vec3d p7; // right top front = maxCorner


  protected Cuboid(Vec3d v1, Vec3d v2) {
    this.p0 = new Vec3d(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
    this.p7 = new Vec3d(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
  }

  public static Cuboid parse(String input) {
    String[] split = Util.cleanFrom(input, "x=", "y=", "z=").split(",");
    List<Integer> x = Arrays.stream(split[0].split("\\.\\.")).mapToInt(Integer::parseInt).sorted().boxed().toList();
    List<Integer> y = Arrays.stream(split[1].split("\\.\\.")).mapToInt(Integer::parseInt).sorted().boxed().toList();
    List<Integer> z = Arrays.stream(split[2].split("\\.\\.")).mapToInt(Integer::parseInt).sorted().boxed().toList();

    return new Cuboid(new Vec3d(x.getFirst(), y.getFirst(), z.getFirst()), new Vec3d(x.getLast(), y.getLast(), z.getLast()));
  }

  public Cuboid copy() {
    return new Cuboid(p0.copy(), p7.copy());
  }

  public long volume() {
    return xRange().size() * yRange().size() * zRange().size();
  }

  public boolean contains(Vec3d vec) {
    return xRange().contains(vec.x)
        && yRange().contains(vec.y)
        && zRange().contains(vec.z);
  }

  public Set<Vec3d> filter(Collection<Vec3d> input) {
    return input.stream().filter(this::contains).collect(Collectors.toSet());
  }

  public boolean covers(Cuboid other) {
    return xRange().covers(other.xRange())
        && yRange().covers(other.yRange())
        && zRange().covers(other.zRange());
  }

  public boolean intersects(Cuboid other) {
    return !isDisjoint(other);
  }

  public boolean isDisjoint(Cuboid other) {
    return xMin() > other.xMax() || xMax() < other.xMin()
        || yMin() > other.yMax() || yMax() < other.yMin()
        || zMin() > other.zMax() || zMax() < other.zMin();
  }

  public boolean isMiniCube() {
    return xRange().size() == 1 && yRange().size() == 1 && zRange().size() == 1;
  }

  public boolean isZeroCube() {
    return xRange().size() < 1 || yRange().size() < 1 || zRange().size() < 1;
  }

  public Cuboid intersect(Cuboid other) {
    if (isDisjoint(other))
      return null;
    return new Cuboid(
        new Vec3d(Math.max(p0.x, other.p0.x),
            Math.max(p0.y, other.p0.y),
            Math.max(p0.z, other.p0.z)),
        new Vec3d(Math.min(p7.x, other.p7.x),
            Math.min(p7.y, other.p7.y),
            Math.min(p7.z, other.p7.z))
    );
  }

  public List<Cuboid> splitBy(Cuboid other) {
    /*
     *      07 - 08 - 09
     *    16   17   18 |
     *  25 - 26 - 27   |
     *  |   04   05   06
     *  | 13   14   15 |
     *  22 - 23 - 24   |
     *  |   01   02   03
     *  | 10   11   12
     *  19 - 20 - 21
     */

    Cuboid splitBy = null;
    if (covers(other))
      splitBy = other;
    else if (intersects(other))
      splitBy = intersect(other);

    if (splitBy == null)
      return null;

    List<Cuboid> result = new ArrayList<>();

    // covered corners must not be part of the new cuboids!

    // corners
    result.add(new Cuboid(getP0(), splitBy.getP0().addToNew(-1, -1, -1))); // 01
    result.add(new Cuboid(getP1(), splitBy.getP1().addToNew(1, -1, -1))); // 03
    result.add(new Cuboid(getP2(), splitBy.getP2().addToNew(-1, 1, -1))); // 07
    result.add(new Cuboid(getP3(), splitBy.getP3().addToNew(1, 1, -1))); // 09
    result.add(new Cuboid(getP4(), splitBy.getP4().addToNew(-1, -1, 1))); // 19
    result.add(new Cuboid(getP5(), splitBy.getP5().addToNew(1, -1, 1))); // 21
    result.add(new Cuboid(getP6(), splitBy.getP6().addToNew(-1, 1, 1))); // 25
    result.add(new Cuboid(getP7(), splitBy.getP7().addToNew(1, 1, 1))); // 27

    // centers
    result.add(new Cuboid(splitBy.getP0().withZ(getP0()), splitBy.getP3().addToNew(0, 0, -1))); // 05
    result.add(new Cuboid(splitBy.getP4().withZ(getP4()), splitBy.getP7().addToNew(0, 0, 1))); // 23
    result.add(new Cuboid(splitBy.getP0().withY(getP0()), splitBy.getP5().addToNew(0, -1, 0))); // 11
    result.add(new Cuboid(splitBy.getP2().withY(getP2()), splitBy.getP7().addToNew(0, 1, 0))); // 17
    result.add(new Cuboid(splitBy.getP2().withX(getP2()), splitBy.getP4().addToNew(-1, 0, 0))); // 13
    result.add(new Cuboid(splitBy.getP3().withX(getP3()), splitBy.getP5().addToNew(1, 0, 0))); // 15

    // edges
    result.add(new Cuboid(getP0().withX(splitBy.getP0()), splitBy.getP1().addToNew(0, -1, -1))); // 02
    result.add(new Cuboid(getP4().withX(splitBy.getP4()), splitBy.getP5().addToNew(0, -1, 1))); // 20
    result.add(new Cuboid(getP2().withX(splitBy.getP2()), splitBy.getP3().addToNew(0, 1, -1))); // 08
    result.add(new Cuboid(getP6().withX(splitBy.getP6()), splitBy.getP7().addToNew(0, 1, 1))); // 26

    result.add(new Cuboid(getP0().withY(splitBy.getP0()), splitBy.getP2().addToNew(-1, 0, -1))); // 04
    result.add(new Cuboid(getP1().withY(splitBy.getP1()), splitBy.getP3().addToNew(1, 0, -1))); // 06
    result.add(new Cuboid(getP4().withY(splitBy.getP4()), splitBy.getP6().addToNew(-1, 0, 1))); // 22
    result.add(new Cuboid(getP5().withY(splitBy.getP5()), splitBy.getP7().addToNew(1, 0, 1))); // 24

    result.add(new Cuboid(getP0().withZ(splitBy.getP0()), splitBy.getP4().addToNew(-1, -1, 0))); // 10
    result.add(new Cuboid(getP1().withZ(splitBy.getP1()), splitBy.getP5().addToNew(1, -1, 0))); // 12
    result.add(new Cuboid(getP2().withZ(splitBy.getP2()), splitBy.getP6().addToNew(-1, 1, 0))); // 16
    result.add(new Cuboid(getP3().withZ(splitBy.getP3()), splitBy.getP7().addToNew(1, 1, 0))); // 18

    result.removeIf(c -> !this.covers(c) || c.isZeroCube());

    return result;
  }

  public Cuboid union(Cuboid other) {
    return new Cuboid(
        new Vec3d(Math.min(p0.x, other.p0.x),
            Math.min(p0.y, other.p0.y),
            Math.min(p0.z, other.p0.z)),
        new Vec3d(Math.max(p7.x, other.p7.x),
            Math.max(p7.y, other.p7.y),
            Math.max(p7.z, other.p7.z))
    );
  }

  public long xMin() {
    return p0.x;
  }

  public long xMax() {
    return p7.x;
  }

  public long yMin() {
    return p0.y;
  }

  public long yMax() {
    return p7.y;
  }

  public long zMin() {
    return p0.z;
  }

  public long zMax() {
    return p7.z;
  }

  public Set<Vec3d> toPositions() {
    Set<Vec3d> positions = new LinkedHashSet<>();
    for (long x = xRange().from(); x <= xRange().to(); x++) {
      for (long y = yRange().from(); y <= yRange().to(); y++) {
        for (long z = zRange().from(); z <= zRange().to(); z++) {
          positions.add(new Vec3d(x, y, z));
        }
      }
    }
    return positions;
  }

  public List<Vec3d> getAllCorners() {
    List<Vec3d> corners = new ArrayList<>();
    corners.add(getP0());
    corners.add(getP1());
    corners.add(getP2());
    corners.add(getP3());
    corners.add(getP4());
    corners.add(getP5());
    corners.add(getP6());
    corners.add(getP7());
    return corners;
  }

  public Vec3d getP7() {
    return new Vec3d(p7.x, p7.y, p7.z);
  }

  public Vec3d getP6() {
    return new Vec3d(p0.x, p7.y, p7.z);
  }

  public Vec3d getP5() {
    return new Vec3d(p7.x, p0.y, p7.z);
  }

  public Vec3d getP4() {
    return new Vec3d(p0.x, p0.y, p7.z);
  }

  public Vec3d getP3() {
    return new Vec3d(p7.x, p7.y, p0.z);
  }

  public Vec3d getP2() {
    return new Vec3d(p0.x, p7.y, p0.z);
  }

  public Vec3d getP1() {
    return new Vec3d(p7.x, p0.y, p0.z);
  }

  public Vec3d getP0() {
    return new Vec3d(p0.x, p0.y, p0.z);
  }

  public Range xRange() {
    return new Range(p0.x, p7.x);
  }

  public Range yRange() {
    return new Range(p0.y, p7.y);
  }

  public Range zRange() {
    return new Range(p0.z, p7.z);
  }

  @Override
  public String toString() {
    return "Cuboid{" +
        "p0=" + p0 +
        ", p7=" + p7 +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Cuboid cuboid = (Cuboid) o;
    return Objects.equals(p0, cuboid.p0) && Objects.equals(p7, cuboid.p7);
  }

  @Override
  public int hashCode() {
    return Objects.hash(p0, p7);
  }
}
