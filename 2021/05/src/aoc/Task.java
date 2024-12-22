package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
  private final List<Vent> vents = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    vents.add(new Vent(input));
  }

  public void afterParse() throws Exception {
    // out(vents);

    int countOverlaps = 0;
    int countOverlaps2 = 0;

    Map<Vec, Integer> map = new HashMap<>();
    Map<Vec, Integer> map2 = new HashMap<>();
    for (Vent vent : vents) {
      if (vent.isStraightLine()) {
        List<Vec> vecs = vent.getLine();
        for (Vec vec : vecs) {
          Integer soFarValue = map.getOrDefault(vec, 0);
          map.put(vec, soFarValue + 1);
          if (soFarValue == 1)
            countOverlaps++;
        }
      }
      if (vent.isStraightOrDiagonalLine()) {
        List<Vec> vecs = vent.getLine();
        for (Vec vec : vecs) {
          Integer soFarValue = map2.getOrDefault(vec, 0);
          map2.put(vec, soFarValue + 1);
          if (soFarValue == 1)
            countOverlaps2++;
        }
      }
    }

    // Img img = new Img();
    // for (Map.Entry<Vec, Integer> entry : map2.entrySet()) {
    //   img.add(entry.getKey(), entry.getValue().toString());
    // }
    // out(img.toConsoleString());

    out("part 1", "overlaps", countOverlaps);
    out("part 2", "overlaps", countOverlaps2);
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
    img.writeBitmapAged();
  }

  public String toStringConsole() {
    Img img = new Img();
    return img.toConsoleString();
  }
}
