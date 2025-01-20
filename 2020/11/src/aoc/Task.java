package aoc;

import aoc.util.Img;
import aoc.util.Range;
import aoc.util.Vec;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    Set<Vec> map = new HashSet<>();
    Vec maxXY = Vec.mapInputVisitor(lines, (vec, string) -> {
      if (!string.equals(".")) map.add(vec);
    });
    Set<Vec> mapBackup = new HashSet<>(map);
    Set<Vec> occupied = new HashSet<>();

    // out(toStringConsole(map, occupied));

    while (true) {
      HashSet<Vec> tmp = new HashSet<>(occupied);
      step1(map, tmp);
      if (tmp.equals(occupied)) break;
      occupied = tmp;
    }

    out("part 1: ", occupied.size());

    map.clear();
    map.addAll(mapBackup);
    occupied.clear();

    // out(toStringConsole(map, occupied));

    while (true) {
      HashSet<Vec> tmp = new HashSet<>(occupied);
      step2(maxXY, map, tmp);
      if (tmp.equals(occupied)) break;
      occupied = tmp;
    }

    out("part 2: ", occupied.size());
  }

  private void step1(Set<Vec> map, Set<Vec> occupied) {
    Set<Vec> nextOccupied = new HashSet<>(occupied);
    for (Vec seat : map) {
      int countOccupiedNeighbors = getCountOccupiedNeighbors1(occupied, seat);
      if (!occupied.contains(seat) && countOccupiedNeighbors == 0)
        nextOccupied.add(seat);
      else if (occupied.contains(seat) && countOccupiedNeighbors >= 4)
        nextOccupied.remove(seat);
    }
    occupied.clear();
    occupied.addAll(nextOccupied);
  }

  private static int getCountOccupiedNeighbors1(Set<Vec> occupied, Vec seat) {
    int countOccupiedNeighbors = 0;
    for (Vec adjacent : Vec.adjacent8) {
      if (occupied.contains(seat.add(adjacent))) {
        countOccupiedNeighbors++;
      }
    }
    return countOccupiedNeighbors;
  }

  private void step2(Vec maxXY, Set<Vec> map, Set<Vec> occupied) {
    Range xRange = new Range(0, maxXY.x);
    Range yRange = new Range(0, maxXY.y);
    Set<Vec> nextOccupied = new HashSet<>(occupied);
    for (Vec seat : map) {
      int countOccupiedNeighbors = getCountOccupiedNeighbors2(map, occupied, seat, xRange, yRange);
      if (!occupied.contains(seat) && countOccupiedNeighbors == 0)
        nextOccupied.add(seat);
      else if (occupied.contains(seat) && countOccupiedNeighbors >= 5)
        nextOccupied.remove(seat);
    }
    occupied.clear();
    occupied.addAll(nextOccupied);
  }

  private static int getCountOccupiedNeighbors2(Set<Vec> map, Set<Vec> occupied, Vec seat, Range xRange, Range yRange) {
    int countOccupiedNeighbors = 0;
    for (Vec adjacent : Vec.adjacent8) {
      Vec tmp = seat;
      while (xRange.contains(tmp.x) && yRange.contains(tmp.y)) {
        tmp = tmp.add(adjacent);
        if (occupied.contains(tmp)) {
          countOccupiedNeighbors++;
          break;
        }
        if (map.contains(tmp)) break;
      }
    }
    return countOccupiedNeighbors;
  }


  public String toStringConsole(Collection<Vec> map, Set<Vec> occupied) {
    Img img = new Img();
    map.forEach(v -> img.add(v, "L"));
    occupied.forEach(v -> img.add(v, "#"));
    return img.toConsoleString();
  }
}
