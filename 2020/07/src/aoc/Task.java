package aoc;

import aoc.util.Pair;
import aoc.util.Util;

import java.util.*;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    // runForInput("./input_example_2.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    Map<String, Set<Pair<String, Integer>>> rules = new HashMap<>();
    for (String line : lines) {
      String[] split = line.split(" bags contain ");
      String bagType = split[0];
      String contains = split[1];
      if (contains.equals("no other bags.")) {
        rules.put(bagType, Set.of());
        continue;
      }
      Set<Pair<String, Integer>> containedBags = new HashSet<>();
      String[] contained = Util.cleanFrom(contains, "bags", "bag", ".").split(",");
      Arrays.stream(contained).forEach(c -> {
        String[] cSplit = c.trim().split(" ", 2);
        int count = Integer.parseInt(cSplit[0]);
        String bag = cSplit[1];
        containedBags.add(new Pair<>(bag, count));
      });
      rules.put(bagType, containedBags);
    }
    // out(rules);

    Set<String> containsEventuallyShinyGold = new HashSet<>();
    findBagsContaining("shiny gold", containsEventuallyShinyGold, rules);
    // out(containsEventuallyShinyGold);
    out("part 1", containsEventuallyShinyGold.size());

    Map<String, Long> cache = new HashMap<>();
    long containedBags = countContainedBags("shiny gold", cache, rules);
    out("part 2", containedBags);

  }

  private long countContainedBags(String searchBag, Map<String, Long> cache, Map<String, Set<Pair<String, Integer>>> rules) {

    if (cache.containsKey(searchBag)) return cache.get(searchBag);

    long res = 0;

    Set<Pair<String, Integer>> containing = rules.get(searchBag);
    if (containing == null) {
      cache.put(searchBag, 0L);
      return 0;
    }
    for (Pair<String, Integer> pair : containing) {
      res += pair.value();
      res += pair.value() * countContainedBags(pair.key(), cache, rules);
    }

    cache.put(searchBag, res);
    return res;
  }

  private void findBagsContaining(String searchBag, Set<String> containsEventuallyShinyGold, Map<String, Set<Pair<String, Integer>>> rules) {
    for (Map.Entry<String, Set<Pair<String, Integer>>> entry : rules.entrySet()) {
      String bag = entry.getKey();
      for (Pair<String, Integer> pair : entry.getValue()) {
        if (pair.key().equals(searchBag)) {
          containsEventuallyShinyGold.add(bag);
          findBagsContaining(bag, containsEventuallyShinyGold, rules);
        }
      }
    }
  }

  public String part2(List<String> lines, Object... params) throws Exception {
    return null;
  }
}
