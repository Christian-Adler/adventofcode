package aoc;

import aoc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Task {
  private List<Integer> horizontals = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    horizontals = Util.getIntegersFromCommaInput(input);
  }

  public void afterParse() throws Exception {
    int min = horizontals.stream().min(Integer::compareTo).orElseThrow();
    int max = horizontals.stream().max(Integer::compareTo).orElseThrow();

    int minFuel = Integer.MAX_VALUE;
    for (int h = min; h <= max; h++) {
      int sumFuel = 0;
      for (Integer horizontal : horizontals) {
        sumFuel += Math.abs(horizontal - h);
      }
      if (sumFuel < minFuel) minFuel = sumFuel;
    }
    out("part 1", "min sum fuel", minFuel);

    // part 2
    minFuel = Integer.MAX_VALUE;
    for (int h = 5; h <= max; h++) {
      int sumFuel = 0;
      for (Integer horizontal : horizontals) {
        sumFuel += getSummedUp(Math.abs(horizontal - h));
      }
      if (sumFuel < minFuel) minFuel = sumFuel;
    }
    out("part 2", "min sum fuel", minFuel);
  }

  public static int getSummedUp(int f) {
    if (f <= 1)
      return 1;
    return f + getSummedUp(f - 1);
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
