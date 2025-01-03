package aoc.util;

import java.util.Arrays;
import java.util.Objects;

public class Vec3d {


  public long x;
  public long y;
  public long z;

  public Vec3d(String input) {
    long[] arr = Arrays.stream(input.split(",")).mapToLong(Long::parseLong).toArray();
    if (arr.length != 3)
      throw new IllegalArgumentException("Unexpected input");
    x = arr[0];
    y = arr[1];
    z = arr[2];
  }

  public Vec3d(long x, long y, long z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }


  public void add(Vec3d add) {
    this.x += add.x;
    this.y += add.y;
    this.z += add.z;
  }

  public Vec3d addToNew(long xyz) {
    return new Vec3d(this.x + xyz, this.y + xyz, this.z + xyz);
  }

  public Vec3d addToNew(long x, long y, long z) {
    return new Vec3d(this.x + x, this.y + y, this.z + z);
  }

  public Vec3d addToNew(Vec3d other) {
    return addToNew(other.x, other.y, other.z);
  }

  public Vec3d subToNew(long xyz) {
    return new Vec3d(this.x - xyz, this.y - xyz, this.z - xyz);
  }

  public Vec3d subToNew(long x, long y, long z) {
    return new Vec3d(this.x - x, this.y - y, this.z - z);
  }

  public Vec3d subToNew(Vec3d other) {
    return subToNew(other.x, other.y, other.z);
  }

  public Vec3d multToNew(long mult) {
    return new Vec3d(this.x * mult, this.y * mult, this.z * mult);
  }

  public Vec3d copy() {
    return new Vec3d(x, y, z);
  }

  public Vec3d withX(long val) {
    return new Vec3d(val, y, z);
  }

  public Vec3d withX(Vec3d other) {
    return withX(other.x);
  }

  public Vec3d withY(long val) {
    return new Vec3d(x, val, z);
  }

  public Vec3d withY(Vec3d other) {
    return withY(other.y);
  }

  public Vec3d withZ(long val) {
    return new Vec3d(x, y, val);
  }

  public Vec3d withZ(Vec3d other) {
    return withZ(other.z);
  }


  public boolean isIn(long minX, long minY, long minZ, long maxX, long maxY, long maxZ) {
    return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
  }

  public long manhattanDistance() {
    return manhattanDistance(new Vec3d(0, 0, 0));
  }

  public long manhattanDistance(Vec3d other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
  }

  @Override
  public String toString() {
    return "(" + x + "," + y + "," + z + ')';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Vec3d vec = (Vec3d) o;
    return x == vec.x && y == vec.y && z == vec.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }
}
