package aoc;

import aoc.util.Util;
import aoc.util.Vec;

public class Task2 {

  private Vec position = new Vec(0, 0);
  private long aim = 0;

  public void init() {
  }

  public void addLine(String input) {
    Vec dir = null;
    if (input.contains("down"))
      aim += Integer.parseInt(Util.cleanFrom(input, "forward", "down", "up", " "));
    else if (input.contains("up"))
      aim -= Integer.parseInt(Util.cleanFrom(input, "forward", "down", "up", " "));
    else if (input.contains("forward")) {
      dir = Vec.RIGHT;
      int val = Integer.parseInt(Util.cleanFrom(input, "forward", "down", "up", " "));
      dir = dir.multToNew(val);
      position.add(dir);
      position.add(Vec.DOWN.multToNew(val * aim));
    }
  }

  public void afterParse() throws Exception {
    out(position);
    out("part 2", "final depth", position.x * position.y);
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
