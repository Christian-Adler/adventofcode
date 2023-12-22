import java.util.*;

public class Task {
  private Set<Brick> bricks = new HashSet<>();

  public void init() {
  }

  public void addLine(String input) {
    bricks.add(Brick.from(input));
  }

  public void afterParse() {
//    out(bricks);
    out("falling bricks...", System.currentTimeMillis());
    List<Brick> worklist = new ArrayList<>(bricks);
    worklist.sort(Comparator.comparingInt(Brick::minZ));
//    out(bricks);
    List<Brick> setList = new ArrayList<>();
    while (!worklist.isEmpty()) {
      Brick brick = worklist.removeFirst();
      while (brick.minZ() > 1) {
        Set<Pos> checkPositions = new HashSet<>();
        for (int x = brick.minX(); x <= brick.maxX(); x++) {
          for (int y = brick.minY(); y <= brick.maxY(); y++) {
            checkPositions.add(new Pos(x, y, brick.minZ() - 1));
          }
        }

        // hit-test with all already set
        boolean foundHitTest = false;
        for (Brick alreadySetBrick : setList) {
          if (alreadySetBrick.maxZ() != brick.minZ() - 1)
            continue;
          foundHitTest = alreadySetBrick.hitTest(checkPositions);
          if (foundHitTest)
            break;
        }
        if (foundHitTest)
          break;

        brick.move(0, 0, -1);
      }
      setList.add(brick);
    }

//    out(setList);

    out("Build supportLists...", System.currentTimeMillis());
    worklist = new ArrayList<>(bricks);
    worklist.sort(Comparator.comparingInt(Brick::minZ));
    for (int i = 0; i < worklist.size(); i++) {
      Brick brick = worklist.get(i);
      // check all below
      if (brick.minZ() == 1) // on the ground? don't check
        continue;

      Set<Pos> checkPositions = new HashSet<>();
      for (int x = brick.minX(); x <= brick.maxX(); x++) {
        for (int y = brick.minY(); y <= brick.maxY(); y++) {
          checkPositions.add(new Pos(x, y, brick.minZ() - 1));
        }
      }

      Set<Brick> checkSet = new HashSet<>(worklist.subList(0, i));
      checkSet.remove(brick);

      // hit-test with all others
      for (Brick hitTestBrick : checkSet) {
        // check first if it could hit by z
        if (hitTestBrick.maxZ() != brick.minZ() - 1)
          continue;

        if (hitTestBrick.hitTest(checkPositions)) {
          brick.supportedBy.add(hitTestBrick);
          hitTestBrick.supports.add(brick);
        }
      }
    }

    out("check for removable bricks", System.currentTimeMillis());
    long countSafelyRemovable = bricks.stream().filter(b -> b.getBricksOnlySupportedBy().isEmpty()).count();
    out("Part 1 ", countSafelyRemovable);


    // Part 2
    int countFallingBricks = 0;

    /*
    ====
    x  y
    ----
    weder x noch y alleine reichen zum Fall aber ---- reist alles ein.
     */

    Map<Brick, Set<Brick>> brick2FallingBricks = new HashMap<>();

    worklist = new ArrayList<>(bricks);
    worklist.sort(Comparator.comparingInt(Brick::minZ).reversed());
//    out(worklist);
    for (Brick brick : worklist) {
      Set<Brick> fallingBricks = new HashSet<>();
      fallingBricks.add(brick);

      List<Brick> perBrickWorkList = new ArrayList<>();
      perBrickWorkList.add(brick);

      while (!perBrickWorkList.isEmpty()) {
        Brick checkBrick = perBrickWorkList.removeFirst();
        for (Brick supportedBrick : checkBrick.supports) {
          if (fallingBricks.containsAll(supportedBrick.supportedBy)) {
            fallingBricks.add(supportedBrick);
            perBrickWorkList.add(supportedBrick);
          }
        }
      }

      countFallingBricks += fallingBricks.size() - 1; // -1 remove brick itself
    }

    out("Part 2:", countFallingBricks);
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
