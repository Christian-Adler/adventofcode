package aoc;

import aoc.util.Vec;

import java.util.Arrays;
import java.util.List;

public class Vent {
  private Vec from;
  private Vec to;

  public Vent(String input) {
    List<Vec> vecs = Arrays.stream(input.split(" -> ")).map(Vec::new).toList();
    from = vecs.getFirst();
    to = vecs.getLast();
  }

  public boolean isStraightLine() {
    return from.x == to.x || from.y == to.y;
  }

  public boolean isStraightOrDiagonalLine() {
    if (isStraightLine()) return true;
    Vec dir = to.subToNew(from);
    return Math.abs(dir.x) == Math.abs(dir.y);
  }

  public List<Vec> getLine() {
    return from.pathBetween(to, true);
  }

  @Override
  public String toString() {
    return from + " -> " + to;
  }
}
