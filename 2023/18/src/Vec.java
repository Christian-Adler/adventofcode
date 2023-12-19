import java.util.ArrayList;
import java.util.List;

public record Vec(Pos pos, Pos dir, int length) {
  Pos getEnd() {
    return pos.addToNew(dir.multToNew(length));
  }

  boolean isOnVec(Pos p) {
    if (p.equals(pos)) return true;

    if ((dir.equals(Pos.UP) || dir.equals(Pos.DOWN)) && p.x == pos.x) {
      int minY = Math.min(pos.y, getEnd().y);
      int maxY = Math.max(pos.y, getEnd().y);
      if (p.y >= minY && p.y <= maxY)
        return true;
    } else if ((dir.equals(Pos.LEFT) || dir.equals(Pos.RIGHT)) && p.y == pos.y) {
      int minX = Math.min(pos.x, getEnd().x);
      int maxX = Math.max(pos.x, getEnd().x);
      if (p.x >= minX && p.x <= maxX)
        return true;
    }

    return false;
  }

  List<Integer> getXValues(int y) {
    List<Integer> result = new ArrayList<>();
    // Waagerecht
    if (dir.equals(Pos.LEFT) || dir.equals(Pos.RIGHT)) {
      if (y == pos.y) {
        for (int i = 0; i < length; i++) {
          result.add(pos.x + dir.x * i);
        }
      }
    } else {
      if (isOnVec(new Pos(pos.x, y)))
        result.add(pos.x);
    }
    return result;
  }

  public static void main(String[] args) {
    Vec vec = new Vec(new Pos(0, 0), Pos.RIGHT, 5);
    System.out.println(vec.isOnVec(new Pos(6, 0)));
  }
}
