package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;
import aoc.util.astar.AStar;
import aoc.util.astar.AStarItem;
import aoc.util.astar.AStarNextItem;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Same as Task but with 4 starts - iterating over them
 */
public class Task2 extends Task {
  private final Set<Vec> map = new HashSet<>();
  private final Map<String, Vec> keys = new HashMap<>();
  private final Map<String, Vec> doors = new HashMap<>();
  private final Map<Vec, String> doorVec2s = new HashMap<>();
  private int maxY = -1;
  private int maxX = -1;
  private final Map<String, Vec> start = new HashMap<>();


  public void addLine(String input) {
    maxX = input.length() - 1;
    maxY++;
    int x = -1;
    for (String s : Util.str2List(input)) {
      x++;
      if (s.equals("#")) continue;
      map.add(new Vec(x, maxY));
      if (!s.equals(".")) {
        if (s.equals("@"))
          start.put(String.valueOf(start.size() + 1), new Vec(x, maxY));
        else if (Character.isUpperCase(s.codePointAt(0))) {
          doors.put(s, new Vec(x, maxY));
          doorVec2s.put(new Vec(x, maxY), s);
        } else
          keys.put(s, new Vec(x, maxY));
      }
    }
  }


  public void afterParse() {
    // out(this);

    // build map/graph from each key to each other
    Map<String, Map<String, StepsAndDoors>> graph = new HashMap<>();
    // start 2 keys
    for (String keyStart : start.keySet()) {
      fillGraphForKey(keyStart, graph);
    }

    // out(graph);
    for (String keyStart : keys.keySet()) {
      fillGraphForKey(keyStart, graph);
    }

    // out(graph);


    Set<String> knownKeys = new HashSet<>();
    Set<String> toFindKeys = new HashSet<>(keys.keySet());
    List<String> actKey = new ArrayList<>(start.keySet()); // start


    Map<String, Long> cache = new HashMap<>();

    AtomicLong minSteps = new AtomicLong(Long.MAX_VALUE);
    findMinSteps(actKey, 0, toFindKeys, knownKeys, graph, cache, minSteps);
    out("part 2", minSteps);
  }

  private void fillGraphForKey(String keyStart, Map<String, Map<String, StepsAndDoors>> graph) {
    Vec keyStartPos = start.get(keyStart);
    if (keyStartPos == null)
      keyStartPos = keys.get(keyStart);

    for (String key : keys.keySet()) {
      if (keyStart.equals(key)) continue;
      Vec keyPos = keys.get(key);
      AStar<Vec> aStar = new AStar<>((vec, vec2) -> (long) vec.manhattanDistance(vec2), vec -> {
        List<AStarNextItem<Vec>> nextItems = new ArrayList<>();
        for (Vec dir : Vec.adjacent) {
          Vec nextPos = vec.add(dir);
          if (map.contains(nextPos))
            nextItems.add(new AStarNextItem<>(nextPos, 1));
        }
        return nextItems;
      });
      AStarItem<Vec> aStarItem = aStar.findShortestPath(keyStartPos, keyPos);
      if (aStarItem != null) {
        Set<String> doorsOnPath = new HashSet<>();
        long steps = aStarItem.score;
        while (aStarItem != null) {
          aStarItem = aStarItem.getPredecessor();
          if (aStarItem != null) {
            String doorOnPath = doorVec2s.get(aStarItem.item);
            if (doorOnPath != null)
              doorsOnPath.add(doorOnPath.toLowerCase());
          }
        }
        Map<String, StepsAndDoors> innerMap = graph.computeIfAbsent(keyStart, k -> new HashMap<>());
        innerMap.put(key, new StepsAndDoors(steps, doorsOnPath));
      }
      // else
      //   out("found no path from " + keyStart + " to key " + key);
    }
  }

  private void findMinSteps(List<String> actKeys, long soFarSteps, Set<String> toFindKeys, Set<String> knownKeys, Map<String, Map<String, StepsAndDoors>> graph, Map<String, Long> cache, AtomicLong minSteps) {
    if (soFarSteps > minSteps.get())
      return;
    // recursion finished?
    if (toFindKeys.isEmpty()) {
      if (soFarSteps < minSteps.get()) {
        minSteps.set(soFarSteps);
        // out("found solution soFarSteps", soFarSteps);
      }
      return;
    }

    String cacheKey = String.join(",", actKeys) + "_" + soFarSteps + "_" + knownKeys.stream().sorted().collect(Collectors.joining(","));
    Long cached = cache.get(cacheKey);
    if (cached == null || cached > soFarSteps)
      cache.put(cacheKey, soFarSteps);
    else {
      // out("cache treffer", cacheKey);
      return;
    }

    for (int i = 0; i < actKeys.size(); i++) {
      String actKey = actKeys.get(i);

      Map<String, StepsAndDoors> actKeyMap = graph.get(actKey);
      if (actKeyMap == null) // end of keys in this fault
        continue;

      for (String toFindKey : toFindKeys) {
        StepsAndDoors stepsAndDoors = actKeyMap.get(toFindKey);
        if (stepsAndDoors == null) // not reachable in this fault
          continue;

        // are there still doors for which the keys are not found
        Set<String> doorsOnPath = new HashSet<>(stepsAndDoors.doorsLowerCase());
        doorsOnPath.removeAll(knownKeys);
        if (!doorsOnPath.isEmpty())
          continue;

        long steps = stepsAndDoors.steps();

        List<String> nextActKeys = new ArrayList<>(actKeys);
        nextActKeys.set(i, toFindKey);

        Set<String> nextKnownKeys = new HashSet<>(knownKeys);
        nextKnownKeys.add(toFindKey);
        Set<String> nextToFindKeys = new HashSet<>(toFindKeys);
        nextToFindKeys.remove(toFindKey);

        findMinSteps(nextActKeys, soFarSteps + steps, nextToFindKeys, nextKnownKeys, graph, cache, minSteps);
      }
    }
  }


  public String toStringConsole() {
    Img img = new Img();

    img.add(new Vec(0, 0), " ");
    img.add(new Vec(maxX, maxY), " ");

    map.forEach(v -> img.add(v, "."));
    keys.forEach((s, v) -> img.add(v, s));
    doors.forEach((s, v) -> img.add(v, s));
    for (Map.Entry<String, Vec> entry : start.entrySet()) {
      img.add(entry.getValue(), entry.getKey());
    }

    return img.toConsoleString(' ');
  }
}
