package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;

import java.util.*;

public class Task {

  private final Set<Vec> walls = new HashSet<>();
  private int maxY = -1;
  private int maxX = -1;
  private Vec start = null;
  private Vec end = null;


  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    if (maxX < 0) maxX = input.length() - 1;
    int x = -1;
    for (String l : Util.str2List(input)) {
      x++;
      switch (l) {
        case "#" -> walls.add(new Vec(x, maxY));
        case "S" -> start = (new Vec(x, maxY));
        case "E" -> end = (new Vec(x, maxY));
        default -> {
        }
      }
    }
  }

  public void afterParse() throws Exception {
    Set<Vec> visited = new LinkedHashSet<>();
    Vec act = start.copy();
    while (act != null) {
      visited.add(act);
      if (act.equals(end))
        break;
      boolean foundNext = false;
      for (Vec dir : Vec.adjacent) {
        Vec next = act.addToNew(dir);
        if (!visited.contains(next) && !walls.contains(next)) {
          act = next;
          foundNext = true;
          break;
        }
      }
      if (!foundNext) break;
    }
    ArrayList<Vec> path = new ArrayList<>(visited);
    // out(path.size()); // size = picoseonds +1 because of start

    List<Cheat> possibleCheats = findAllPossibleCheats();
    // out(toStringConsole(possibleCheats));
    out("Num possible cheats", possibleCheats.size());

    long numCheatsSaved100PS = 0;
    // Map<Integer, Integer> saved2count = new HashMap<>();
    for (Cheat possibleCheat : possibleCheats) {
      Vec pathItem1 = possibleCheat.start().addToNew(possibleCheat.dir());
      Vec pathItem2 = possibleCheat.start().addToNew(possibleCheat.dir().multToNew(-1));

      int idx1 = path.indexOf(pathItem1);
      int idx2 = path.indexOf(pathItem2);
      int savedTime = Math.abs(idx2 - idx1) - 2;
      // out("savedTime", savedTime);

      // Integer soFar = saved2count.getOrDefault(savedTime, 0);
      // saved2count.put(savedTime, soFar + 1);

      if (savedTime >= 100)
        numCheatsSaved100PS++;
    }

    // out(saved2count);
    out("part 1", "numCheatsSaved100PS", numCheatsSaved100PS); // <5491
  }

  private List<Cheat> findAllPossibleCheats() {
    List<Cheat> result = new ArrayList<>();
    for (Vec wall : walls) {
      if (!wall.isInRect(1, 1, maxX - 1, maxY - 1))
        continue;

      if (!walls.contains(wall.addToNew(Vec.UP)) && !walls.contains(wall.addToNew(Vec.DOWN))) {
        result.add(new Cheat(wall.copy(), Vec.UP));
        // result.add(new Cheat(wall.copy(), wall.addToNew(Vec.DOWN)));
      }
      if (!walls.contains(wall.addToNew(Vec.LEFT)) && !walls.contains(wall.addToNew(Vec.RIGHT))) {
        result.add(new Cheat(wall.copy(), Vec.LEFT));
        // result.add(new Cheat(wall.copy(), wall.addToNew(Vec.RIGHT)));
      }
    }
    return result;
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return toStringConsole(null);
  }

  public void toBmp() throws Exception {
    Img img = new Img();
    img.writeBitmapAged();
  }

  public String toStringConsole(List<Cheat> cheats) {
    Img img = new Img();
    for (Vec wall : walls) {
      img.add(wall, "#");
    }
    img.add(start, "S");
    img.add(end, "E");

    if (cheats != null) {
      for (Cheat cheat : cheats) {
        img.add(cheat.start(), "C");
      }
    }

    return img.toConsoleString();
  }
}
