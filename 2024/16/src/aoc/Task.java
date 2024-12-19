package aoc;

import aoc.astar.AStar;
import aoc.astar.AStarItem;
import aoc.astar.AStarNextItem;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Task {
  private final Set<Vec> walls = new HashSet<>();
  private Vec start;
  private Vec end;
  private final Vec startDir = Vec.RIGHT;

  private static final int POINTS_ROTATE = 1000;
  private static final int POINTS_STEP = 1;

  private int maxY = -1;

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    ArrayList<String> in = Util.str2List(input);
    for (int x = 0; x < in.size(); x++) {
      String i = in.get(x);
      switch (i) {
        case "#" -> walls.add(new Vec(x, maxY));
        case "S" -> start = new Vec(x, maxY);
        case "E" -> end = new Vec(x, maxY);
      }
    }
  }

  private record PosDir(Vec pos, Vec dir) {
    @Override
    public String toString() {
      return pos + " - " + dir;
    }
  }

  public void afterParse() throws Exception {
    List<Integer> nextStepsCouldBe = new ArrayList<>(Arrays.asList(0, -1, 1));
    AStar<PosDir> aStar = new AStar<>((pd, pdEnd) -> (long) pd.pos.manhattanDistance(pdEnd.pos), pd -> {
      List<AStarNextItem<PosDir>> nextItems = new LinkedList<>();

      // Alle moeglichen Schritte durchgehen
      // In aktueller Richtung weiter? Links | Rechts
      for (int next : nextStepsCouldBe) {
        Vec nextPosWouldBe = null;
        Vec nextDir = null;
        if (next == 0) {
          nextPosWouldBe = pd.pos.addToNew(pd.dir);
          nextDir = pd.dir;
          if (walls.contains(nextPosWouldBe))
            continue;
        } else if (next == -1) {
          nextPosWouldBe = pd.pos.copy();
          nextDir = pd.dir.rotate90DegToNew(true);
          if (walls.contains(nextPosWouldBe.addToNew(nextDir)))
            continue;
        } else  /*if (next == 1)*/ {
          nextPosWouldBe = pd.pos.copy();
          nextDir = pd.dir.rotate90DegToNew(false);
          if (walls.contains(nextPosWouldBe.addToNew(nextDir)))
            continue;
        }

        long stepCosts = next == 0 ? 1 : 1000;

        nextItems.add(new AStarNextItem<>(new PosDir(nextPosWouldBe, nextDir), stepCosts));
      }

      return nextItems;
    });


    AStarItem<PosDir> targetReached = aStar.findShortestPaths(new PosDir(start, startDir), new PosDir(end, startDir));

    out("Part 1", "min points", targetReached.score);

    Set<Vec> atLeastOne = new HashSet<>();

    Img img = new Img();
    for (Vec wall : walls) {
      img.add(wall, Color.GRAY);
    }

    List<AStarItem<PosDir>> workList = new ArrayList<>();
    workList.add(targetReached);
    while (!workList.isEmpty()) {
      AStarItem<PosDir> actWorkItem = workList.removeFirst();
      if (atLeastOne.add(actWorkItem.item.pos)) {
        img.add(actWorkItem.item.pos);
      }
      workList.addAll(actWorkItem.getPredecessors());
    }

    Util.writeToFile(img.toSVGStringAged(), "./svg.svg");

    out("Part 2", "best path seats", atLeastOne.size());
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
    for (Vec wall : walls) {
      img.add(wall);
    }
    img.add(start, "S");
    img.add(end, "E");
    return img.toConsoleString();
  }
}
