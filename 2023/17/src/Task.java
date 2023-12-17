import java.io.IOException;
import java.util.*;

public class Task {
  private static final int MAX_STEPS_PER_DIR = 3;
  private final Map<Pos, Integer> map = new HashMap<>();
  int maxY = -1;
  int maxX = -1;
  Pos end;

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    maxX = input.length() - 1;
    List<Integer> asList = Util.str2List(input).stream().mapToInt(Integer::parseInt).boxed().toList();
    for (int x = 0; x < asList.size(); x++) {
      map.put(new Pos(x, maxY), asList.get(x));
    }
  }

  public void afterParse() throws IOException {
    out(toStringConsole());
    Pos start = new Pos(0, 0);
    end = new Pos(maxX, maxY);

    long minHeatLos = -1;

    // https://de.wikipedia.org/wiki/A*-Algorithmus
    PriorityQueue<WorkItem> openList = new PriorityQueue<>(Comparator.comparingLong(WorkItem::score));
    Set<WorkItem> closedList = new HashSet<>();

    openList.add(new WorkItem(start, Pos.RIGHT, 0, 0));
    openList.add(new WorkItem(start, Pos.DOWN, 0, 0));

    WorkItem targetReached = null;

    while (!openList.isEmpty()) {
      WorkItem workItem = openList.poll();

      if (workItem.pos.equals(end)) {
        minHeatLos = workItem.soFarHeatLos;
        targetReached = workItem;
        break;
      }

      closedList.add(workItem);

      // expand Node
      // Alle moeglichen Schritte durchgehen
      List<Pos> nextStepsCouldBe = new ArrayList<>();
      // In aktueller Richtung weiter?
      if (workItem.soFarStepsInDirection < MAX_STEPS_PER_DIR)
        nextStepsCouldBe.add(workItem.direction);
      nextStepsCouldBe.add(workItem.direction.rotate90DegToNew(true));
      nextStepsCouldBe.add(workItem.direction.rotate90DegToNew(false));

      for (Pos nextDir : nextStepsCouldBe) {
        Pos nextPosWouldBe = workItem.pos.addToNew(nextDir);

        if (nextPosWouldBe.x < 0 || nextPosWouldBe.y < 0 || nextPosWouldBe.x > maxX || nextPosWouldBe.y > maxY)
          continue;

        long stepCosts = map.get(nextPosWouldBe);

        long tentativeG = workItem.soFarHeatLos + stepCosts;

        int stepsInThisDirection = 1;
        if (nextDir.equals(workItem.direction))
          stepsInThisDirection = workItem.soFarStepsInDirection + 1;

        WorkItem nextWorkItemWouldBe = new WorkItem(nextPosWouldBe, nextDir, stepsInThisDirection, tentativeG);
        nextWorkItemWouldBe.predecessor = workItem;
        if (closedList.contains(nextWorkItemWouldBe))
          continue;

        WorkItem alreadyInList = openList.stream().filter(w -> w.equals(nextWorkItemWouldBe)).findFirst().orElse(null);
        if (alreadyInList != null && tentativeG >= alreadyInList.soFarHeatLos)
          continue;

        openList.remove(alreadyInList);
        openList.add(nextWorkItemWouldBe);
      }
    }

    out("Part 1", "min heat los", minHeatLos); //

    SVG svg = new SVG();
    WorkItem actWorkItem = targetReached;
    while (actWorkItem != null) {
      svg.add(actWorkItem.pos);
      actWorkItem = actWorkItem.predecessor;
    }
    Util.writeToAOCSvg(svg.toSVGStringAged());
  }

  public void out(Object... str) {
    Util.out(str);
  }


  public String toStringConsole() {
    SVG svg = new SVG();
    for (Map.Entry<Pos, Integer> entry : map.entrySet()) {
      svg.add(entry.getKey(), String.valueOf(entry.getValue()));
    }
    return svg.toConsoleString();
  }


  private class WorkItem {
    Pos pos;
    Pos direction;
    int soFarStepsInDirection;
    long soFarHeatLos;
    WorkItem predecessor = null;

    public WorkItem(Pos pos, Pos direction, int soFarStepsInDirection, long soFarHeatLos) {
      this.pos = pos;
      this.direction = direction;
      this.soFarStepsInDirection = soFarStepsInDirection;
      this.soFarHeatLos = soFarHeatLos;
    }

    public long score() {
      // f(x) = g(x)      + h(x)
      return soFarHeatLos + pos.manhattanDistance(end);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      WorkItem workItem = (WorkItem) o;
      return soFarStepsInDirection == workItem.soFarStepsInDirection && Objects.equals(pos, workItem.pos) && Objects.equals(direction, workItem.direction);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pos, direction, soFarStepsInDirection);
    }

    @Override
    public String toString() {
      return "WorkItem{" +
          "pos=" + pos +
          ", direction=" + direction +
          ", soFarStepsInDirection=" + soFarStepsInDirection +
          ", soFarHeatLos=" + soFarHeatLos +
          ", score=" + score() +
          '}';
    }
  }
}
