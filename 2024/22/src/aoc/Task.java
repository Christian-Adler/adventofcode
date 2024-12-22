package aoc;

import aoc.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task {
  private final List<Secret> buyers = new ArrayList<>();

  private final Set<String> visited = new HashSet<>();

  public void init() {
  }

  public void addLine(String input) {
    buyers.add(new Secret(Long.parseLong(input)));
  }

  public void afterParse() {
    for (Secret buyer : buyers) {
      for (int i = 0; i < 2000; i++) {
        buyer.evolve();
      }
      buyer.buildCache();
      // out(buyer);
    }

    out("part 1", "sum of secrets", buyers.stream().mapToLong(Secret::getSecret).sum());

    long mostBananas = -1;
    String mostBananasKey = null;

    int counter = 0;
    int numBuyers = buyers.size();

    for (Secret buyer : buyers) {
      counter++;
      if (counter < 10 || counter < 100 && counter % 10 == 0 || counter % 100 == 0)
        out(counter, "/", numBuyers);

      for (String visitedKey : buyer.getCacheKeys()) {
        if (visited.contains(visitedKey))
          continue;

        long sumBananas = 0;
        for (Secret b : buyers) {
          Long p = b.getPriceForSubListKey(visitedKey);
          if (p != null)
            sumBananas += p;
        }

        if (sumBananas > mostBananas) {
          mostBananas = sumBananas;
          mostBananasKey = visitedKey;
        }

        visited.add(visitedKey);
      }
    }

    out("part 2", "most bananas", mostBananas, mostBananasKey);
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
