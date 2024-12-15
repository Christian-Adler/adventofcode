import java.awt.*;

public class Box {
  private final Vec pos;

  public Box(Vec pos) {
    this.pos = pos;
  }

  public void move(Vec dir) {
    pos.add(dir);
  }

  public boolean hits(Vec vec) {
    return pos.equals(vec) || pos.addToNew(Vec.RIGHT).equals(vec);
  }

  public long getGPS() {
    return pos.y * 100L + pos.x;
  }

  public Vec getPos1() {
    return pos.copy();
  }

  public Vec getPos2() {
    return pos.addToNew(Vec.RIGHT).copy();
  }

  public void draw(Img img, boolean console) {
    img.add(pos, console ? "[" : Color.ORANGE);
    img.add(pos.addToNew(Vec.RIGHT), console ? "]" : Color.YELLOW);
  }

  @Override
  public String toString() {
    return "Box{" +
        "pos=" + pos +
        '}';
  }
}
