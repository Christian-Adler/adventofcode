package aoc;

import aoc.util.Util;

import java.util.*;

public class Task {
  private final List<Integer> lanternFishs = new ArrayList<>();

  private final Map<String, Long> cache = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    lanternFishs.addAll(Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).boxed().toList());
  }

  public void afterParse() {
    long count = 0;
    for (Integer lanternFish : lanternFishs) {
      count += calcFish(lanternFish, 80);
    }
    out("part 1", "count", count);

    count = 0;
    for (Integer lanternFish : lanternFishs) {
      count += calcFish(lanternFish, 256);
    }
    out("part 2", "count", count);
  }

  private long calcFish(int start, int days) {
    String cacheKey = start + "_" + days;
    Long cached = cache.get(cacheKey);
    if (cached != null)
      return cached;

    int decrement = start;
    long count = 1;
    while (days > 0) {
      days -= decrement;
      if (days > 0)
        count += calcFish(9, days); // 8+1
      decrement = 7; // 6+1
    }

    cache.put(cacheKey, count);

    return count;
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
