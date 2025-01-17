package aoc;

import aoc.util.Img;
import aoc.util.Vec;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grid {
  public Grid outerGrid = null;
  public Grid innerGrid = null;
  public final int level;
  private final Set<Integer> bugs = new HashSet<>();
  private final Set<Integer> nextGen = new HashSet<>();

  public Grid(int level) {
    this.level = level;
  }

  public void setBug(int bugPos) {
    bugs.add(bugPos);
  }

  public boolean hasBugAt(int bugPos) {
    return bugs.contains(bugPos);
  }

  public long countAllBugs() {
    Grid grid = this;
    while (grid.outerGrid != null) {
      grid = grid.outerGrid;
    }

    long numBugs = grid.bugs.size();
    while (grid.innerGrid != null) {
      grid = grid.innerGrid;
      numBugs += grid.bugs.size();
    }
    return numBugs;
  }

  public void printAll() {
    Grid grid = this;
    while (grid.outerGrid != null) {
      grid = grid.outerGrid;
    }

    System.out.println(grid);
    while (grid.innerGrid != null) {
      grid = grid.innerGrid;
      System.out.println(grid);
    }
  }

  public void step() {
    calcNextGen();
    useNextGen();
  }

  protected void useNextGen() {
    Grid grid = this;
    while (grid.outerGrid != null) {
      grid = grid.outerGrid;
    }

    while (grid != null) {
      grid.bugs.clear();
      grid.bugs.addAll(grid.nextGen);
      grid.nextGen.clear();

      grid = grid.innerGrid;
    }
  }

  protected void calcNextGen() {
    Grid grid = this;
    while (grid.outerGrid != null) {
      grid = grid.outerGrid;
    }

    if (!grid.bugs.isEmpty()) {
      grid.outerGrid = new Grid(grid.level - 1);
      grid.outerGrid.innerGrid = grid;
      grid = grid.outerGrid;
    }

    while (grid != null) {
      calcNextGen(grid);
      if (grid.innerGrid != null)
        grid = grid.innerGrid;
      else if (!grid.bugs.isEmpty()) {
        grid.innerGrid = new Grid(grid.level + 1);
        grid.innerGrid.outerGrid = grid;
        grid = grid.innerGrid;
      } else
        grid = null;
    }
  }

  private static void calcNextGen(Grid grid) {
    for (Map.Entry<Integer, Adjacent> adjacentEntry : adjacentMap.entrySet()) {
      int pos = adjacentEntry.getKey();
      Adjacent adjacent = adjacentEntry.getValue();

      boolean isBug = grid.bugs.contains(pos);
      int countAdjacent = 0;

      for (Integer checkPos : adjacent.outer) {
        if (hasBug(grid.outerGrid, checkPos))
          countAdjacent++;
      }
      for (Integer checkPos : adjacent.same) {
        if (hasBug(grid, checkPos))
          countAdjacent++;
      }
      for (Integer checkPos : adjacent.inner) {
        if (hasBug(grid.innerGrid, checkPos))
          countAdjacent++;
      }

      if (isBug && countAdjacent == 1
          || !isBug && countAdjacent >= 1 && countAdjacent <= 2)
        grid.nextGen.add(pos);
    }
  }


  private static boolean hasBug(Grid grid, int bugPos) {
    if (grid == null) return false;
    return grid.hasBugAt(bugPos);
  }

  @Override
  public String toString() {
    Img img = new Img();
    img.add(Vec.ZERO, ".");
    img.add(new Vec(4, 4), ".");
    img.add(new Vec(2, 2), "?");
    for (int bugPos : bugs) {
      int x = bugPos % 5;
      int y = (bugPos - x) / 5;
      img.add(new Vec(x, y));
    }
    return "\r\nGrid " + level + ":\r\n" + img.toConsoleString('.');
  }

  /*
     |     |         |     |
  0  |  1  |    2    |  3  |  4
     |     |         |     |
-----+-----+---------+-----+-----
     |     |         |     |
  5  |  6  |    7    |  8  |  9
     |     |         |     |
-----+-----+---------+-----+-----
     |     |A|B|C|D|E|     |
     |     |-+-+-+-+-|     |
     |     |F|G|H|I|J|     |
     |     |-+-+-+-+-|     |
 10  | 11  |K|L|?|N|O|  13 |  14
     |     |-+-+-+-+-|     |
     |     |P|Q|R|S|T|     |
     |     |-+-+-+-+-|     |
     |     |U|V|W|X|Y|     |
-----+-----+---------+-----+-----
     |     |         |     |
 15  | 16  |    17   |  18 |  19
     |     |         |     |
-----+-----+---------+-----+-----
     |     |         |     |
 20  | 21  |    22   |  23 |  24
     |     |         |     |
  * */
  private static final Map<Integer, Adjacent> adjacentMap = new HashMap<>();

  static {
    adjacentMap.put(0, new Adjacent(intSet(7, 11), intSet(1, 5), intSet()));
    adjacentMap.put(1, new Adjacent(intSet(7), intSet(0, 2, 6), intSet()));
    adjacentMap.put(2, new Adjacent(intSet(7), intSet(1, 3, 7), intSet()));
    adjacentMap.put(3, new Adjacent(intSet(7), intSet(2, 4, 8), intSet()));
    adjacentMap.put(4, new Adjacent(intSet(7, 13), intSet(3, 9), intSet()));
    adjacentMap.put(5, new Adjacent(intSet(11), intSet(0, 6, 10), intSet()));
    adjacentMap.put(6, new Adjacent(intSet(), intSet(1, 5, 7, 11), intSet()));
    adjacentMap.put(7, new Adjacent(intSet(), intSet(2, 6, 8), intSet(0, 1, 2, 3, 4)));
    adjacentMap.put(8, new Adjacent(intSet(), intSet(3, 7, 9, 13), intSet()));
    adjacentMap.put(9, new Adjacent(intSet(13), intSet(4, 8, 14), intSet()));
    adjacentMap.put(10, new Adjacent(intSet(11), intSet(5, 11, 15), intSet()));
    adjacentMap.put(11, new Adjacent(intSet(), intSet(6, 10, 16), intSet(0, 5, 10, 15, 20)));
    // adjacentMap.put(12, new Adjacent(intSet(), intSet(), intSet())); = ?
    adjacentMap.put(13, new Adjacent(intSet(), intSet(8, 14, 18), intSet(4, 9, 14, 19, 24)));
    adjacentMap.put(14, new Adjacent(intSet(13), intSet(9, 13, 19), intSet()));
    adjacentMap.put(15, new Adjacent(intSet(11), intSet(10, 16, 20), intSet()));
    adjacentMap.put(16, new Adjacent(intSet(), intSet(11, 15, 17, 21), intSet()));
    adjacentMap.put(17, new Adjacent(intSet(), intSet(16, 18, 22), intSet(20, 21, 22, 23, 24)));
    adjacentMap.put(18, new Adjacent(intSet(), intSet(13, 17, 19, 23), intSet()));
    adjacentMap.put(19, new Adjacent(intSet(13), intSet(14, 18, 24), intSet()));
    adjacentMap.put(20, new Adjacent(intSet(11, 17), intSet(15, 21), intSet()));
    adjacentMap.put(21, new Adjacent(intSet(17), intSet(16, 20, 22), intSet()));
    adjacentMap.put(22, new Adjacent(intSet(17), intSet(17, 21, 23), intSet()));
    adjacentMap.put(23, new Adjacent(intSet(17), intSet(18, 22, 24), intSet()));
    adjacentMap.put(24, new Adjacent(intSet(13, 17), intSet(19, 23), intSet()));
  }

  private static Set<Integer> intSet(int... vals) {
    Set<Integer> set = new HashSet<>();
    for (int val : vals) {
      set.add(val);
    }
    return set;
  }

  private record Adjacent(Set<Integer> outer, Set<Integer> same, Set<Integer> inner) {

  }
}
