import java.util.*;

public class Task {
  private int maxY = -1;
  private int maxX = 0;
  private final Set<Pos> galaxies = new HashSet<>();

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    maxX = input.length() - 1;
    for (int x = 0; x < input.length(); x++) {
      if (input.charAt(x) == '#')
        galaxies.add(new Pos(x, maxY));
    }
  }

  public void afterParse(long expandFactor) {
    // expand
    // rows
    List<Integer> expandRows = new ArrayList<>();
    for (int y = 0; y <= maxY; y++) {
      boolean foundGalaxy = false;
      for (int x = 0; x <= maxX; x++) {
        Pos check = new Pos(x, y);
        if (galaxies.contains(check)) {
          foundGalaxy = true;
          break;
        }
      }

      if (!foundGalaxy)
        expandRows.add(y);
    }
    out("To expand rows", expandRows);

    // columns
    List<Integer> expandColumns = new ArrayList<>();
    for (int x = 0; x <= maxX; x++) {
      boolean foundGalaxy = false;
      for (int y = 0; y <= maxY; y++) {
        Pos check = new Pos(x, y);
        if (galaxies.contains(check)) {
          foundGalaxy = true;
          break;
        }
      }

      if (!foundGalaxy)
        expandColumns.add(x);
    }
    out("To expand cols", expandColumns);

    // Go through reverse!
    Collections.reverse(expandRows);
    Collections.reverse(expandColumns);

    // rows
    for (Integer expandRow : expandRows) {
      Set<Pos> toUpdate = new HashSet<>();
      for (Pos galaxy : galaxies) {
        if (galaxy.y > expandRow)
          toUpdate.add(galaxy);
      }

      galaxies.removeAll(toUpdate);
      for (Pos galaxy : toUpdate) {
        galaxies.add(galaxy.addToNew(0, expandFactor));
      }
    }
    // cols
    for (Integer expandRow : expandColumns) {
      Set<Pos> toUpdate = new HashSet<>();
      for (Pos galaxy : galaxies) {
        if (galaxy.x > expandRow)
          toUpdate.add(galaxy);
      }

      galaxies.removeAll(toUpdate);
      for (Pos galaxy : toUpdate) {
        galaxies.add(galaxy.addToNew(expandFactor, 0));
      }
    }

    // Sum up every pair distance
    long sumDistances = 0;
    List<Pos> galaxyList = new ArrayList<>(galaxies);
    for (int i = 0; i < galaxyList.size() - 1; i++) {
      Pos actGalaxy = galaxyList.get(i);

      for (int j = i + 1; j < galaxyList.size(); j++) {
        Pos pairGalaxy = galaxyList.get(j);

        sumDistances += actGalaxy.manhattanDistance(pairGalaxy);
      }
    }

    out("Part 1", "sum distances", sumDistances);
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
