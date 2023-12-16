import java.util.*;

public class Task {
  private final Map<Pos, String> map = new HashMap<>();
  private int maxY = -1;
  private int maxX = -1;

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    maxX = input.length() - 1;

    ArrayList<String> inputAsList = Util.str2List(input);
    for (int x = 0; x < inputAsList.size(); x++) {
      String s = inputAsList.get(x);
      if (!s.equals("."))
        map.put(new Pos(x, maxY), s);
    }
  }

  public void afterParse() {
    out(toStringConsole());

    PosDir pd = new PosDir(new Pos(-1, 0), Pos.RIGHT.copy());

    int energized = getEnergizedFromStart(pd);
    out("Part 1", "energized", energized);

    // Part 2 find max energized
    int maxEnergized = 0;
    List<PosDir> toCheck = new ArrayList<>();

    for (int x = 0; x <= maxX; x++) {
      toCheck.add(new PosDir(new Pos(x, -1), Pos.DOWN.copy()));
      toCheck.add(new PosDir(new Pos(x, maxY + 1), Pos.UP.copy()));
    }
    for (int y = 0; y <= maxY; y++) {
      toCheck.add(new PosDir(new Pos(-1, y), Pos.RIGHT.copy()));
      toCheck.add(new PosDir(new Pos(maxX + 1, y), Pos.LEFT.copy()));
    }

    for (PosDir posDir : toCheck) {
      energized = getEnergizedFromStart(posDir);
      if (energized > maxEnergized) maxEnergized = energized;
    }

    out("Part 2", "max energized", maxEnergized);
  }

  private int getEnergizedFromStart(PosDir pd) {
    Set<Pos> beamPositions = new HashSet<>();

    Set<PosDir> alreadyVisited = new HashSet<>();

    List<PosDir> worklist = new LinkedList<>();
    addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);

    while (!worklist.isEmpty()) {
      //      out();
      //      SVG svg = new SVG();
      //      beamPositions.forEach(svg::add);
      //      out(svg.toConsoleString());

      PosDir posDir = worklist.removeFirst();

      Pos nextStep = posDir.nextStep();
      Pos dir = posDir.dir;

      // Out of map?
      if (nextStep.x < 0 || nextStep.x > maxX || nextStep.y < 0 || nextStep.y > maxY)
        continue;

      beamPositions.add(nextStep);

      String mapItem = map.get(nextStep);

      // empty space?
      if (mapItem == null || mapItem.equals(".")) {
        pd = new PosDir(nextStep, dir);
        addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
        continue;
      }

      // splitter -
      if (mapItem.equals("-")) {
        if (dir.y == 0) { // pointy end of splitter
          pd = new PosDir(nextStep, dir);
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
        } else { // flat side of splitter
          Pos dirUp = dir.rotate90DegToNew(true);
          pd = new PosDir(nextStep, dirUp);
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
          Pos dirDown = dir.rotate90DegToNew(false);
          pd = new PosDir(nextStep, dirDown);
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
        }
      }
      // splitter |
      else if (mapItem.equals("|")) {
        if (dir.x == 0) { // pointy end of splitter
          pd = new PosDir(nextStep, dir);
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
        } else { // flat side of splitter
          Pos dirLeft = dir.rotate90DegToNew(true);
          pd = new PosDir(nextStep, dirLeft);
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
          Pos dirRight = dir.rotate90DegToNew(false);
          pd = new PosDir(nextStep, dirRight);
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
        }
      }
      // mirror /
      else if (mapItem.equals("/")) {
        if (dir.equals(Pos.RIGHT) || dir.equals(Pos.LEFT)) {
          pd = new PosDir(nextStep, dir.rotate90DegToNew(true));
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
        } else {
          pd = new PosDir(nextStep, dir.rotate90DegToNew(false));
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
        }
      }
      // mirror \
      else if (mapItem.equals("\\")) {
        if (dir.equals(Pos.RIGHT) || dir.equals(Pos.LEFT)) {
          pd = new PosDir(nextStep, dir.rotate90DegToNew(false));
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
        } else {
          pd = new PosDir(nextStep, dir.rotate90DegToNew(true));
          addPdToWorklistIfNotYetVisited(pd, alreadyVisited, worklist);
        }
      }
    }

//    out();
//    SVG svg = new SVG();
//    beamPositions.forEach(svg::add);
//    out(svg.toConsoleString());

    int energized = beamPositions.size();
    return energized;
  }

  private static void addPdToWorklistIfNotYetVisited(PosDir pd, Set<PosDir> alreadyVisited, List<PosDir> worklist) {
    if (alreadyVisited.add(pd))
      worklist.add(pd);
  }

  private record PosDir(Pos pos, Pos dir) {
    Pos nextStep() {
      return pos.addToNew(dir);
    }
  }

  public void out(Object... str) {
    Util.out(str);
  }


  public String toStringSVG() {
    SVG svg = new SVG();
    return svg.toSVGStringAged();
  }


  public String toStringConsole() {
    SVG svg = new SVG();
    map.forEach(svg::add);
    return svg.toConsoleString();
  }
}
