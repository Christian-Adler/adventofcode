import java.util.*;

public class Task {
  private final Map<Pos, String> map = new HashMap<>();
  private int actY = -1;
  private Pos start;

  private final Map<String, ArrayList<Pos>> connections = new HashMap<>();

  public void init() {
    connections.put("|", new ArrayList<>(Arrays.asList(Pos.UP, Pos.DOWN)));
    connections.put("-", new ArrayList<>(Arrays.asList(Pos.RIGHT, Pos.LEFT)));
    connections.put("L", new ArrayList<>(Arrays.asList(Pos.RIGHT, Pos.UP)));
    connections.put("J", new ArrayList<>(Arrays.asList(Pos.LEFT, Pos.UP)));
    connections.put("7", new ArrayList<>(Arrays.asList(Pos.LEFT, Pos.DOWN)));
    connections.put("F", new ArrayList<>(Arrays.asList(Pos.RIGHT, Pos.DOWN)));
  }

  public void addLine(String input) {
    actY++;
    ArrayList<String> chars = Util.str2List(input);
    for (int x = 0; x < chars.size(); x++) {
      String c = chars.get(x);
      if (!c.equals("."))
        map.put(new Pos(x, actY), c);
      if (c.equals("S"))
        start = new Pos(x, actY);
    }
  }

  public void afterParse() {
    out("Start", start);
    // Assume, S has only 2 matching neighbors (not 3 with one dead end)
    Pos actDir = null;
    for (Pos adjacent : Pos.adjacent) {
      String nextToStart = map.get(start.addToNew(adjacent));
      if (nextToStart == null) continue;
      Pos mirrorDir = adjacent.mirrorToNew();
      if (connections.get(nextToStart).contains(mirrorDir)) {
        actDir = adjacent;
        break;
      }
    }
    if (actDir == null)
      throw new IllegalStateException("Found no direction for START!");

    Pos startDir = actDir;

//    out(actDir);
    List<Pos> path = new ArrayList<>();
    Pos actPos = start.addToNew(actDir);
    path.add(actPos);
    int steps = 1;
    while (!actPos.equals(start)) {
      steps++;

      String actPosConnectionKey = map.get(actPos);
      if (actPosConnectionKey == null)
        throw new IllegalStateException("Found no connection key");

//      out(actPos, actPosConnectionKey);
      ArrayList<Pos> possibleDirections = new ArrayList<>(connections.get(actPosConnectionKey));
      // remove opposite of current dir - the remaining pos is the next dir
      possibleDirections.remove(actDir.mirrorToNew());
      actDir = possibleDirections.getFirst();
      actPos = actPos.addToNew(actDir);
      path.add(actPos);
    }

    Pos start2Dir = actDir.mirrorToNew();

    String startLetter = null;
    for (Map.Entry<String, ArrayList<Pos>> entry : connections.entrySet()) {
      ArrayList<Pos> list = entry.getValue();
      if (list.contains(startDir) && list.contains(start2Dir)) {
        startLetter = entry.getKey();
        break;
      }
    }


    out("Part 1", "max distance steps:", steps / 2);

//    out("path", path.size());

    // Part 2

    out("Start-Letter", startLetter);
    map.put(start, startLetter); // insert StartLetter to be able to start at any pos in path

    // get top left pos of path
    Pos topLeft = null;
    for (Pos pos : path) {
      if (topLeft == null)
        topLeft = pos;
      else if (pos.y < topLeft.y || pos.y == topLeft.y && pos.x < topLeft.x)
        topLeft = pos;
    }

    if (topLeft == null) throw new IllegalStateException("No top left found!");

    // TopLeft needs to be F > inside loop is right down of this pos
//    out(topLeft);
//    out(map.get(topLeft));

    Set<Pos> pathPositions = new HashSet<>(path);
//    out("pathPositions", pathPositions.size());


    List<Pos> pathWithStartTopLeft = new ArrayList<>();
    int topLeftIdx = path.indexOf(topLeft);
    if (topLeftIdx > 0) {
      pathWithStartTopLeft.addAll(path.subList(topLeftIdx, path.size()));
      pathWithStartTopLeft.addAll(path.subList(0, topLeftIdx));
    } else
      pathWithStartTopLeft.addAll(path);

    // Start from TopLeft and go through path
    // if path goes right - right hand side is inside loop
    // Else if path goes down - left hand side is inside loop
    boolean turnRight = pathWithStartTopLeft.get(1).x == topLeft.x + 1;
    out("turnRight", turnRight);

    Set<Pos> inLoopPositions = new HashSet<>();

    Pos prevPos = null;
    for (Pos pos : pathWithStartTopLeft) {
      if (prevPos != null) {
        Pos dir = new Pos(pos.x - prevPos.x, pos.y - prevPos.y);
        Pos possiblyInLoop = prevPos.addToNew(dir.rotate90DegToNew(turnRight));
        if (!pathPositions.contains(possiblyInLoop))
          inLoopPositions.add(possiblyInLoop);
        // Second test necessary! See input_0_6.txt
        possiblyInLoop = pos.addToNew(dir.rotate90DegToNew(turnRight));
        if (!pathPositions.contains(possiblyInLoop))
          inLoopPositions.add(possiblyInLoop);
      }
      prevPos = pos;
    }

    // Find holes
    // III
    // I.I
    // III

    List<Pos> workList = new ArrayList<>(inLoopPositions);
    while (!workList.isEmpty()) {
      actPos = workList.removeFirst();
      for (Pos adjacent : Pos.adjacent) {
        Pos checkPos = actPos.addToNew(adjacent);
        if (pathPositions.contains(checkPos)) continue;
        if (inLoopPositions.contains(checkPos)) continue;
        inLoopPositions.add(checkPos);
        workList.add(checkPos);
      }
    }

    SVG svg = new SVG();
    for (Pos pathPosition : pathPositions) {
//      svg.add(pathPosition, map.get(pathPosition));
      svg.add(pathPosition, "#");
    }

//    prevPos = null;
//    for (Pos pos : pathWithStartTopLeft) {
//      if (prevPos != null) {
//        Pos dir = new Pos(pos.x - prevPos.x, pos.y - prevPos.y);
//        if (dir.equals(Pos.UP))
//          svg.add(prevPos, "^");
//        else if (dir.equals(Pos.DOWN))
//          svg.add(prevPos, "v");
//        else if (dir.equals(Pos.RIGHT))
//          svg.add(prevPos, ">");
//        else if (dir.equals(Pos.LEFT))
//          svg.add(prevPos, "<");
//      }
//      prevPos = pos;
//    }


    for (Pos inLoop : inLoopPositions) {
      svg.add(inLoop, "I");
    }
    out(svg.toConsoleString());

    out("Part 2", "enclosed tiles", inLoopPositions.size());


  }

  public void out(Object... str) {
    Util.out(str);
  }
}
