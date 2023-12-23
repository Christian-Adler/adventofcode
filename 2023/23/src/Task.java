import java.util.*;

public class Task {
  private final Map<Pos, String> map = new HashMap<>();
  private final Set<Pos> walls = new HashSet<>();
  private Pos start = null;
  private Pos end = null;
  int maxY = -1;
  int maxX = -1;

  private final Map<String, Pos> directionMap = new HashMap<>();
  private final Set<PosDir> traps = new HashSet<>();

  public void init() {
    directionMap.put(">", new Pos(1, 0));
    directionMap.put("<", new Pos(-1, 0));
    directionMap.put("^", new Pos(0, -1));
    directionMap.put("v", new Pos(0, 1));
  }

  public void addLine(String input) {
    maxY++;
    maxX = input.length() - 1;

    ArrayList<String> asList = Util.str2List(input);
    for (int x = 0; x < asList.size(); x++) {
      String s = asList.get(x);
      Pos p = new Pos(x, maxY, s);
      if (s.equals("#"))
        walls.add(p);
      else
        map.put(p, s);
    }
  }

  public void afterParse() {
    // find start and end
    findStartAndEnd();

    out(toStringConsole());

    LinkedHashSet<Pos> maxPath = findMaxPath(new PosDir(start, Pos.DOWN), new LinkedHashSet<>());

    assert maxPath != null;
    out();
    out(toStringConsole(maxPath));

    out("Part 1", maxPath.size() - 1);
  }

  private LinkedHashSet<Pos> findMaxPath(PosDir posDir, LinkedHashSet<Pos> visited) {
    Pos actPos = posDir.pos();
    Pos dir = posDir.dir();
    // End?
    if (actPos.equals(end)) {
      visited.add(actPos);
      return visited;
    }

    // Trap
    if (traps.contains(posDir))
      return null;

    String pathItem = map.get(actPos);

    Pos forcedDir = directionMap.get(pathItem);

    // forced dir - only one choice
    if (forcedDir != null) {
      // from dir the opposite? Abort
      if (forcedDir.isOpposite(dir))
        return null;

      visited.add(actPos);
      Pos nextPos = actPos.addToNew(forcedDir);
      return findMaxPath(new PosDir(nextPos, forcedDir), visited);
    }

    // check all adjacent
    LinkedHashSet<Pos> maxVisited = null;
    for (Pos adjacentDir : Pos.adjacent) {
      Pos adjacent = actPos.addToNew(adjacentDir);
      if (visited.contains(adjacent))
        continue; // no way back
      PosDir nextPosDir = new PosDir(adjacent, adjacentDir);
      if (traps.contains(nextPosDir))
        continue;
      if (map.containsKey(adjacent)) {
        LinkedHashSet<Pos> nextVisited = new LinkedHashSet<>(visited);
        nextVisited.add(actPos);
        LinkedHashSet<Pos> tmpMaxVisited = findMaxPath(nextPosDir, nextVisited);
        if (tmpMaxVisited == null) // No path to end found
        {
          traps.add(nextPosDir);
          continue;
        }
        if (maxVisited == null || tmpMaxVisited.size() > maxVisited.size())
          maxVisited = tmpMaxVisited;
      }
    }
    return maxVisited;
  }

  private void findStartAndEnd() {
    for (int x = 0; x <= maxX; x++) {
      Pos posS = new Pos(x, 0);
      Pos posE = new Pos(x, maxY);
      if (".".equals(map.get(posS))) {
        start = posS;
      }
      if (".".equals(map.get(posE))) {
        end = posE;
      }
    }
  }

  public void out(Object... str) {
    Util.out(str);
  }

  public String toStringSVG() {
    SVG svg = new SVG();
    return svg.toSVGStringAged();
  }


  public String toStringConsole() {
    return toStringConsole(null);
  }

  public String toStringConsole(Collection<Pos> optPath) {
    SVG svg = new SVG();
    map.forEach(svg::add);
    walls.forEach(svg::add);
    if (optPath != null)
      optPath.forEach(p -> {
        svg.add(p, "O");
      });
    svg.add(start, "S");
    svg.add(end, "E");

    return svg.toConsoleString();
  }
}
