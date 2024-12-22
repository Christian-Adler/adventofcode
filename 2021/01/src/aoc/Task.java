package aoc;

import aoc.util.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Task {

  private final List<Integer> sonarPings = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    sonarPings.add(Integer.parseInt(input));
  }

  public void afterParse() throws Exception {
    int largerThenPrev = getLargerThenPrevCount(sonarPings);
    out("part 1", "num increase", largerThenPrev);

    // part 2 : create new list of sums

    List<Integer> sum3s = new LinkedList<>();
    List<Integer> prev = new LinkedList<>();
    for (Integer sonarPing : sonarPings) {
      if (prev.size() == 2)
        sum3s.add(prev.get(0) + prev.get(1) + sonarPing);

      prev.add(sonarPing);
      while (prev.size() > 2)
        prev.removeFirst();
    }

    largerThenPrev = getLargerThenPrevCount(sum3s);
    out("part 2", "num increase", largerThenPrev);
  }

  private static int getLargerThenPrevCount(List<Integer> integerList) {
    int largerThenPrev = 0;
    int prev = -1;
    for (Integer sonarPing : integerList) {
      if (prev >= 0 && sonarPing > prev) largerThenPrev++;
      prev = sonarPing;
    }
    return largerThenPrev;
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
