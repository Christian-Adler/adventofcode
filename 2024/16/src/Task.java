import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Task {
  private Set<Vec> walls = new HashSet<>();
  private Vec start;
  private Vec end;
  private Vec startDir = Vec.RIGHT;

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
      if (i.equals("#"))
        walls.add(new Vec(x, maxY));
      else if (i.equals("S"))
        start = new Vec(x, maxY);
      else if (i.equals("E"))
        end = new Vec(x, maxY);
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

    openList.add(new WorkItem(start, Vec.RIGHT, 0));

    WorkItem targetReached = null;
    long count = 0;
    while (!openList.isEmpty()) {
      count++;
      // if (count % 10000 == 0)
      //   out(count, openList.size(), minPoints);
      WorkItem workItem = openList.poll();
      if (workItem == null)
        throw new IllegalStateException("null!");
      if (workItem.pos.equals(end)) {
        minPoints = workItem.soFarPoints;
        targetReached = workItem;
        break;
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
        }
        else if (next == -1) {
          nextPosWouldBe = workItem.pos.copy();
          nextDir = workItem.direction.rotate90DegToNew(true);
          if (walls.contains(nextPosWouldBe.addToNew(nextDir)))
            continue;
        }
        else if (next == 1) {
          nextPosWouldBe = workItem.pos.copy();
          nextDir = workItem.direction.rotate90DegToNew(false);
          if (walls.contains(nextPosWouldBe.addToNew(nextDir)))
            continue;
        }

        long stepCosts = Math.abs(next) > 0 ? 1000 : 1;

        long tentativeG = workItem.soFarPoints + stepCosts;

        WorkItem nextWorkItemWouldBe = new WorkItem(nextPosWouldBe, nextDir, tentativeG);
        nextWorkItemWouldBe.predecessor = workItem;
        if (closedList.contains(nextWorkItemWouldBe))
          continue;

        WorkItem alreadyInList = openList.stream().filter(w -> w.equals(nextWorkItemWouldBe)).findFirst().orElse(null);
        if (alreadyInList != null && tentativeG >= alreadyInList.soFarPoints)
          continue;

        openList.remove(alreadyInList);
        openList.add(nextWorkItemWouldBe);
      }
    }

    out("Part 1", "min points", minPoints); //

    Img img = new Img();
    WorkItem actWorkItem = targetReached;
    while (actWorkItem != null) {
      img.add(actWorkItem.pos);
      actWorkItem = actWorkItem.predecessor;
    }
    Util.writeToFile(img.toSVGStringAged(), "./svg.svg");
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
    WorkItem predecessor = null;

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
      return Objects.hash(pos, direction, soFarPoints);
    }

  }
}
