import java.util.Arrays;
import java.util.List;

public class Robot {
  private Pos p;
  private final Pos v;

  public Robot(String input) {
    List<Pos> pv = Arrays.stream(Util.cleanFrom(input, "p", "v", "=").split(" ")).map(s -> {
      List<Integer> xy = Arrays.stream(s.split(",")).map(Integer::parseInt).toList();
      return new Pos(xy.getFirst(), xy.getLast());
    }).toList();
    this.p = pv.getFirst();
    this.v = pv.getLast();
  }

  public Pos getP() {
    return p;
  }

  public void move(int width, int height) {
    p = new Pos((p.x + v.x + width) % width, (p.y + v.y + height) % height);
  }

  @Override
  public String toString() {
    return "Robot{" +
        "p=" + p +
        ", v=" + v +
        '}';
  }
}
