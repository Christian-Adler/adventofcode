import java.awt.*;
import java.util.List;
import java.util.*;

public class Task {
  private Set<Vec> walls = new HashSet<>();
  private Set<Vec> boxes = new HashSet<>();
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
        if (i.equals("#"))
          walls.add(new Vec(x, maxY));
        else if (i.equals("O"))
          boxes.add(new Vec(x, maxY));
        else if (i.equals("@"))
          robot = new Vec(x, maxY);
      }

    } else // Movement
      Util.str2List(input).forEach(m -> movements.add(movementMapping.get(m)));
  }

  public void afterParse() throws Exception {
    // out(toString());
    move();
    // out();
    // out(toString());
    out("part 1", "sum box gps", sumBoxCoordinates());
  }

  private void move() {
    int step = 0;
    for (Vec dir : movements) {
      step++;
      Vec nextPosWouldBe = robot.addToNew(dir);
      if (boxes.contains(nextPosWouldBe)) {
        // move box if possible
        // move multiple boxes at once is allowed!
        List<Vec> boxesToMoveMaybe = new ArrayList<>();
        Vec actPos = robot.copy();
        while (true) {
          actPos.add(dir);
          if (walls.contains(actPos)) {
            boxesToMoveMaybe.clear();
            // out("\r\nstep against wall with boxes");
            break;
          } else if (boxes.contains(actPos))
            boxesToMoveMaybe.add(actPos.copy());
          else // free space -> move
            break;
        }

        if (!boxesToMoveMaybe.isEmpty()) {
          // move all boxes one step in dir
          boxesToMoveMaybe.forEach(boxes::remove);
          for (Vec box : boxesToMoveMaybe) {
            boxes.add(box.addToNew(dir));
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
    for (Vec box : boxes) {
      sum += box.y * 100L + box.x;
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
    for (Vec box : boxes) {
      img.add(box, Color.ORANGE);
    }
    img.add(robot, Color.blue);
    img.writeBitmap();
  }

  public String toStringConsole() {
    Img img = new Img();
    for (Vec wall : walls) {
      img.add(wall, "#");
    }
    for (Vec box : boxes) {
      img.add(box, "O");
    }
    img.add(robot, "@");
    return img.toConsoleString();
  }
}
