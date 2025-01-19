package aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt", 5);
    runForInput("./input.txt", 25);
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    int preambleSize = (int) params[0];

    long invalidNum = part1(lines, preambleSize);

    part2(lines, preambleSize, invalidNum);

  }

  private static void part2(List<String> lines, int preambleSize, long invalidNumber) {

    ArrayList<Long> lastNumbers = new ArrayList<>();

    int counter = 0;
    for (String line : lines) {
      counter++;
      long num = Long.parseLong(line);
      if (counter > preambleSize) {
        for (int i = 0; lastNumbers.size() > i; i++) {
          long sum = lastNumbers.get(i);
          for (int j = i + 1; j < lastNumbers.size(); j++) {
            sum += lastNumbers.get(j);
            if (invalidNumber == sum) {
              Set<Long> numbers = new HashSet<>();
              for (int k = i; k <= j; k++) {
                numbers.add(lastNumbers.get(k));
              }
              long min = numbers.stream().mapToLong(n -> n).min().orElseThrow();
              long max = numbers.stream().mapToLong(n -> n).max().orElseThrow();

              out("part 2: ", min + max);
              return;
            }
          }
        }
      }
      if (lastNumbers.size() >= 25)
        lastNumbers.removeFirst();
      lastNumbers.add(num);
    }

    throw new IllegalStateException("Found no result");
  }

  private static long part1(List<String> lines, int preambleSize) {

    ArrayList<Long> lastNumbers = new ArrayList<>();


    int counter = 0;
    for (String line : lines) {
      counter++;
      long num = Long.parseLong(line);
      if (counter > preambleSize) {
        boolean foundSum = false;
        for (int i = 0; i < lastNumbers.size() && !foundSum; i++) {
          for (int j = i + 1; j < lastNumbers.size(); j++) {
            if (lastNumbers.get(i) + lastNumbers.get(j) == num) {
              foundSum = true;
              break;
            }
          }
        }
        if (!foundSum) {
          out("part 1: ", "invalid number: ", num);
          return num;
        }
      }
      if (lastNumbers.size() >= preambleSize)
        lastNumbers.removeFirst();
      lastNumbers.add(num);
    }

    throw new IllegalStateException("Found no result");
  }
}
