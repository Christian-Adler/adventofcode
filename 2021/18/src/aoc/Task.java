package aoc;

import aoc.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Task {
  List<Pair> pairs = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    Pair pair = Pair.parse(input, null);
    pairs.add(pair);
  }

  public void afterParse() throws Exception {
    Pair actPair = null;
    for (Pair pair : pairs) {
      if (actPair == null)
        actPair = pair;
      else
        actPair = Pair.add(actPair, pair);

      actPair.reduce();
    }
    if (actPair == null) throw new IllegalStateException("Null");

    out("part 1", "magnitude", actPair.magnitude());

    List<Pair> allPairs = buildPairs(pairs);
    long maxMagnitude = allPairs.stream().mapToLong(p -> {
      p.reduce();
      return p.magnitude();
    }).max().orElseThrow();
    out("part 2", "max add magnitude", maxMagnitude);
  }

  public static List<Pair> buildPairs(Collection<Pair> collection) {
    List<Pair> list = new ArrayList<>(collection);
    List<Pair> result = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      Pair item1 = list.get(i);
      for (int j = i + 1; j < list.size(); j++) {
        Pair item2 = list.get(j);
        result.add(Pair.add(item1, item2));
        result.add(Pair.add(item2, item1));
      }
    }
    return result;
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
