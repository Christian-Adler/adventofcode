package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Task {

  private final Set<Vec> walls = new HashSet<>();
  private int maxY = -1;
  private int maxX = -1;
  private Vec start = null;
  private Vec end = null;

  private Cheat maxSavedTimeCheat20 = null;

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
    out("not cheated path steps:", path.size() - 1); // size = picoseonds +1 because of start

    List<Cheat> possibleCheats = findAllPossibleCheats();
    // out(toStringConsole(possibleCheats));
    out("Num possible cheats", possibleCheats.size());

    long numCheatsSaved100PS = getNumCheatsSaved100PS(possibleCheats, path);

    out("part 1", "numCheatsSaved100PS", numCheatsSaved100PS);


    possibleCheats = findAllPossibleCheats20();
    out("Num possible cheats", possibleCheats.size());

    numCheatsSaved100PS = getNumCheatsSaved100PS(possibleCheats, path);

    out("part 2", "numCheatsSaved100PS", numCheatsSaved100PS);

    Img img = new Img();
    // for (Vec wall : walls) {
    //   img.add(wall, Color.gray);
    // }
    for (Vec vec : path) {
      img.add(vec);
    }

    List<Vec> cheatPath = maxSavedTimeCheat20.start().pathBetween(maxSavedTimeCheat20.end());
    for (Vec vec : cheatPath) {
      img.add(vec, Color.red.brighter());
    }
    img.add(start, Color.red);
    img.add(end, Color.red);
    Util.writeToFile(img.toSVGStringAged(), "./svg.svg");
  }

  private long getNumCheatsSaved100PS(List<Cheat> possibleCheats, ArrayList<Vec> path) {
    long numCheatsSaved100PS = 0;
    Map<Integer, Integer> saved2count = new HashMap<>();
    long maxCheatLen = 0;
    long maxSavedTime = 0;
    for (Cheat possibleCheat : possibleCheats) {

      int idx1 = path.indexOf(possibleCheat.start());
      int idx2 = path.indexOf(possibleCheat.end());
      int cheatLength = possibleCheat.len();
      maxCheatLen = Math.max(maxCheatLen, cheatLength);
      int normalPathLength = Math.abs(idx2 - idx1);
      int savedTime = normalPathLength - cheatLength;
      // out("savedTime", savedTime);
      if (savedTime > maxSavedTime) {
        maxSavedTime = savedTime;
        // out("maxSavedTime", maxSavedTime, possibleCheat);
        maxSavedTimeCheat20 = possibleCheat;
      }

      Integer soFar = saved2count.getOrDefault(savedTime, 0);

      if (savedTime >= 50) {
        saved2count.put(savedTime, soFar + 1);
        if (savedTime >= 100) {
          numCheatsSaved100PS++;
        }
      }
    }
    // out(saved2count);
    // out("max cheat len", maxCheatLen);
    // out("count : saved");
    // out("=============");
    // for (Integer saved : saved2count.keySet().stream().sorted().toList()) {
    //   out(saved2count.get(saved), ":", saved);
    // }
    return numCheatsSaved100PS;
  }

  private List<Cheat> findAllPossibleCheats() {
    List<Cheat> result = new ArrayList<>();
    for (Vec wall : walls) {
      if (!wall.isInRect(1, 1, maxX - 1, maxY - 1))
        continue;

      if (!walls.contains(wall.addToNew(Vec.UP)) && !walls.contains(wall.addToNew(Vec.DOWN))) {
        result.add(new Cheat(wall.addToNew(Vec.DOWN), wall.addToNew(Vec.UP), 2));
      }
      if (!walls.contains(wall.addToNew(Vec.LEFT)) && !walls.contains(wall.addToNew(Vec.RIGHT))) {
        result.add(new Cheat(wall.addToNew(Vec.LEFT), wall.addToNew(Vec.RIGHT), 2));
      }
    }
    return result;
  }

  private List<Cheat> findAllPossibleCheats20() {
    Set<Cheat> result = new HashSet<>();
    for (int y = 1; y < maxY; y++) {
      for (int x = 1; x < maxX; x++) {
        Vec start = new Vec(x, y);
        if (!walls.contains(start)) {
          result.addAll(findPathsThroughWalls(start));
        }
      }
    }
    return new ArrayList<>(result);
  }

  private Set<Cheat> findPathsThroughWalls(Vec start) {
    Set<Cheat> result = new HashSet<>();
    for (int x = start.x - 20; x <= start.x + 20; x++) {
      for (int y = start.y - 20; y <= start.y + 20; y++) {
        Vec next = new Vec(x, y);
        if (next.isInRect(1, 1, maxX - 1, maxY - 1)) {
          if (!walls.contains(next)) {
            int manhattanDistance = start.manhattanDistance(next);
            if (manhattanDistance > 1 && manhattanDistance <= 20)
              result.add(new Cheat(start, next, manhattanDistance));
          }
        }
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
