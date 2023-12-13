import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Task {
  private int maxY = -1;
  private int maxX = -1;
  private final Set<Pos> map = new HashSet<>();

  private long sum = 0;
  private long sum2 = 0;

  public void init() {
  }

  public void addLine(String input) {
    if (input.trim().isEmpty()) { // eval pattern > reset
      runCheckForPattern();
      return;
    }

    maxX = input.length() - 1;
    maxY++;
    ArrayList<String> inputAsList = Util.str2List(input);
    for (int x = 0; x < inputAsList.size(); x++) {
      if (inputAsList.get(x).equals("#"))
        map.add(new Pos(x, maxY));
    }
  }

  private void runCheckForPattern() {
    out("");
    SVG svg = new SVG();
    map.forEach(svg::add);
    out(svg.toConsoleString());

    checkVertical();
    checkHorizontal();

    // reset for next pattern
    maxY = -1;
    maxX = -1;
    map.clear();
  }

  private void checkVertical() {
    int horizontalMirror = -1;
    int maxMirrorRange = -1;
    int horizontalMirror2 = -1;
    int maxMirrorRange2 = -1;

    for (int mirrorY = 1; mirrorY <= maxY; mirrorY++) {
      int mirrorRange = Math.min(mirrorY, maxY - mirrorY + 1);
//      out("mirrorRange", mirrorRange);

      Set<Pos> checkSetAboveMirror = new HashSet<>();
      Set<Pos> checkSetBelowMirror = new HashSet<>();

      for (int y = mirrorY - mirrorRange; y < mirrorY; y++) {
        for (int x = 0; x <= maxX; x++) {
          Pos checkPos = new Pos(x, y);
          if (map.contains(checkPos))
            checkSetAboveMirror.add(checkPos);
        }
      }

      for (int y = mirrorY; y < mirrorY + mirrorRange; y++) {
        for (int x = 0; x <= maxX; x++) {
          Pos checkPos = new Pos(x, y);
          if (map.contains(checkPos))
            checkSetBelowMirror.add(checkPos);
        }
      }

      Set<Pos> mirrorSetAbove = new HashSet<>();
      for (Pos pos : checkSetAboveMirror) {
        int mirrorDistance = mirrorY - pos.y - 1;
        Pos mirrorPos = new Pos(pos.x, mirrorY + mirrorDistance);
        mirrorSetAbove.add(mirrorPos);
      }
      Set<Pos> mirrorSetBelow = new HashSet<>();
      for (Pos pos : checkSetBelowMirror) {
        int mirrorDistance = pos.y - mirrorY;
        Pos mirrorPos = new Pos(pos.x, mirrorY - mirrorDistance - 1);
        mirrorSetBelow.add(mirrorPos);
      }

      if (checkSetAboveMirror.containsAll(mirrorSetBelow) && checkSetBelowMirror.containsAll(mirrorSetAbove)) {
        if (mirrorRange > maxMirrorRange) {
          maxMirrorRange = mirrorRange;
          horizontalMirror = mirrorY;
        }
      }
      // Part 2 - only one difference?
      Set<Pos> part2Set = new HashSet<>();
      checkSetAboveMirror.removeAll(mirrorSetBelow);
      checkSetBelowMirror.removeAll(mirrorSetAbove);
      part2Set.addAll(checkSetAboveMirror);
      part2Set.addAll(checkSetBelowMirror);
      if (part2Set.size() == 1) {
        if (mirrorRange > maxMirrorRange2) {
          maxMirrorRange2 = mirrorRange;
          horizontalMirror2 = mirrorY;
        }
      }
    }

    out("horizontalMirror", horizontalMirror);
    if (horizontalMirror > 0)
      sum += horizontalMirror * 100L;
    out("horizontalMirror2", horizontalMirror2);
    if (horizontalMirror2 > 0)
      sum2 += horizontalMirror2 * 100L;
  }

  private void checkHorizontal() {
    int verticalMirror = -1;
    int maxMirrorRange = -1;
    int verticalMirror2 = -1;
    int maxMirrorRange2 = -1;

    for (int mirrorX = 1; mirrorX <= maxX; mirrorX++) {
      int mirrorRange = Math.min(mirrorX, maxX - mirrorX + 1);
//      out("mirrorRange", mirrorRange);

      Set<Pos> checkSetLeftMirror = new HashSet<>();
      Set<Pos> checkSetRightMirror = new HashSet<>();

      for (int x = mirrorX - mirrorRange; x < mirrorX; x++) {
        for (int y = 0; y <= maxY; y++) {
          Pos checkPos = new Pos(x, y);
          if (map.contains(checkPos))
            checkSetLeftMirror.add(checkPos);
        }
      }

      for (int x = mirrorX; x < mirrorX + mirrorRange; x++) {
        for (int y = 0; y <= maxY; y++) {
          Pos checkPos = new Pos(x, y);
          if (map.contains(checkPos))
            checkSetRightMirror.add(checkPos);
        }
      }

      Set<Pos> mirrorSetLeft = new HashSet<>();
      for (Pos pos : checkSetLeftMirror) {
        int mirrorDistance = mirrorX - pos.x - 1;
        Pos mirrorPos = new Pos(mirrorX + mirrorDistance, pos.y);
        mirrorSetLeft.add(mirrorPos);
      }
      Set<Pos> mirrorSetRight = new HashSet<>();
      for (Pos pos : checkSetRightMirror) {
        int mirrorDistance = pos.x - mirrorX;
        Pos mirrorPos = new Pos(mirrorX - mirrorDistance - 1, pos.y);
        mirrorSetRight.add(mirrorPos);
      }

      if (checkSetLeftMirror.containsAll(mirrorSetRight) && checkSetRightMirror.containsAll(mirrorSetLeft)) {
        if (mirrorRange > maxMirrorRange) {
          maxMirrorRange = mirrorRange;
          verticalMirror = mirrorX;
        }
      }
      // Part 2 - only one difference?
      Set<Pos> part2Set = new HashSet<>();
      checkSetLeftMirror.removeAll(mirrorSetRight);
      checkSetRightMirror.removeAll(mirrorSetLeft);
      part2Set.addAll(checkSetLeftMirror);
      part2Set.addAll(checkSetRightMirror);
      if (part2Set.size() == 1) {
        if (mirrorRange > maxMirrorRange2) {
          maxMirrorRange2 = mirrorRange;
          verticalMirror2 = mirrorX;
        }
      }
    }

    out("verticalMirror", verticalMirror);
    if (verticalMirror > 0)
      sum += verticalMirror;
    out("verticalMirror2", verticalMirror2);
    if (verticalMirror2 > 0)
      sum2 += verticalMirror2;
  }

  public void afterParse() {
    runCheckForPattern(); // last pattern

    out("Part 1:", sum);
    out("Part 2:", sum2); // < 72300
  }

  public void out(Object... str) {
    Util.out(str);
  }
}