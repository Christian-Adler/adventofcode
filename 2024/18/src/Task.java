import java.util.HashSet;
import java.util.Set;

public class Task {
  private final Set<Vec> obstacles = new HashSet<>();
  private final Vec start = new Vec(0, 0);
  private final Vec end = new Vec(0, 0);

  public void init() {
  }

  public void addLine(String input) {
    if (obstacles.size() < 12)  // part 1 only 12 bytes
      obstacles.add(new Vec(input));

  }

  public void afterParse(int width, int height) throws Exception {
    out(toStringConsole(width, height));
  }

  public void out(Object... str) {
    Util.out(str);
  }


  public void toBmp(int width, int height) throws Exception {
    Img img = new Img();
    img.writeBitmapAged();
  }

  public String toStringConsole(int width, int height) {
    Img img = new Img();
    img.add(new Vec(width, height), ".");
    img.add(new Vec(0, 0), ".");

    for (Vec obstacle : obstacles) {
      img.add(obstacle);
    }
    return img.toConsoleString();
  }
}
