package aoc;

import aoc.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    // part 1
    List<List<Long>> problems = new ArrayList<>();
    List<Boolean> operators = new ArrayList<>();

    for (String line : lines) {
      Stream<String> strStream = Arrays.stream(line.trim().split("\\s+"));
      if (line.trim().startsWith("+") || line.trim().startsWith("*")) {
        operators.addAll(strStream.map(s -> s.equals("+")).toList());
      } else {
        List<Long> numbers = (strStream.map(Long::parseLong).toList());
        if (problems.isEmpty()) {
          for (Long number : numbers) {
            problems.add(new ArrayList<>());
          }
        }
        for (int i = 0; i < numbers.size(); i++) {
          problems.get(i).add(numbers.get(i));
        }
      }
    }

    long sum = 0;
    for (int p = 0; p < problems.size(); p++) {
      List<Long> problem = problems.get(p);
      // System.out.println(problem);
      Boolean op = operators.get(p);
      if (op) {
        sum += problem.stream().reduce(Long::sum).orElse(0L);
      } else {
        sum += problem.stream().reduce((aLong, aLong2) -> aLong * aLong2).orElse(0L);
      }
    }

    out("part 1: ", sum);

    // part 2
    // column wise
    List<List<String>> chars = new ArrayList<>();

    int maxLineLen = lines.stream().mapToInt(String::length).max().orElse(0);
    for (int i = 0; i < maxLineLen; i++) {
      chars.add(new ArrayList<>());
    }

    for (String line : lines) {
      ArrayList<String> cs = Util.str2List(line);
      for (int i = 0; i < cs.size(); i++) {
        chars.get(i).add(cs.get(i));
      }
    }

    sum = 0;

    boolean actOperator = true;
    long actValue = -1;
    for (List<String> col : chars) {
      boolean hasNewOp = (col.getLast().equals("+") || col.getLast().equals("*"));
      if (hasNewOp) {
        actOperator = col.getLast().equals("+");
      }

      String colStr = String.join("", col).trim();
      if (colStr.isEmpty()) {
        sum += actValue;
        actValue = -1;
        continue;
      }

      if (hasNewOp) colStr = colStr.substring(0, colStr.length() - 1);

      long parsedValue = Long.parseLong(colStr.trim());

      if (hasNewOp)
        actValue = parsedValue;
      else {
        if (actOperator) actValue += parsedValue;
        else actValue *= parsedValue;
      }
    }
    sum += actValue;

    out("part 2: ", sum);
  }
}
