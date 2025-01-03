package aoc;

import aoc.util.Cuboid;
import aoc.util.Util;

public class Box {

  public final Cuboid cuboid;
  public final boolean on;

  protected Box(Cuboid cuboid, boolean on) {
    this.cuboid = cuboid;
    this.on = on;
  }

  public static Box parse(String input) {
    boolean on = input.startsWith("on");
    Cuboid cuboid = Cuboid.parse(Util.cleanFrom(input, "on ", "off "));

    return new Box(cuboid, on);
  }

  public Box copy() {
    return new Box(cuboid.copy(), on);
  }

  public boolean isOn() {
    return on;
  }

  @Override
  public String toString() {
    return "Box{" +
        "cuboid=" + cuboid +
        ", on=" + on +
        '}';
  }
}
