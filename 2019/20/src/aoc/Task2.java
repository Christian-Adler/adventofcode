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

public class Task2 {

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


    // out(this);

    // out(start, end);
    // out(key2portal);


    // build graph
    Map<PortalKey, Map<PortalKey, Long>> graph = new HashMap<>();

    for (Portal portal : key2portal.values()) {
      List<Vec> startVecs = new ArrayList<>();
      startVecs.add(portal.p1());
      if (portal.p2() != null)
        startVecs.add(portal.p2());


      for (Portal portal2 : key2portal.values()) {
        List<Vec> endVecs = new ArrayList<>();
        endVecs.add(portal2.p1());
        if (portal2.p2() != null)
          endVecs.add(portal2.p2());


        for (Vec startVec : startVecs) {
          for (Vec endVec : endVecs) {

            PortalKey portalKey = new PortalKey(portal.key(), isOuterPortal(startVec));
            Map<PortalKey, Long> targetMap = graph.computeIfAbsent(portalKey, k -> new HashMap<>());

            PortalKey portal2Key = new PortalKey(portal2.key(), isOuterPortal(endVec));
            Map<PortalKey, Long> target2Map = graph.computeIfAbsent(portal2Key, k -> new HashMap<>());

            if (portalKey.equals(portal2Key)) continue;
            if (targetMap.containsKey(portal2Key)) continue;

            Long shortestPathLen = findShortestPath(startVec, endVec);
            if (shortestPathLen != null) {

              targetMap.put(portal2Key, shortestPathLen + (isStartOrEnd(portal2Key) ? 0 : 1));// +1 = step through portal
              target2Map.put(portalKey, shortestPathLen + (isStartOrEnd(portalKey) ? 0 : 1));
            }
          }
        }
      }
    }

    PortalKey startKey = new PortalKey("AA", true);
    for (Map<PortalKey, Long> innerMap : graph.values()) {
      innerMap.remove(startKey);
    }

    // // connect inner and outer
    // for (String s : key2portal.keySet()) {
    //
    //   if (s.startsWith("AA") || s.startsWith("ZZ"))
    //     continue;
    //
    //   String key_o = s + "_o";
    //   String key_i = s + "_i";
    //
    //
    //   Map<String, PortalInfo> innerMap = graph.get(key_o);
    //   if (innerMap != null)
    //     innerMap.put(key_i, new PortalInfo(0, false));
    //   innerMap = graph.get(key_i);
    //   if (innerMap != null)
    //     innerMap.put(key_o, new PortalInfo(0, false));
    // }

    // out("graph", graph);
    // for (Map.Entry<PortalKey, Map<PortalKey, Long>> entry : graph.entrySet()) {
    //   out(entry);
    // }


    AtomicLong minPathLen = new AtomicLong(Long.MAX_VALUE);
    List<String> minPath = new ArrayList<>();

    findShortestPath2(minPathLen, minPath, graph);
    // out(minPath);
    out("part 2", minPathLen);
  }


  private boolean isStartOrEnd(PortalKey portalKey) {
    return portalKey.portalId().startsWith("AA") || portalKey.portalId().startsWith("ZZ");
  }

  private boolean isOuterPortal(Vec vec) {
    return vec.x == 2 || vec.y == 2 || vec.x == maxX - 2 || vec.y == maxY - 2;
  }

  private String depth(int depth) {
    return "(" + depth + ")";
  }

  private record WorkItem(PortalKey actNode, int depth, long soFarLen, List<String> soFarPath,
                          Set<String> visited) {
  }

  private record PortalKey(String portalId, boolean outer) {
    @Override
    public String toString() {
      return portalId + '_' + (outer ? "o" : "i");
    }

    public boolean isStart() {
      return portalId.equals("AA");
    }

    public boolean isEnd() {
      return portalId.equals("ZZ");
    }

    public PortalKey flipOuter() {
      return new PortalKey(portalId, !outer);
    }
  }

  private void findShortestPath2(AtomicLong minPathLen, List<String> minPath, Map<PortalKey, Map<PortalKey, Long>> graph) {

    PriorityQueue<WorkItem> workList = new PriorityQueue<>(Comparator.comparingInt(WorkItem::depth).thenComparing((o1, o2) -> Boolean.compare(o2.actNode.outer, o1.actNode.outer)));
    workList.add(new WorkItem(new PortalKey("AA", true), 0, 0, new ArrayList<>(), new HashSet<>()));

    while (!workList.isEmpty()) {
      WorkItem workItem = workList.poll();

      if (!workItem.visited.add(workItem.actNode + depth(workItem.depth))) continue;
      workItem.soFarPath.add(workItem.actNode + depth(workItem.depth));

      if (workItem.actNode.portalId().startsWith("ZZ")) {
        if (workItem.soFarLen < minPathLen.get()) {
          // out("found path");
          minPathLen.set(workItem.soFarLen);
          minPath.clear();
          minPath.addAll(workItem.soFarPath);
          // out(minPath);
        }
        continue;
      }

      if (workItem.soFarLen > minPathLen.get()) continue;

      // if (workItem.depth > 10) return;

      Map<PortalKey, Long> neighbours = graph.get(workItem.actNode);
      ArrayList<Map.Entry<PortalKey, Long>> neighboursSorted = new ArrayList<>(neighbours.entrySet());
      // sort neighbours: first try outer
      neighboursSorted.sort((o1, o2) -> Boolean.compare(o2.getKey().outer(), o1.getKey().outer()));
      for (Map.Entry<PortalKey, Long> entry : neighboursSorted) {
        PortalKey nextNode = entry.getKey();
        Long distance = entry.getValue();

        // in depth > 0 there are no AA / ZZ portals
        if (workItem.depth > 0 && (nextNode.isStart() || nextNode.isEnd()))
          continue;

        // in depth = 0 there are only AA / ZZ portals
        if (workItem.depth <= 0 && nextNode.outer() && !nextNode.isEnd())
          continue;

        int nextDepth = workItem.depth;
        nextDepth += (nextNode.outer() ? -1 : 1);

        workList.add(new WorkItem(nextNode.flipOuter(), nextDepth, workItem.soFarLen + distance, new ArrayList<>(workItem.soFarPath), new HashSet<>(workItem.visited)));
      }
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
    img.writeBitmapAged();
  }

  public String toStringConsole() {
    Img img = new Img();
    passages.forEach(p -> img.add(p, UnicodeCharacters.FULL_BLOCK));
    letters.forEach(img::add);
    return img.toConsoleString(' ');
  }
}
