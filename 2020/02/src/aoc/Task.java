package aoc;

import aoc.util.Range;
import aoc.util.Util;

import java.util.List;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public String part1(List<String> lines, Object... params) throws Exception {
    int countValid = 0;
    for (String line : lines) {
      String[] split = line.split(" ");
      Range range = Range.parse(split[0]);
      String letter = Util.cleanFrom(split[1], ":").trim();
      String password = split[2];
      String replaced = password.replace(letter, "");
      int lengthDifference = password.length() - replaced.length();
      if (range.contains(lengthDifference))
        countValid++;
    }
    return String.valueOf(countValid);
  }

  public String part2(List<String> lines, Object... params) throws Exception {
    int countValid = 0;
    for (String line : lines) {
      String[] split = line.split(" ");
      Range range = Range.parse(split[0]);
      char letter = Util.cleanFrom(split[1], ":").trim().charAt(0);
      String password = split[2];

      int count = 0;
      if (password.length() > range.from() - 1 && password.charAt((int) (range.from() - 1)) == letter)
        count++;
      if (password.length() > range.to() - 1 && password.charAt((int) (range.to() - 1)) == letter)
        count++;
      if (count == 1)
        countValid++;
    }
    return String.valueOf(countValid); // <416
  }
}
