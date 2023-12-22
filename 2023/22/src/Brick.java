import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

// Brick Pos is the center of the Edge
public final class Brick {
  private static final AtomicLong idGenerator = new AtomicLong();

  private final long id;
  private final Pos p1;
  private final Pos p2;

  public final Set<Brick> supports = new HashSet<>();
  public final Set<Brick> supportedBy = new HashSet<>();


  public Brick(Pos p1, Pos p2) {
    this.id = idGenerator.incrementAndGet();
    this.p1 = p1;
    this.p2 = p2;
  }

  public static Brick from(String input) {
    String[] split = input.split("~");
    return new Brick(Pos.from(split[0]), Pos.from(split[1]));
  }

  public int volumeInCubes() {
    int xCubes = Math.abs(p2.x - p1.x) + 1;
    int yCubes = Math.abs(p2.y - p1.y) + 1;
    int zCubes = Math.abs(p2.z - p1.z) + 1;
    return xCubes * yCubes * zCubes;
  }

  public int minZ() {
    return Math.min(p1.z, p2.z);
  }

  public int maxZ() {
    return Math.max(p1.z, p2.z);
  }

  public int minX() {
    return Math.min(p1.x, p2.x);
  }

  public int maxX() {
    return Math.max(p1.x, p2.x);
  }

  public int minY() {
    return Math.min(p1.y, p2.y);
  }

  public int maxY() {
    return Math.max(p1.y, p2.y);
  }

  public boolean hitTest(Pos pos) {
    return pos.x >= minX() && pos.x <= maxX() && pos.y >= minY() && pos.y <= maxY() && pos.z >= minZ() && pos.z <= maxZ();
  }

  public boolean hitTest(Collection<Pos> positions) {
    for (Pos pos : positions) {
      boolean hit = hitTest(pos);
      if (hit) return true;
    }
    return false;
  }

  public void move(int x, int y, int z) {
    p1.add(x, y, z);
    p2.add(x, y, z);
  }

  public Brick addToNew(int x, int y, int z) {
    return new Brick(p1.addToNew(x, y, z), p2.addToNew(x, y, z));
  }

  public Pos p1() {
    return p1;
  }

  public Pos p2() {
    return p2;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Brick) obj;
    return this.id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Brick(" + id + ")[" +
        "p1=" + p1 + ", " +
        "p2=" + p2 + ']';
  }

  public Set<Brick> getBricksOnlySupportedBy() {
    Set<Brick> onlySupportedBy = new HashSet<>();
    // iterate all supported
    for (Brick supportedBrick : supports) {
      if (supportedBrick.supportedBy.size() == 1) {
        onlySupportedBy.add(supportedBrick);
      }
    }
    return onlySupportedBy;
  }

}
