package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Task {
  private final Set<Vec> eastMoving = new HashSet<>();
  private final Set<Vec> southMoving = new HashSet<>();
  private int maxY = -1;
  private int maxX = -1;

  public void init() {
  }

  public void addLine(String input) {
    maxX = input.length() - 1;
    maxY++;
    int x = -1;
    for (String s : Util.str2List(input)) {
      x++;
      if (s.equals(">"))
        eastMoving.add(new Vec(x, maxY));
      else if (s.equals("v"))
        southMoving.add(new Vec(x, maxY));
    }
  }

  public void afterParse() throws Exception {
    // out(this);

    long step = 0;
    // for (int i = 0; i < 10; i++) {
    while (true) {
      step++;

      boolean foundMovables = false;
      Set<Vec> movables = findMovables(eastMoving, Vec.RIGHT);
      if (!movables.isEmpty())
        foundMovables = true;
      move(movables, Vec.RIGHT, eastMoving);

      movables = findMovables(southMoving, Vec.DOWN);
      if (!movables.isEmpty())
        foundMovables = true;
      move(movables, Vec.DOWN, southMoving);

      // out(i, "\r\n" + this);

      if (!foundMovables) {
        out("no movement at step", step);
        break;
      }
    }
  }


  private void move(Collection<Vec> movables, Vec dir, Set<Vec> herd) {
    herd.removeAll(movables);
    movables.forEach(v -> herd.add(getNextPos(v, dir)));
  }

  private Set<Vec> findMovables(Collection<Vec> checks, Vec dir) {
    Set<Vec> movables = new HashSet<>();
    for (Vec check : checks) {
      Vec next = getNextPos(check, dir);
      if (!southMoving.contains(next) && !eastMoving.contains(next))
        movables.add(check);
    }
    return movables;
  }

  private Vec getNextPos(Vec pos, Vec dir) {
    Vec next = pos.add(dir);
    if (next.x > maxX)
      next = next.withX(0);
    else if (next.y > maxY)
      next = next.withY(0);
    return next;
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return toStringConsole();
  }

  public void toBmp() throws Exception {
    Img img = new Img();
    img.writeBitmapAged();
  }

  public String toStringConsole() {
    Img img = new Img();
    img.add(new Vec(0, 0), ".");
    img.add(new Vec(maxX, maxY), ".");
    eastMoving.forEach(v -> img.add(v, ">"));
    southMoving.forEach(v -> img.add(v, "v"));
    return img.toConsoleString();
  }
}
