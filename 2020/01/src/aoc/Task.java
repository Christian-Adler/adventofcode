package aoc;

import aoc.util.Pair;
import aoc.util.Util;

import java.util.List;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public String part1(List<String> lines, Object... params) throws Exception {
    List<Integer> intList = Util.convertToIntList(lines);
    List<Pair<Integer, Integer>> allPairs = Util.buildPairs(intList);
    for (Pair<Integer, Integer> pair : allPairs) {
      if (pair.key() + pair.value() == 2020)
        return pair.key() * pair.value() + "";
    }
    return null;
  }

  public String part2(List<String> lines, Object... params) throws Exception {
    List<Integer> intList = Util.convertToIntList(lines);
    List<List<Integer>> allTriples = Util.buildTriples(intList);
    for (List<Integer> triple : allTriples) {
      if (triple.get(0) + triple.get(1) + triple.get(2) == 2020)
        return triple.get(0) * (long) triple.get(1) * triple.get(2) + "";
    }
    return null;
  }
}
