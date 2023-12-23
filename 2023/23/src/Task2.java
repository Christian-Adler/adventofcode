import java.io.IOException;
import java.util.*;

public class Task2 {
  private final Map<Pos, Node> map = new HashMap<>();
  private final Set<Pos> ways = new HashSet<>();
  private final Set<Pos> walls = new HashSet<>();
  private Pos start = null;
  private Pos end = null;
  int maxY = -1;
  int maxX = -1;

  private final Set<Pos> traps = new HashSet<>();

  public void init() {
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
      else {
        ways.add(p);
        map.put(p, new Node(p));
      }
    }
  }

  public void afterParse() throws IOException {
    // find start and end
    findStartAndEnd();

    // link nodes
    for (Node node : map.values()) {
      Pos pos = node.pos;
      for (Pos adjacentDir : Pos.adjacent) {
        Node adjacent = map.get(pos.addToNew(adjacentDir));
        if (adjacent == null)
          continue;

        node.neighbors.put(adjacent, 1);
        adjacent.neighbors.put(node, 1);
      }
    }
//    out(map);

    // shrink graph
    Pos directPathNodes = map.values().stream().filter(n -> n.neighbors.size() == 2).map(n -> n.pos).findFirst().orElse(null);
    while (directPathNodes != null) {
//      out("remove", directPathNodes);
      Node node = map.remove(directPathNodes);
      if (node == null)
        continue;
      // get both neighbors
      if (node.neighbors.size() != 2) throw new IllegalStateException("unexpected");

      Node n1 = null;
      int weight1 = 0;
      Node n2 = null;
      int weight2 = 0;
      for (Map.Entry<Node, Integer> entry : node.neighbors.entrySet()) {
        if (n1 == null) {
          n1 = entry.getKey();
          weight1 = entry.getValue();
        } else {
          n2 = entry.getKey();
          weight2 = entry.getValue();
        }
      }
      if (n1 == null || n2 == null) throw new IllegalStateException("unexpected");
      int sumWeight = weight1 + weight2;
      n1.neighbors.remove(node);
      n2.neighbors.remove(node);
      if (n1.neighbors.containsKey(n2)) // direkter Ring gefunden? 2 Knoten mit 2 direkten Pfaden verbunden?
        n1.neighbors.put(n2, Math.max(n1.neighbors.get(n2), sumWeight));
      else
        n1.neighbors.put(n2, sumWeight);
      if (n2.neighbors.containsKey(n1))
        n2.neighbors.put(n1, Math.max(n2.neighbors.get(n1), sumWeight));
      else
        n2.neighbors.put(n1, sumWeight);

//      out(map);

      directPathNodes = map.values().stream().filter(n -> n.neighbors.size() == 2).map(n -> n.pos).findFirst().orElse(null);
    }

    out(map.size());
    out(map);

    SVG svg = new SVG();
    for (Pos pos : map.keySet()) {
      svg.add(pos, "#ff0000");
    }
    Util.writeToAOCSvg(svg.toSVGString());

//
////    out(toStringConsole());
//
    LinkedHashSet<Node> maxPath = findMaxPath();
    out(maxPath);
//
//    assert maxPath != null;
//    out();
//    out(toStringConsole(maxPath));
//
//    Util.writeToAOCSvg(toStringSVG(maxPath));

//    out("Part 2", maxPath.size() - 1);
  }

  private LinkedHashSet<Node> findMaxPath() {

    int maxPath = 0;
    LinkedHashSet<Node> maxPathList = null;

    List<WorkListItem> worklist = new LinkedList<>();
    worklist.add(new WorkListItem(map.get(start), 0, new LinkedHashSet<>(), 0));

    long counter = 0;

    while (!worklist.isEmpty()) {
      counter++;

      if (counter % 100000 == 0)
        out(counter, "worklistsize", worklist.size());

      WorkListItem actItem = worklist.removeFirst();
      LinkedHashSet<Node> visited = actItem.visited();
      Node actNode = actItem.node();

      // End?
      if (actItem.node().pos.equals(end)) {
        visited.add(actNode);
        int pathLength = actItem.visitedPathLength();
        if (pathLength > maxPath) {
          out("Found end with steps", pathLength);
          maxPath = pathLength;
          maxPathList = visited;
        }
      }

      for (Map.Entry<Node, Integer> entry : actNode.neighbors.entrySet()) {
        Node neighbor = entry.getKey();
        int pathLength = entry.getValue();
        if (visited.contains(neighbor))
          continue; // no way back

        LinkedHashSet<Node> nextVisited = new LinkedHashSet<>(visited);
        nextVisited.add(actNode);
        worklist.add(new WorkListItem(neighbor, pathLength, nextVisited, actItem.visitedPathLength() + pathLength));
      }
    }

    out("maxPathLength", maxPath);

    return maxPathList;
  }

  private void findStartAndEnd() {
    for (int x = 0; x <= maxX; x++) {
      Pos posS = new Pos(x, 0);
      Pos posE = new Pos(x, maxY);
      if (map.containsKey(posS)) {
        start = posS;
      }
      if (map.containsKey(posE)) {
        end = posE;
      }
    }
  }

  public void out(Object... str) {
    Util.out(str);
  }

  public String toStringSVG(Collection<Pos> optPath) {
    SVG svg = new SVG();
    for (Pos wall : walls) {
      svg.add(wall, "#a0a0a0");
    }
    for (Pos pos : optPath) {
      svg.add(pos);
    }
    return svg.toSVGStringAged();
  }


  public String toStringConsole() {
    return toStringConsole(null);
  }

  public String toStringConsole(Collection<Pos> optPath) {
    SVG svg = new SVG();
    map.keySet().forEach(svg::add);
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
