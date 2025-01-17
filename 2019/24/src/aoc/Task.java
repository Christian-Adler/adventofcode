package aoc;

import aoc.util.Img;
import aoc.util.Vec;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt", 10);
    // runForInput("./input_example_2.txt", 100);
    runForInput("./input.txt", 200);
  }


  public String part1(List<String> lines, Object... params) throws Exception {
    Set<Vec> bugs = new HashSet<>();
    Vec maxXY = Vec.mapInputVisitor(lines, (vec, string) -> {
      if (string.equals("#"))
        bugs.add(vec);
    });

    // out(toStringConsole(bugs, maxXY));

    HashSet<String> cache = new HashSet<>();
    cache.add(buildCacheKey(bugs));

    long biodiversity = -1;

    while (true) {
      step(bugs, maxXY);
      // out(toStringConsole(bugs, maxXY));

      if (!cache.add(buildCacheKey(bugs))) {
        // out("found duplicate");
        biodiversity = biodiversity(bugs, maxXY);
        break;
      }
    }

    return String.valueOf(biodiversity);
  }

  public String part2(List<String> lines, Object... params) throws Exception {
    Set<Vec> bugs = new HashSet<>();
    Vec.mapInputVisitor(lines, (vec, string) -> {
      if (string.equals("#"))
        bugs.add(vec);
    });

    Grid grid0 = new Grid(0);
    for (Vec bug : bugs) {
      int bugPos = bug.y * 5 + bug.x;
      grid0.setBug(bugPos);
    }

    // grid0.printAll();
    for (int i = 0; i < (int) params[0]; i++) {
      grid0.step();
    }
    out("After", params[0], "min");
    // grid0.printAll();

    return String.valueOf(grid0.countAllBugs());
  }

  private void step(Set<Vec> bugs, Vec maxXY) {
    Set<Vec> nextGen = new HashSet<>();

    for (int y = 0; y <= maxXY.y; y++) {
      for (int x = 0; x <= maxXY.x; x++) {
        Vec v = new Vec(x, y);
        boolean isBug = bugs.contains(v);
        int countAdjacent = 0;
        for (Vec dir : Vec.adjacent) {
          if (bugs.contains(v.add(dir)))
            countAdjacent++;
        }

        if (isBug && countAdjacent == 1
            || !isBug && countAdjacent >= 1 && countAdjacent <= 2)
          nextGen.add(v);
      }
    }

    bugs.clear();
    bugs.addAll(nextGen);
  }

  private long biodiversity(Set<Vec> bugs, Vec maxXY) {
    long biodiversity = 0;
    long power = 1;
    for (int y = 0; y <= maxXY.y; y++) {
      for (int x = 0; x <= maxXY.x; x++) {
        boolean isBug = bugs.contains(new Vec(x, y));
        if (isBug)
          biodiversity += power;
        power *= 2;
      }
    }

    return biodiversity;
  }

  private String buildCacheKey(Set<Vec> bugs) {
    return bugs.stream().sorted().map(Vec::toString).collect(Collectors.joining("_"));
  }

  public String toStringConsole(Collection<Vec> vecs, Vec maxXY) {
    Img img = new Img();
    img.add(Vec.ZERO, ".");
    img.add(maxXY, ".");
    for (Vec vec : vecs) {
      img.add(vec);
    }
    return img.toConsoleString('.');
  }
}
