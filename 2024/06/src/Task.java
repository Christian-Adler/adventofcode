import java.util.*;

public class Task {

  private Map<Pos, String> map = new HashMap<>();
  private Set<Pos> visitedPositions = new HashSet<>();
  private Pos guardPos = null;
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
        map.put(new Pos(x, maxY), val);
      else if (val.equals("^"))
        guardPos = new Pos(x, maxY);
    }
  }

  public void afterParse() {
    System.out.println(toStringConsole());

    guardWalk();
  }

  private void guardWalk() {
    System.out.println("visited:" + visitedPositions.size());
    System.out.println(toStringConsole());
    visitedPositions.add(guardPos.copy());

    Pos nextPos = guardPos.addToNew(guardDir);
    while (!(nextPos.x < 0 || nextPos.y < 0 || nextPos.x > maxX || nextPos.y > maxY)) {
      if (map.containsKey(nextPos)) {
        guardDir = guardDir.rotate90DegToNew(false);
      } else
        guardPos = nextPos;

      visitedPositions.add(guardPos.copy());

      nextPos = guardPos.addToNew(guardDir);

      // System.out.println("visited:" + visitedPositions.size());
      // System.out.println(toStringConsole());
    }
    System.out.println("left the map");
    System.out.println("visited:" + visitedPositions.size());
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
    for (Pos pos : map.keySet()) {
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
