package aoc;

import aoc.astar.AStar;
import aoc.astar.AStarItem;
import aoc.astar.AStarNextItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Task {
  private final List<Vec> obstacles = new ArrayList<>();
  private final Vec start = new Vec(0, 0);
  private Vec end = new Vec(0, 0);
  private int fallingBytes = 0;

  public void init(int fallingBytes, int width, int height) throws Exception {
    end = new Vec(width, height);
    this.fallingBytes = fallingBytes;
  }

  public void addLine(String input) {
    obstacles.add(new Vec(input));
  }

  public void afterParse() throws Exception {
    // out(toStringConsole());

    // part 1
    AStarItem<Vec> targetReached = findShortestPath(fallingBytes);

    out("Part 1", "min points", targetReached.score); //

    Img img = new Img();
    AStarItem<Vec> actWorkItem = targetReached;
    while (actWorkItem != null) {
      img.add(actWorkItem.item);
      actWorkItem = actWorkItem.getPredecessor();
    }
    Util.writeToFile(img.toSVGStringAged(), "./svg_1.svg");

    // part 2
    int searchBetweenMin = fallingBytes;
    int searchBetweenMax = obstacles.size();

    while (true) {
      if (searchBetweenMin == searchBetweenMax - 1) {
        // out("found path", searchBetweenMin, obstacles.get(searchBetweenMin));
        out("part 2:", Util.cleanFrom(obstacles.get(searchBetweenMin).toString(), "(", ")"));

        break;
      }
      int avgSearchBetween = searchBetweenMin + (searchBetweenMax - searchBetweenMin) / 2;
      // out("search path for", avgSearchBetween);

      AStarItem<Vec> targetReached2 = findShortestPath(avgSearchBetween);
      if (targetReached2 == null)
        searchBetweenMax = avgSearchBetween;
      else {
        searchBetweenMin = avgSearchBetween;
        targetReached = targetReached2;
      }
      // out(searchBetweenMin, searchBetweenMax);
    }

    img = new Img();
    actWorkItem = targetReached;
    while (actWorkItem != null) {
      img.add(actWorkItem.item);
      actWorkItem = actWorkItem.getPredecessor();
    }

    for (Vec vec : obstacles.subList(0, searchBetweenMin + 1)) {
      img.add(vec, Color.GRAY);
    }

    Util.writeToFile(img.toSVGStringAged(), "./svg_2.svg");
  }

  private AStarItem<Vec> findShortestPath(int fallenBytes) throws Exception {

    HashSet<Vec> workObstacles = new HashSet<>(obstacles.subList(0, fallenBytes));

    AStar<Vec> aStar = new AStar<>((vec, end) -> (long) vec.manhattanDistance(end), vec -> {
      List<AStarNextItem<Vec>> nextItems = new ArrayList<>();

      for (Vec dir : Vec.adjacent) {
        Vec nextPosWouldBe = vec.addToNew(dir);
        if (workObstacles.contains(nextPosWouldBe))
          continue;
        // borders?
        if (!nextPosWouldBe.isInRect(0, 0, end.x, end.y))
          continue;
        nextItems.add(new AStarNextItem<>(nextPosWouldBe, 1));
      }

      return nextItems;
    });

    return aStar.findShortestPath(start, end);
  }

  public void out(Object... str) {
    Util.out(str);
  }


  public void toBmp(int width, int height) throws Exception {
    Img img = new Img();
    img.writeBitmapAged();
  }

  public String toStringConsole(int width, int height) {
    Img img = new Img();
    img.add(start, "S");
    img.add(end, "E");

    for (Vec obstacle : obstacles) {
      img.add(obstacle);
    }
    return img.toConsoleString();
  }

  private class WorkItem {
    Vec pos;
    long soFarPoints;
    WorkItem predecessor = null;

    public WorkItem(Vec pos, long soFarPoints) {
      this.pos = pos;
      this.soFarPoints = soFarPoints;
    }

    public long score() {
      // f(x) = g(x)      + h(x)
      return soFarPoints + pos.manhattanDistance(end);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      WorkItem workItem = (WorkItem) o;
      return Objects.equals(pos, workItem.pos);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pos);
    }

  }
}
