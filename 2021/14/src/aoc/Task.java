package aoc;

import aoc.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
  private String last = "";
  private final Map<Pair, Long> pair2count = new HashMap<>();

  private final Map<Pair, List<Pair>> mapping = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    if (input.trim().isEmpty()) return;
    ArrayList<String> list;
    if (last.isEmpty()) {
      list = Util.str2List(input);
      last = list.getLast();
      for (int i = 0; i < list.size() - 1; i++) {
        Pair pair = new Pair(list.get(i), list.get(i + 1));
        addPairToMap(pair, 1);
      }
    } else {
      list = Util.str2List(Util.cleanFrom(input, " -> "));

      mapping.put(new Pair(list.get(0), list.get(1)),
          List.of(new Pair(list.get(0), list.get(2)), new Pair(list.get(2), list.get(1))));
    }
  }


  public void afterParse() throws Exception {
    for (int i = 0; i < 10; i++) {
      step();
    }
    out("part 1:", calcResultForMap());
    for (int i = 0; i < 30; i++) {
      step();
    }
    out("part 2:", calcResultForMap());

  }

  private void step() {
    Map<Pair, Long> p2c = new HashMap<>(pair2count);
    pair2count.clear();
    for (Map.Entry<Pair, Long> entry : p2c.entrySet()) {
      Pair pair = entry.getKey();
      Long count = entry.getValue();

      List<Pair> generatedPairs = mapping.get(pair);
      for (Pair generatedPair : generatedPairs) {
        addPairToMap(generatedPair, count);
      }
    }
  }

  private long calcResultForMap() {
    Map<String, Long> letter2count = new HashMap<>();
    for (Map.Entry<Pair, Long> entry : pair2count.entrySet()) {
      String s = entry.getKey().left();
      Long soFarCount = letter2count.getOrDefault(s, 0L);
      letter2count.put(s, soFarCount + entry.getValue());
    }

    // add last letter
    Long soFarCount = letter2count.getOrDefault(last, 0L);
    letter2count.put(last, soFarCount + 1);

    List<Long> counts = letter2count.values().stream().sorted().toList();
    return counts.getLast() - counts.getFirst();
  }

  private void addPairToMap(Pair pair, long count) {
    Long soFarCount = pair2count.getOrDefault(pair, 0L);
    pair2count.put(pair, soFarCount + count);
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
