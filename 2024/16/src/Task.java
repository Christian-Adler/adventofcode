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

  public void afterParse() throws Exception {
    // out(this);

    findShortestPath();
  }

  private void findShortestPath() throws Exception {

    long minPoints = -1;

    // https://de.wikipedia.org/wiki/A*-Algorithmus
    PriorityQueue<WorkItem> openList = new PriorityQueue<>(Comparator.comparingLong(WorkItem::score));
    Set<WorkItem> closedList = new HashSet<>();

    openList.add(new WorkItem(start, startDir, 0));

    List<WorkItem> targetReached = new ArrayList<>();
    long count = 0;
    while (!openList.isEmpty()) {
      count++;
      // if (count % 10000 == 0)
      // out(count, openList.size());
      WorkItem workItem = openList.poll();
      if (workItem == null)
        throw new IllegalStateException("null!");
      if (workItem.pos.equals(end)) {
        minPoints = workItem.soFarPoints;
        targetReached.add(workItem);

        out("Part 1", "min points", minPoints); //

        // break;
        // no break because of part 2 - find all shortest path
      }

      closedList.add(workItem);

      // expand Node
      // Alle moeglichen Schritte durchgehen
      List<Integer> nextStepsCouldBe = new ArrayList<>();
      // In aktueller Richtung weiter?
      nextStepsCouldBe.add(0);
      nextStepsCouldBe.add(-1);
      nextStepsCouldBe.add(+1);

      for (int next : nextStepsCouldBe) {
        Vec nextPosWouldBe = null;
        Vec nextDir = null;
        if (next == 0) {
          nextPosWouldBe = workItem.pos.addToNew(workItem.direction);
          nextDir = workItem.direction;
          if (walls.contains(nextPosWouldBe))
            continue;
        } else if (next == -1) {
          nextPosWouldBe = workItem.pos.copy();
          nextDir = workItem.direction.rotate90DegToNew(true);
          if (walls.contains(nextPosWouldBe.addToNew(nextDir)))
            continue;
        } else if (next == 1) {
          nextPosWouldBe = workItem.pos.copy();
          nextDir = workItem.direction.rotate90DegToNew(false);
          if (walls.contains(nextPosWouldBe.addToNew(nextDir)))
            continue;
        }

        long stepCosts = Math.abs(next) > 0 ? 1000 : 1;

        long tentativeG = workItem.soFarPoints + stepCosts;
        if (minPoints >= 0 && tentativeG > minPoints)
          continue;

        WorkItem nextWorkItemWouldBe = new WorkItem(nextPosWouldBe, nextDir, tentativeG);

        if (closedList.contains(nextWorkItemWouldBe)) {
          for (WorkItem closedItem : closedList) {
            if (closedItem.pos.equals(nextWorkItemWouldBe.pos) && closedItem.soFarPoints == nextWorkItemWouldBe.soFarPoints && next == 0) {
              closedItem.predecessors.add(workItem);
              // out("found new path");
            }
          }
          continue;
        }

        nextWorkItemWouldBe.predecessors.add(workItem);

        WorkItem alreadyInList = openList.stream().filter(w -> w.equals(nextWorkItemWouldBe)).findFirst().orElse(null);
        if (alreadyInList != null && tentativeG > alreadyInList.soFarPoints)
          continue;
        if (alreadyInList != null && tentativeG == alreadyInList.soFarPoints) {
          alreadyInList.predecessors.addAll(nextWorkItemWouldBe.predecessors);
          continue;
        }

        openList.remove(alreadyInList);
        openList.add(nextWorkItemWouldBe);
      }
    }

    Set<Vec> atLeastOne = new HashSet<>();

    Img img = new Img();
    for (Vec wall : walls) {
      img.add(wall, Color.GRAY);
    }

    for (WorkItem workItem : targetReached) {
      List<WorkItem> workList = new ArrayList<>();
      workList.add(workItem);
      while (!workList.isEmpty()) {
        WorkItem actWorkItem = workList.removeFirst();
        if (atLeastOne.add(actWorkItem.pos)) {
          img.add(actWorkItem.pos);
        }
        workList.addAll(actWorkItem.predecessors);
      }
    }
    Util.writeToFile(img.toSVGStringAged(), "./svg.svg");

    out("Part 2", "best path seats", atLeastOne.size());
  }

  private boolean checkForConnectedStart(WorkItem workItem) {
    if (workItem.pos.equals(start))
      return true;
    List<WorkItem> workList = new ArrayList<>(workItem.predecessors);
    while (!workList.isEmpty()) {
      WorkItem checkItem = workList.removeFirst();
      if (checkItem.pos.equals(start))
        return true;
      // workList.addAll(checkItem.predecessors);
      workList.add(checkItem.predecessors.getFirst());
    }
    return false;
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


  private class WorkItem {
    Vec pos;
    Vec direction;
    long soFarPoints;
    final List<WorkItem> predecessors = new ArrayList<>();

    public WorkItem(Vec pos, Vec direction, long soFarPoints) {
      this.pos = pos;
      this.direction = direction;
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
      return Objects.equals(pos, workItem.pos) && Objects.equals(direction, workItem.direction);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pos, direction);
    }

  }
}
