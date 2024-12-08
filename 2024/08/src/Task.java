import java.util.*;

public class Task {
  private final Map<String, List<Pos>> map = new HashMap<>();
  private final Set<Pos> antiNodes = new HashSet<>();

  int maxY = -1;
  int maxX = -1;

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    ArrayList<String> parts = Util.str2List(input);
    maxX = parts.size() - 1;

    for (int x = 0; x < parts.size(); x++) {
      String s = parts.get(x);
      if (s.equals("."))
        continue;
      List<Pos> posList = map.computeIfAbsent(s, k -> new ArrayList<>());
      posList.add(new Pos(x, maxY));
    }
  }

  public void afterParse() {
    // out(toStringConsole());

    calcAntiNodes(false);

    // out("");
    // out(toStringConsole());

    out("part 1", "antiNodes:", antiNodes.size());

    calcAntiNodes(true);

    // out("");
    // out(toStringConsole());

    out("part 2", "antiNodes:", antiNodes.size());
  }

  private void calcAntiNodes(boolean part2) {
    for (List<Pos> posList : map.values()) {
      List<Pair<Pos>> posPairs = buildPairs(posList);
      for (Pair<Pos> posPair : posPairs) {
        calcAntiNodes(posPair, part2);
      }
    }
  }

  private void calcAntiNodes(Pair<Pos> posPair, boolean part2) {
    Pos p1 = posPair.t1();
    Pos p2 = posPair.t2();

    if (!part2) {
      calcAntiNode(p1, p2);
      calcAntiNode(p2, p1);
    } else
      calcAntiNodesInLine(p1, p2);
  }

  private void calcAntiNode(Pos p1, Pos p2) {
    int x = p1.x - (p2.x - p1.x);
    int y = p1.y - (p2.y - p1.y);
    if (x < 0 || x > maxX || y < 0 || y > maxY)
      return;
    antiNodes.add(new Pos(x, y));
  }

  private void calcAntiNodesInLine(Pos p1, Pos p2) {
    int distX = p2.x - p1.x;
    int distY = p2.y - p1.y;

    int x = p1.x;
    int y = p1.y;
    while (!(x < 0 || x > maxX || y < 0 || y > maxY)) {
      antiNodes.add(new Pos(x, y));
      x -= distX;
      y -= distY;
    }
    x = p1.x;
    y = p1.y;
    while (!(x < 0 || x > maxX || y < 0 || y > maxY)) {
      antiNodes.add(new Pos(x, y));
      x += distX;
      y += distY;
    }
  }

  private List<Pair<Pos>> buildPairs(List<Pos> list) {
    List<Pair<Pos>> result = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      Pos p1 = list.get(i);
      for (int j = i + 1; j < list.size(); j++) {
        Pos p2 = list.get(j);
        result.add(new Pair<>(p1, p2));
      }
    }
    return result;
  }

  private record Pair<T>(T t1, T t2) {
  }

  public void out(Object... str) {
    Util.out(str);
  }

  public String toStringConsole() {
    SVG svg = new SVG();

    svg.add(new Pos(0, 0), ".");
    svg.add(new Pos(maxX, maxY), ".");

    for (Map.Entry<String, List<Pos>> entry : map.entrySet()) {
      String color = entry.getKey();
      for (Pos pos : entry.getValue()) {
        svg.add(pos, color);
      }
    }

    for (Pos antiNode : antiNodes) {
      svg.add(antiNode);
    }

    return svg.toConsoleString();
  }
}
