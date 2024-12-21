package aoc.keypad;

import aoc.util.Vec;
import aoc.util.astar.AStar;
import aoc.util.astar.AStarItem;
import aoc.util.astar.AStarNextItem;

import java.util.*;

public abstract class KeypadBase {
  protected Map<String, Vec> keypad = new HashMap<>();
  protected Set<Vec> availablePositions = new HashSet<>();
  public Vec startPos = null;

  private static final Map<Vec, String> dir2str = new HashMap<>();

  static {
    dir2str.put(Vec.DOWN, "v");
    dir2str.put(Vec.UP, "^");
    dir2str.put(Vec.LEFT, "<");
    dir2str.put(Vec.RIGHT, ">");
  }

  public List<String> findShortestPaths(String start, String end) {
    Vec startPos = keypad.get(start);
    Vec endPos = keypad.get(end);

    AStar<Vec> aStar = new AStar<>(
        (vec, vec2) -> (long) vec.manhattanDistance(vec2),
        (vec) -> {
          List<AStarNextItem<Vec>> result = new ArrayList<>();
          for (Vec dir : Vec.adjacent) {
            Vec next = vec.addToNew(dir);
            if (!availablePositions.contains(next)) continue;
            result.add(new AStarNextItem<>(next, 1));
          }
          return result;
        }
    );

    AStarItem<Vec> endItem = aStar.findShortestPaths(startPos, endPos);

    List<List<Vec>> allPaths = aStar.getAllPaths(endItem);

    List<String> strPaths = new ArrayList<>();

    for (List<Vec> path : allPaths) {
      strPaths.add(getStrPath(path));
    }

    return strPaths;
  }

  private static String getStrPath(List<Vec> list) {
    StringBuilder path = new StringBuilder();
    Vec prev = null;
    for (Vec vec : list) {
      if (prev != null)
        path.append(dirctionToString(vec.subToNew(prev)));
      prev = vec;
    }
    return path.toString();
  }

  public static String dirctionToString(Vec direction) {
    return dir2str.get(direction);
  }

  public static List<String> directionsToStrings(List<Vec> directions) {
    return new ArrayList<>(directions.stream().map(KeypadBase::dirctionToString).toList());
  }
}
