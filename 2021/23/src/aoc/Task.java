package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task {
  private final Set<Vec> walls = new HashSet<>();
  private final Set<Vec> map = new HashSet<>();
  private final List<Amphipod> amphipods = new ArrayList<>();
  private final Set<Vec> possibleHallwayPositions = new HashSet<>(Stream.of(1, 2, 4, 6, 8, 10, 11).map(i -> new Vec(i, 1)).toList());

  private final Set<String> visited = new HashSet<>();

  private int maxY = -1;

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    int x = -1;
    for (String s : Util.str2List(input)) {
      x++;
      if (s.trim().isEmpty()) continue;
      if (s.equals("#"))
        walls.add(new Vec(x, maxY));
      else if (s.equals("."))
        map.add(new Vec(x, maxY));
      else if (List.of("A", "B", "C", "D").contains(s)) {
        map.add(new Vec(x, maxY));
        amphipods.add(new Amphipod(s, new Vec(x, maxY)));
      }
    }
  }

  public void afterParse() throws Exception {
    out(this);
    AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
    organize(amphipods, 0, min);

    out(Amphipod.spotHeight == 2 ? "part 1" : "part 2", "min energy", min);
  }


  private void organize(List<Amphipod> as, int depth, AtomicInteger min) {
    // out("\r\norganize", depth, "\r\n" + toStringConsole(as));

    String cacheKey = getCacheKey(as);
    if (visited.contains(cacheKey))
      return;
    visited.add(cacheKey);

    Map<Vec, Amphipod> pos2A = toMap(as);
    // Abbruch?
    if (as.stream().filter(a -> a.isInTarget(pos2A)).count() == as.size()) {
      int summedEnergy = getSummedEnergy(as);
      if (summedEnergy < min.get()) {
        min.set(summedEnergy);
        // out("\r\norganized", "depth", depth, "energy", summedEnergy, "\r\n" + toStringConsole(as));
      }
      return;
    }

    // already to expensive for finish
    int summedEnergy = getSummedEnergy(as);
    for (Amphipod a : as) {
      if (!a.isInTarget(pos2A))
        summedEnergy += a.costs(a.position.manhattanDistance(a.target()));
    }
    if (summedEnergy > min.get())
      return;

    // first try ways direct to target
    for (Amphipod a : as) {
      if (a.isInTarget(pos2A)) continue;
      List<Vec> wayToTarget = a.wayToTarget(pos2A);
      if (wayToTarget != null) {
        List<Amphipod> copyAs = copy(as);
        copyAs.remove(a);
        Amphipod copyA = a.copy();
        copyA.position = wayToTarget.getLast();
        copyA.usedEnergy += a.costs(wayToTarget.size());
        copyAs.add(copyA);

        organize(copyAs, depth + 1, min);
      }
    }

    // step into hallway
    for (Amphipod a : as) {
      if (a.isInTarget(pos2A)) continue;
      if (a.isInHallway()) continue;

      for (Vec possibleHallwayPosition : possibleHallwayPositions) {
        List<Vec> wayToTarget = a.wayToHallway(possibleHallwayPosition, pos2A);

        if (wayToTarget != null) {
          List<Amphipod> copyAs = copy(as);
          copyAs.remove(a);
          Amphipod copyA = a.copy();
          copyA.position = wayToTarget.getLast();
          copyA.usedEnergy += a.costs(wayToTarget.size());
          copyAs.add(copyA);

          organize(copyAs, depth + 1, min);
        }
      }
    }
  }

  private static int getSummedEnergy(List<Amphipod> as) {
    return as.stream().mapToInt(Amphipod::getUsedEnergy).sum();
  }

  private static String getCacheKey(List<Amphipod> as) {
    return as.stream().map(a -> a.type + a.position).sorted().collect(Collectors.joining("-")) + "__" + getSummedEnergy(as);
  }

  private static Map<Vec, Amphipod> toMap(List<Amphipod> as) {
    Map<Vec, Amphipod> pos2A = new HashMap<>();
    for (Amphipod amphipod : as) {
      pos2A.put(amphipod.position, amphipod);
    }
    return pos2A;
  }

  private List<Amphipod> copy(List<Amphipod> as) {
    return new ArrayList<>(as.stream().map(Amphipod::copy).toList());
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return toStringConsole(amphipods);
  }

  public void toBmp() throws Exception {
    Img img = new Img();
    img.writeBitmapAged();
  }

  public String toStringConsole(List<Amphipod> amphipods) {
    Img img = new Img();
    walls.forEach(w -> img.add(w, "#"));
    map.forEach(w -> img.add(w, "."));
    for (Amphipod amphipod : amphipods) {
      img.add(amphipod.position, amphipod.type);
    }
    return img.toConsoleString(' ');
  }
}
