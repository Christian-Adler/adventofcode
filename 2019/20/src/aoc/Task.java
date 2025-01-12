package aoc;

import aoc.util.Img;
import aoc.util.UnicodeCharacters;
import aoc.util.Util;
import aoc.util.Vec;
import aoc.util.astar.AStar;
import aoc.util.astar.AStarItem;
import aoc.util.astar.AStarNextItem;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Task {

  private final Set<Vec> passages = new HashSet<>();
  private final Map<Vec, String> letters = new HashMap<>();

  private int maxY = -1;
  private int maxX = -1;


  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    maxX = Math.max(maxX, input.length() - 1);
    int x = -1;
    for (String s : Util.str2List(input)) {
      x++;
      if (s.equals(" ") || s.equals("#")) continue;
      if (s.equals("."))
        passages.add(new Vec(x, maxY));
      else {
        letters.put(new Vec(x, maxY), s);
      }
    }
  }

  public void afterParse() throws Exception {
    // build portals

    Map<String, Portal> key2portal = new HashMap<>();

    List<Vec> rightDownDirs = List.of(Vec.RIGHT, Vec.DOWN);

    for (Map.Entry<Vec, String> entry : letters.entrySet()) {
      Vec v1 = entry.getKey();
      String letter = entry.getValue();

      for (Vec dir : rightDownDirs) {
        Vec v2 = v1.add(dir);
        String letter2 = letters.get(v2);

        if (letter2 != null) {
          String key = letter + letter2;
          Vec passagePoint = findPassagePoint(v1, v2);
          if (passagePoint == null) throw new IllegalStateException("found no portal passage");

          Portal portal = key2portal.get(key);
          if (portal == null)
            portal = new Portal(key, passagePoint, null);
          else
            portal = new Portal(key, portal.p1(), passagePoint);
          key2portal.put(key, portal);

          break;
        }
      }
    }


    out(this);
    toBmp();

    // out(start, end);
    // out(key2portal);


    // build graph
    Map<String, Map<String, PortalInfo>> graph = new HashMap<>();

    for (Portal portal : key2portal.values()) {
      List<Vec> startVecs = new ArrayList<>();
      startVecs.add(portal.p1());
      if (portal.p2() != null)
        startVecs.add(portal.p2());

      Map<String, PortalInfo> targetMap = graph.computeIfAbsent(portal.key(), k -> new HashMap<>());

      for (Portal portal2 : key2portal.values()) {
        if (portal.key().equals(portal2.key())) continue;
        if (targetMap.containsKey(portal2.key())) continue;

        List<Vec> endVecs = new ArrayList<>();
        endVecs.add(portal2.p1());
        if (portal2.p2() != null)
          endVecs.add(portal2.p2());

        Map<String, PortalInfo> target2Map = graph.computeIfAbsent(portal2.key(), k -> new HashMap<>());

        for (Vec startVec : startVecs) {
          for (Vec endVec : endVecs) {
            Long shortestPathLen = findShortestPath(startVec, endVec);
            if (shortestPathLen != null) {

              targetMap.put(portal2.key(), new PortalInfo(shortestPathLen + (isStartOrEnd(portal2.key()) ? 0 : 1), isOuterPortal(endVec)));// +1 = step through portal
              target2Map.put(portal.key(), new PortalInfo(shortestPathLen + (isStartOrEnd(portal.key()) ? 0 : 1), isOuterPortal(startVec)));
            }
          }
        }
      }
    }

    // out("graph", graph);


    Set<String> visited = new HashSet<>();
    AtomicLong minPathLen = new AtomicLong(Long.MAX_VALUE);
    List<String> minPath = new ArrayList<>();
    List<String> soFarPath = new ArrayList<>();
    findShortestPath("AA", 0, soFarPath, visited, minPathLen, minPath, graph);
    // out(minPath);
    out("part 1", minPathLen);
  }

  private boolean isStartOrEnd(String s) {
    return s.equals("AA") || s.equals("ZZ");
  }

  private boolean isOuterPortal(Vec vec) {
    return vec.x == 2 || vec.y == 2 || vec.x == maxX - 2 || vec.y == maxY - 2;
  }

  private void findShortestPath(String actNode, long soFarLen, List<String> soFarPath, Set<String> visited, AtomicLong minPathLen, List<String> minPath, Map<String, Map<String, PortalInfo>> graph) {
    visited.add(actNode);
    soFarPath.add(actNode);

    if (actNode.equals("ZZ")) {
      if (soFarLen < minPathLen.get()) {
        minPathLen.set(soFarLen);
        minPath.clear();
        minPath.addAll(soFarPath);
      }
      return;
    }

    if (soFarLen > minPathLen.get()) return;


    Map<String, PortalInfo> neighbours = graph.get(actNode);
    for (Map.Entry<String, PortalInfo> entry : neighbours.entrySet()) {
      String nextNode = entry.getKey();
      if (visited.contains(nextNode)) continue;

      findShortestPath(nextNode, soFarLen + entry.getValue().distance(), new ArrayList<>(soFarPath), new HashSet<>(visited), minPathLen, minPath, graph);
    }
  }

  private Long findShortestPath(Vec v1, Vec v2) {
    AStar<Vec> aStar = new AStar<>((vec, vec2) -> (long) vec.manhattanDistance(vec2), vec -> {
      List<AStarNextItem<Vec>> nextItems = new ArrayList<>();
      for (Vec dir : Vec.adjacent) {
        if (passages.contains(vec.add(dir)))
          nextItems.add(new AStarNextItem<>(vec.add(dir), 1));
      }
      return nextItems;
    });
    AStarItem<Vec> shortestPathItem = aStar.findShortestPath(v1, v2);
    if (shortestPathItem != null) return shortestPathItem.score;
    return null;
  }

  private Vec findPassagePoint(Vec... v) {
    for (Vec vec : v) {
      for (Vec dir : Vec.adjacent) {
        if (passages.contains(vec.add(dir)))
          return vec.add(dir);
      }
    }
    return null;
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return toStringConsole();
  }

  public void toBmp() throws Exception {
    Img img = new Img();
    passages.forEach(img::add);
    img.writeBitmapAged();
  }

  public String toStringConsole() {
    Img img = new Img();
    passages.forEach(p -> img.add(p, UnicodeCharacters.toBox(p, passages)));
    letters.forEach(img::add);
    return img.toConsoleString(' ');
  }
}
