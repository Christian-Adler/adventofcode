package aoc.util;

import java.util.Arrays;
import java.util.Objects;

public class Vec3d {


  public int x;
  public int y;
  public int z;

  public Vec3d(String input) {
    int[] arr = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    if (arr.length != 3)
      throw new IllegalArgumentException("Unexpected input");
    x = arr[0];
    y = arr[1];
    z = arr[2];
  }

  public Vec3d(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }


  public void add(Vec3d add) {
    this.x += add.x;
    this.y += add.y;
    this.z += add.z;
  }

  public Vec3d addToNew(int x, int y, int z) {
    return new Vec3d(this.x + x, this.y + y, this.z + z);
  }

  public Vec3d addToNew(Vec3d other) {
    return addToNew(other.x, other.y, other.z);
  }

  public Vec3d subToNew(int x, int y, int z) {
    return new Vec3d(this.x - x, this.y - y, this.z - z);
  }

  public Vec3d subToNew(Vec3d other) {
    return subToNew(other.x, other.y, other.z);
  }

  public Vec3d multToNew(int mult) {
    return new Vec3d(this.x * mult, this.y * mult, this.z * mult);
  }

  public Vec3d copy() {
    return new Vec3d(x, y, z);
  }

  public boolean isIn(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
    return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
  }

  public int manhattanDistance(Vec3d other) {
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
