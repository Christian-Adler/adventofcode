package aoc;

import aoc.util.Util;
import aoc.util.Vec;

public class Task {

  private Vec position = new Vec(0, 0);
  private Vec aim = new Vec(0, 0);

  public void init() {
  }

  public void addLine(String input) {
    Vec dir = null;
    if (input.contains("forward"))
      dir = Vec.RIGHT;
    else if (input.contains("down"))
      dir = Vec.DOWN;
    else if (input.contains("up"))
      dir = Vec.UP;
    if (dir == null) throw new IllegalStateException("Unknown direction");

    int mult = Integer.parseInt(Util.cleanFrom(input, "forward", "down", "up", " "));
    dir = dir.multToNew(mult);
    position.add(dir);
  }

  public void afterParse() throws Exception {
    out("part 1", "final depth", position.x * position.y);
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
