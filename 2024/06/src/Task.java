import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task {

  private final Set<Pos> map = new HashSet<>();
  private final Set<Pos> visitedPositions = new HashSet<>();
  private Pos guardPos = null;
  private Pos guardPosOriginal = null;
  private Pos guardDir = Pos.UP;

  private int maxY = -1;
  private int maxX = 0;

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    ArrayList<String> inputs = Util.str2List(input);
    maxX = inputs.size() - 1;
    for (int x = 0; x < inputs.size(); x++) {
      String val = inputs.get(x);
      if (val.equals("#"))
        map.add(new Pos(x, maxY));
      else if (val.equals("^"))
        guardPos = new Pos(x, maxY);
    }
  }

  public void afterParse() {
    guardPosOriginal = guardPos.copy();
// out(toStringConsole());

    List<CheckDir> checkDirs = new ArrayList<>(); // for part 2

    // out(toStringConsole());
    guardWalk(checkDirs);

    out("part 1", "visited", visitedPositions.size());

    // part 2
    Set<Pos> loopCountPositions = new HashSet<>();

    for (CheckDir checkDir : checkDirs) {
      Pos actTestObstaclePos = checkDir.startPos().copy();
      actTestObstaclePos.add(checkDir.direction());
      while (!(actTestObstaclePos.x < 0 || actTestObstaclePos.y < 0 || actTestObstaclePos.x > maxX || actTestObstaclePos.y > maxY || map.contains(actTestObstaclePos))) {
        // test new Obstacle
        reset();
        // map.add(new Pos(3, 6));
        map.add(actTestObstaclePos);
        boolean leftTheMap = guardWalk(null);
        if (!leftTheMap) {
          loopCountPositions.add(actTestObstaclePos.copy());
          // out("loop causing pos", actTestObstaclePos);
        }
        map.remove(actTestObstaclePos);
        actTestObstaclePos.add(checkDir.direction());
      }
    }

    out("part 2", "loop positions", loopCountPositions.size());
  }

  private void reset() {
    guardPos = guardPosOriginal.copy();
    guardDir = Pos.UP;
    visitedPositions.clear();
  }

  private boolean guardWalk(List<CheckDir> checkDirs) {

    visitedPositions.add(guardPos.copy());

    Set<String> obstacleHitWithDir = new HashSet<>();

    Pos nextPos = guardPos.addToNew(guardDir);
    while (!(nextPos.x < 0 || nextPos.y < 0 || nextPos.x > maxX || nextPos.y > maxY)) {
      if (map.contains(nextPos)) {
        String posDirId = nextPos + "-dir:" + guardDir.toString();
        if (!obstacleHitWithDir.add(posDirId))
          return false;
        guardDir = guardDir.rotate90DegToNew(false);

        if (checkDirs != null)
          checkDirs.add(new CheckDir(guardPos.copy(), guardDir.copy()));
      } else
        guardPos = nextPos;

      visitedPositions.add(guardPos.copy());

      nextPos = guardPos.addToNew(guardDir);

      // System.out.println("visited:" + visitedPositions.size());
      // System.out.println(toStringConsole());
    }
    // System.out.println("left the map");
    return true;
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    return builder.toString();
  }

  public String toStringSVG() {
    SVG svg = new SVG();

    return svg.toSVGStringAged();
  }


  public String toStringConsole() {
    SVG svg = new SVG();
    for (Pos pos : map) {
      svg.add(pos);
    }

    for (Pos visitedPosition : visitedPositions) {
      svg.add(visitedPosition, "X");
    }

    String guardD = "^";
    if (guardDir.equals(Pos.DOWN))
      guardD = "v";
    else if (guardDir.equals(Pos.LEFT))
      guardD = "<";
    else if (guardDir.equals(Pos.RIGHT))
      guardD = ">";
    svg.add(guardPos, guardD);
    return svg.toConsoleString();
  }
}
