package aoc;

public class Dial {
  private int pos = 50;
  private int count0 = 0;
  private int count0duringRotation = 0;

  public void rotate(String line) {
    int amount = Integer.parseInt(line.substring(1));

    if (amount >= 100) {
      int over0byRotations = amount / 100;
      count0duringRotation += over0byRotations;
    }

    amount = amount % 100;

    boolean wasZero = pos == 0;

    if (line.startsWith("R")) {
      pos += amount;
      if (pos > 100) {
        count0duringRotation++;
      }
    } else {
      pos -= amount;
      if (pos < 0) {
        if (!wasZero)
          count0duringRotation++;
        pos += 100;
      }
    }
    pos %= 100;
    if (pos == 0) count0++;
  }

  public int getCount0() {
    return count0;
  }

  public int getCount0duringRotation() {
    return count0duringRotation;
  }

  @Override
  public String toString() {
    return "Dial{" +
        "pos=" + pos +
        ", count0=" + count0 +
        ", count0duringRotation=" + count0duringRotation +
        '}';
  }
}
