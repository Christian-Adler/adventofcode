package aoc;

import aoc.util.Vec;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public void part1(List<String> lines, Object... params) throws Exception {
    Set<Vec> trees = new HashSet<>();
    Vec maxXY = Vec.mapInputVisitor(lines, (vec, string) -> {
      if (string.equals("#"))
        trees.add(vec);
    });

    Vec rightDown = new Vec(3, 1);
    long countTrees = getTreeCount(trees, maxXY, rightDown);

    out("part 1", countTrees);

    countTrees = 1;
    for (Vec vec : List.of(new Vec(1, 1), new Vec(3, 1), new Vec(5, 1), new Vec(7, 1), new Vec(1, 2))) {
      int treeCount = getTreeCount(trees, maxXY, vec);
      countTrees *= treeCount;
    }
    out("part 2", countTrees);

  }

  private static int getTreeCount(Set<Vec> trees, Vec maxXY, Vec rightDown) {
    Vec actPos = new Vec(0, 0);
    int countTrees = 0;
    if (trees.contains(actPos)) countTrees++;
    while (actPos.y <= maxXY.y) {
      actPos = new Vec((actPos.x + rightDown.x) % (maxXY.x + 1), actPos.y + rightDown.y);
      // out(actPos);
      if (trees.contains(actPos)) countTrees++;
    }
    return countTrees;
  }
}
