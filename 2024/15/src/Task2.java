import java.awt.*;
import java.util.List;
import java.util.*;

public class Task2 {
  private Set<Vec> walls = new HashSet<>();
  private Set<Box> boxes = new HashSet<>();
  private Vec robot = null;
  private List<Vec> movements = new ArrayList<>();

  private int maxY = -1;

  private final Map<String, Vec> movementMapping = new HashMap<>();

  public void init() {
    movementMapping.put("^", Vec.UP);
    movementMapping.put("v", Vec.DOWN);
    movementMapping.put("<", Vec.LEFT);
    movementMapping.put(">", Vec.RIGHT);
  }

  public void addLine(String input) {
    if (input.isEmpty()) return;
    if (input.startsWith("#")) { // map
      maxY++;
      ArrayList<String> in = Util.str2List(input);
      for (int x = 0; x < in.size(); x++) {
        String i = in.get(x);
        if (i.equals("#")) {
          walls.add(new Vec(x * 2, maxY));
          walls.add(new Vec(x * 2 + 1, maxY));
        } else if (i.equals("O")) {
          boxes.add(new Box(new Vec(x * 2, maxY)));
        } else if (i.equals("@"))
          robot = new Vec(x * 2, maxY);
      }

    } else // Movement
      Util.str2List(input).forEach(m -> movements.add(movementMapping.get(m)));
  }

  public void afterParse() throws Exception {
    // out(toString());
    move();
    out();
    // out(toString());
    out("part 2", "sum box gps", sumBoxCoordinates());
  }

  private Box hitsBox(Vec vec) {
    for (Box box : boxes) {
      if (box.hits(vec)) return box;
    }
    return null;
  }

  private void move() {
    int step = 0;
    for (Vec dir : movements) {
      step++;
      Vec nextPosWouldBe = robot.addToNew(dir);
      if (hitsBox(nextPosWouldBe) != null) {
        // move box if possible
        // move multiple boxes at once is allowed!
        Set<Box> boxesToMoveMaybe = new HashSet<>();
        List<Box> boxesWorkList = new LinkedList<>();
        Box firstBox = hitsBox(nextPosWouldBe);
        boxesToMoveMaybe.add(firstBox);
        boxesWorkList.add(firstBox);
        while (!boxesWorkList.isEmpty()) {
          Box box = boxesWorkList.removeFirst();

          // check for each box is moveable
          Vec p1Next = box.getPos1().addToNew(dir);
          Vec p2Next = box.getPos2().addToNew(dir);

          if (walls.contains(p1Next) || walls.contains(p2Next)) {
            boxesToMoveMaybe.clear();
            // out("\r\nstep against wall with boxes");
            break;
          }

          Box nextBox = hitsBox(p1Next);
          if (nextBox != null && boxesToMoveMaybe.add(nextBox))
            boxesWorkList.add(nextBox);
          nextBox = hitsBox(p2Next);
          if (nextBox != null && boxesToMoveMaybe.add(nextBox))
            boxesWorkList.add(nextBox);
        }

        if (!boxesToMoveMaybe.isEmpty()) {
          // move all boxes one step in dir
          for (Box box : boxesToMoveMaybe) {
            box.move(dir);
          }
          // and robot as well
          robot.add(dir);
        }
      } else if (!walls.contains(nextPosWouldBe))
        robot.add(dir); // just move
      // else
      //   out("\r\nstep against wall");

      // out("\r\nstep", step);
      // out(toString());
    }
  }

  private long sumBoxCoordinates() {
    long sum = 0;
    for (Box box : boxes) {
      sum += box.getGPS();
    }
    return sum;
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
    for (Vec wall : walls) {
      img.add(wall, Color.GRAY);
    }
    for (Box box : boxes) {
      box.draw(img, false);
    }
    img.add(robot, Color.blue);
    img.writeBitmap();
  }

  public String toStringConsole() {
    Img img = new Img();
    for (Vec wall : walls) {
      img.add(wall, "#");
    }
    for (Box box : boxes) {
      box.draw(img, true);
    }
    img.add(robot, "@");
    return img.toConsoleString();
  }
}
