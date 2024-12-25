package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Schema {
  private final Set<Vec> map = new HashSet<>();
  private int maxY = -1;
  private int maxX = -1;
  private List<Integer> heights = new ArrayList<>();

  public void addInput(String input) {
    maxY++;
    ArrayList<String> list = Util.str2List(input);
    int x = -1;
    for (String s : list) {
      x++;
      if (s.equals("#"))
        map.add(new Vec(x, maxY));
    }
    maxX = x;
  }

  public boolean isLock() {
    return map.contains(new Vec(0, 0));
  }

  public boolean isKey() {
    return !isLock();
  }

  public void evalHeights() {
    for (int x = 0; x <= maxX; x++) {
      int pinHeight = -1;
      for (int y = 0; y <= maxY; y++) {
        if (map.contains(new Vec(x, y)))
          pinHeight++;
      }
      heights.add(pinHeight);
    }
  }

  public List<Integer> getHeights() {
    return heights;
  }

  public boolean fits(Schema other) {
    for (int x = 0; x <= maxX; x++) {
      if (heights.get(x) + other.heights.get(x) > 5)
        return false;
    }
    return true;
  }

  @Override
  public String toString() {
    Img img = new Img();
    img.add(new Vec(0, 0), ".");
    img.add(new Vec(maxX, maxY), ".");
    for (Vec vec : map) {
      img.add(vec);
    }
    return (isKey() ? "Key" : "Lock") + "\r\n" + img.toConsoleString();
  }
}
