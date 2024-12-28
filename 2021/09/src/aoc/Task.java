package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Task {
  private final Map<Vec, Integer> map = new HashMap<>();
  private int maxY = -1;
  private int maxX = -1;

  public void init() {
  }

  public void addLine(String input) {
    List<Integer> heights = Util.str2List(input).stream().mapToInt(Integer::parseInt).boxed().toList();
    maxY++;
    maxX = heights.size() - 1;
    for (int x = 0; x < heights.size(); x++) {
      map.put(new Vec(x, maxY), heights.get(x));
    }
  }

  public void afterParse() throws Exception {
    List<Vec> lowPoints = new ArrayList<>();
    int sumRiskLevelLowPoints = 0;
    for (Map.Entry<Vec, Integer> entry : map.entrySet()) {
      Vec vec = entry.getKey();
      int height = entry.getValue();
      boolean foundEqualOrLowerAdjacent = false;
      for (Vec dir : Vec.adjacent) {
        int h = map.getOrDefault(vec.addToNew(dir), 10);
        if (h <= height) {
          foundEqualOrLowerAdjacent = true;
          break;
        }
      }
      if (!foundEqualOrLowerAdjacent) {
        sumRiskLevelLowPoints += height + 1;
        lowPoints.add(vec);
      }
    }

    out("part 1", "sum risk level low points", sumRiskLevelLowPoints);

    // part 2
    Map<Vec, Long> lowPoint2BasinSize = new HashMap<>();

    Img img = new Img();

    for (Vec lowPoint : lowPoints) {
      Set<Vec> basin = getBasin(lowPoint);
      lowPoint2BasinSize.put(lowPoint, (long) basin.size());
      basin.forEach(vec -> img.add(vec, col4int(map.get(vec))));
    }

    img.writeSVG();

    Long multiply3maxBasinsSizes = lowPoint2BasinSize.values().stream().sorted().toList().reversed().subList(0, 3).stream().reduce((accumulated, nextVal) -> accumulated * nextVal).orElseThrow();
    out("part 2", multiply3maxBasinsSizes);
  }

  private Color col4int(int val) {
    if (val < 0 || val > 8) return Color.RED;
    return Color.getHSBColor(0.7f - 15f / 360 * val, 1.0f, 0.5f);
  }

  private Set<Vec> getBasin(Vec lowPoint) {
    Set<Vec> visited = new HashSet<>();
    getBasin(lowPoint, visited);
    return visited;
  }

  private void getBasin(Vec point, Set<Vec> visited) {
    Integer h = map.getOrDefault(point, 9);
    if (h >= 9) return;

    if (visited.add(point)) {
      for (Vec dir : Vec.adjacent) {
        getBasin(point.addToNew(dir), visited);
      }
    }
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
    return img.toConsoleString();
  }
}
