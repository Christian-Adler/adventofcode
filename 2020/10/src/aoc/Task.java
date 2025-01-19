package aoc;

import aoc.util.Util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    // runForInput("./input_example_2.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {

    List<Integer> sorted = new ArrayList<>(Util.convertToIntList(lines).stream().sorted().toList());
    sorted.addFirst(0);
    sorted.addLast(sorted.getLast() + 3);

    List<Integer> splitIdxs = new ArrayList<>();

    int diff1 = 0;
    int diff3 = 0;
    for (int i = 1; i < sorted.size(); i++) {
      Integer prev = sorted.get(i - 1);
      Integer act = sorted.get(i);
      if (act - prev == 1) diff1++;
      if (act - prev == 3) {
        diff3++;
        splitIdxs.add(i); // for part 2
      }
    }

    // out(diff1, diff3);
    out("part 1: ", diff1 * diff3);

    // part 2
    // split sorted list into parts where difference == 3
    // calc for each sublist and multiply all together
    List<List<Integer>> splits = new ArrayList<>();
    int actFrom = 0;
    for (Integer splitIdx : splitIdxs) {
      splits.add(sorted.subList(actFrom, splitIdx));
      actFrom = splitIdx;
    }
    // out(splits);

    long numArrangements = 1;
    for (List<Integer> integers : splits) {
      numArrangements *= calcPossibleArrangements(integers);
    }

    out("part 2: ", numArrangements);
  }

  private long calcPossibleArrangements(List<Integer> integers) {
    if (integers.size() <= 2) return 1;
    long arrangements = 0;
    int first = integers.getFirst();
    arrangements += calcPossibleArrangements(integers.subList(1, integers.size()));
    if (integers.size() > 2 && integers.get(2) - first <= 3)
      arrangements += calcPossibleArrangements(integers.subList(2, integers.size()));
    if (integers.size() > 3 && integers.get(3) - first <= 3)
      arrangements += calcPossibleArrangements(integers.subList(3, integers.size()));

    return arrangements;
  }
}
