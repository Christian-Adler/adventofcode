package aoc;

import aoc.util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    // runForInput("./input_example_2.txt");
    runForInput("./input.txt");
  }

  public String part1(List<String> lines, Object... params) throws Exception {
    long yesCounts = 0;

    Set<String> answers = new HashSet<>();
    for (String line : lines) {
      if (line.isBlank()) {
        yesCounts += answers.size();
        answers.clear();
        continue;
      }
      answers.addAll(Util.str2List(line));
    }
    yesCounts += answers.size();

    return String.valueOf(yesCounts);
  }

  public String part2(List<String> lines, Object... params) throws Exception {
    long yesCounts = 0;

    Set<String> answers = null;
    for (String line : lines) {
      if (line.isBlank()) {
        if (answers != null) {
          yesCounts += answers.size();
          answers = null;
        }
        continue;
      }

      if (answers == null) {
        answers = new HashSet<>(Util.str2List(line));
      } else
        answers.retainAll(Util.str2List(line));
    }
    if (answers != null)
      yesCounts += answers.size();

    return String.valueOf(yesCounts);
  }
}
