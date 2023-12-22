import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Task2 {
  private final Set<Pos> gardenPlots = new HashSet<>();
  private Pos start = null;

  private int maxX = 0;
  private int maxY = -1;

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    maxX = input.length() - 1;
    ArrayList<String> inputAsList = Util.str2List(input);
    for (int x = 0; x < inputAsList.size(); x++) {
      String s = inputAsList.get(x);
      if (s.equals("S")) {
        start = new Pos(x, maxY);
      }
      if (!s.equals("#")) {
        gardenPlots.add(new Pos(x, maxY));
      }
    }
  }

  public void afterParse(int maxSteps) {

    boolean maxStepsEven = (maxSteps % 2 == 0);

    int actStep = 0;
    long reachedGardenPlots = 0;
    if (maxStepsEven)
      reachedGardenPlots++;

    Set<Pos> reachedAfterStep = new HashSet<>();
    Set<Pos> prevReachedSteps = new HashSet<>();
    Set<Pos> prevPrevReachedSteps = new HashSet<>();

    reachedAfterStep.add(start);

    while (actStep < maxSteps) {
      actStep++;

      if (actStep % 100 == 0) out("actStep", actStep, reachedAfterStep.size());

      Set<Pos> workSet = new HashSet<>(reachedAfterStep);

      prevPrevReachedSteps.clear();
      prevPrevReachedSteps.addAll(prevReachedSteps);
      prevReachedSteps.clear();
      prevReachedSteps.addAll(reachedAfterStep);

      reachedAfterStep.clear();

      for (Pos pos : workSet) {
        for (Pos adj : Pos.adjacent) {
          Pos adjacent = pos.addToNew(adj);
          if (prevPrevReachedSteps.contains(adjacent)) // don't go back
            continue;
          if (reachedAfterStep.contains(adjacent))
            continue;

          if (isGardenPlot(adjacent))
            reachedAfterStep.add(adjacent);
        }
      }

      if (actStep % 2 == (maxStepsEven ? 0 : 1)) {
        reachedGardenPlots += reachedAfterStep.size();
      }
    }
    // lasts way to long...
    // better solution see https://github.com/keriati/aoc/blob/master/2023/day21.ts

    out("reachable garden plots with", maxSteps, "steps: ", reachedGardenPlots);

  }

  private boolean isGardenPlot(Pos pos) {
    long checkX = pos.x;
    checkX = checkX % (maxX + 1);
    if (checkX < 0)
      checkX += (maxX + 1); // maxX ist der max index!)
    long checkY = pos.y;
    checkY = checkY % (maxY + 1);
    if (checkY < 0) checkY += (maxY + 1);
    return gardenPlots.contains(new Pos(checkX, checkY));
  }


  public void out(Object... str) {
    Util.out(str);
  }
}
