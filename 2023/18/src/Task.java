import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
  Map<Pos, String> map = new HashMap<>();
  Pos actPos = new Pos(0, 0);

  public void init() {
    map.put(actPos, "#ffffff");
  }

  public void addLine(String input) {
    String[] split = input.split("\\s+");
    String direction = split[0];
    Pos dir = getDir(direction);
    int steps = Integer.parseInt(split[1]);
    String hexColor = Util.cleanFrom(split[2], "(", ")");
    for (int i = 0; i < steps; i++) {
      actPos = actPos.addToNew(dir);
      map.put(actPos, hexColor);
    }
  }

  private static Pos getDir(String direction) {
    return switch (direction) {
      case "U" -> Pos.UP;
      case "D" -> Pos.DOWN;
      case "L" -> Pos.LEFT;
      default -> Pos.RIGHT;
    };
  }

  public void afterParse() {
    out("Part 1");
    out("map size after loop", map.size());
    out(toStringConsole());

    digLoopInsideSimpleAproach();
  }

  private void digLoopInsideSimpleAproach() {
    // dig loop inside
    // find top left
    Pos topLeft = actPos;
    for (Pos pos : map.keySet()) {
      if (pos.y < topLeft.y || pos.y == topLeft.y && pos.x < topLeft.x)
        topLeft = pos;
    }

    List<Pos> worklist = new ArrayList<>();
    worklist.add(topLeft.addToNew(1, 1));
    while (!worklist.isEmpty()) {
      Pos pos = worklist.removeFirst();
      if (map.containsKey(pos)) continue;
      map.put(pos, "#ffffff");

      for (Pos adjacent : Pos.adjacent) {
        Pos nextWouldBe = pos.addToNew(adjacent);
        if (map.containsKey(nextWouldBe)) continue;
        worklist.add(nextWouldBe);
      }
    }

    out("map size after dig inside loop", map.size());
//    out(toStringConsole());
  }

  private void digLoopInside() {
    // dig loop inside
    int minX = 0;
    int maxX = 0;
    int minY = 0;
    int maxY = 0;
    for (Pos pos : map.keySet()) {
      if (pos.y < minY) minY = pos.y;
      if (pos.y > maxY) maxY = pos.y;
      if (pos.x < minX) minX = pos.x;
      if (pos.x > maxX) maxX = pos.x;
    }

    long countDigs = 0;
    boolean insideLoop = false;
    boolean inWall = false;

    for (int y = minY; y <= maxY; y++) {
      for (int x = minX; x <= maxX; x++) {
        Pos checkPos = new Pos(x, y);
        if (map.containsKey(checkPos)) {
          countDigs++;
          if (!inWall) {
            inWall = true;
            insideLoop = !insideLoop;
          }
        } else {
          inWall = false;
          if (insideLoop)
            countDigs++;
        }
      }
    }

    out("map size after dig inside loop", countDigs);
//    out(toStringConsole());
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
    return svg.toConsoleString();
  }
}
