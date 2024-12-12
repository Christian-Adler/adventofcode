import java.util.*;

public class Task {
  private final Map<Pos, String> map = new HashMap<>();
  private int maxY = -1;

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    ArrayList<String> blocks = Util.str2List(input);
    for (int x = 0; x < blocks.size(); x++) {
      String b = blocks.get(x);
      map.put(new Pos(x, maxY, b), b);
    }
  }

  public void afterParse() {
    // out(toStringConsole());

    Set<Pos> visited = new HashSet<>();
    ArrayList<Set<Pos>> areas = new ArrayList<>();

    for (Map.Entry<Pos, String> entry : map.entrySet()) {
      String plant = entry.getValue();
      Pos pos = entry.getKey();
      if (visited.contains(pos))
        continue;
      Set<Pos> area = findTouchedArea(pos, plant);
      visited.addAll(area);
      areas.add(area);
    }

    long prize = 0;
    long prize2 = 0;
    out("areas:", areas.size());
    for (Set<Pos> area : areas) {
      // out("area", area.size(), getAreaPerimeter(area));
      prize += (long) area.size() * getAreaPerimeter(area);
      out("area", area.iterator().next().color, area.size(), getAreaSides(area));
      prize2 += (long) area.size() * getAreaSides(area);
    }
    out("part 1", "prize", prize);
    out("part 2", "prize", prize2);
  }

  private int getAreaSides(Set<Pos> area) {
    int minX = area.stream().min(Comparator.comparingInt(p -> p.x)).orElseThrow().x;
    int maxX = area.stream().max(Comparator.comparingInt(p -> p.x)).orElseThrow().x;
    int minY = area.stream().min(Comparator.comparingInt(p -> p.y)).orElseThrow().y;
    int maxY = area.stream().max(Comparator.comparingInt(p -> p.y)).orElseThrow().y;

    int sides = 0;

    // Scan for y
    for (int y = minY; y <= maxY; y++) {
      List<Integer> positionsNoNeighborBelow = new ArrayList<>();
      List<Integer> positionsNoNeighborAbove = new ArrayList<>();
      for (int x = minX; x <= maxX; x++) {
        Pos pos = new Pos(x, y);
        if (area.contains(pos)) {
          if (!area.contains(pos.addToNew(Pos.DOWN)))
            positionsNoNeighborBelow.add(x);
          if (!area.contains(pos.addToNew(Pos.UP)))
            positionsNoNeighborAbove.add(x);
        }
      }
      sides += countGroups(positionsNoNeighborBelow);
      sides += countGroups(positionsNoNeighborAbove);
    }

    // Scan for x
    for (int x = minX; x <= maxX; x++) {
      List<Integer> positionsNoNeighborLeft = new ArrayList<>();
      List<Integer> positionsNoNeighborRight = new ArrayList<>();
      for (int y = minY; y <= maxY; y++) {
        Pos pos = new Pos(x, y);
        if (area.contains(pos)) {
          if (!area.contains(pos.addToNew(Pos.LEFT)))
            positionsNoNeighborLeft.add(y);
          if (!area.contains(pos.addToNew(Pos.RIGHT)))
            positionsNoNeighborRight.add(y);
        }
      }
      sides += countGroups(positionsNoNeighborLeft);
      sides += countGroups(positionsNoNeighborRight);
    }

    return sides;
  }

  private int countGroups(List<Integer> sortedList) {
    int groups = 0;
    int prev = -2;
    for (Integer i : sortedList) {
      if (i > prev + 1)
        groups++;
      prev = i;
    }

    return groups;
  }

  private int getAreaPerimeter(Set<Pos> area) {
    int perimeter = 0;
    for (Pos pos : area) {
      for (Pos dir : Pos.adjacent) {
        if (!area.contains(pos.addToNew(dir)))
          perimeter++;
      }
    }
    return perimeter;
  }

  private Set<Pos> findTouchedArea(Pos startPos, String plant) {
    Set<Pos> area = new HashSet<>();

    LinkedList<Pos> workList = new LinkedList<>();
    workList.add(startPos);

    while (!workList.isEmpty()) {
      Pos pos = workList.removeFirst();

      String p = map.get(pos);
      if (plant.equals(p)) {
        if (area.add(pos)) {
          for (Pos dir : Pos.adjacent) {
            Pos nextPos = pos.addToNew(dir);
            if (area.contains(nextPos))
              continue;
            workList.add(nextPos);
          }
        }
      }
    }

    return area;
  }

  public void out(Object... str) {
    Util.out(str);
  }


  public String toStringSVG() {
    SVG svg = new SVG();
    return svg.toSVGStringAged();
  }

  public String toStringConsole() {
    SVG svg = new SVG();
    for (Pos pos : map.keySet()) {
      svg.add(pos);
    }
    return svg.toConsoleString();
  }
}
