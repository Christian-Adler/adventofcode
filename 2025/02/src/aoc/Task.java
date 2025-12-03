package aoc;

import java.util.List;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    // runForInput("./input_example_2.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    String[] ranges = lines.getFirst().split(",");

    long sumInvalidIds = 0;
    long sumInvalidIds2 = 0;

    for (String range : ranges) {
      IDRange idRange = new IDRange(range);
      // out(idRange);
      sumInvalidIds += idRange.sumInvalidIds();
      sumInvalidIds2 += idRange.sumInvalidIds2();
    }

    out("part 1: ", sumInvalidIds); // = 19574776074

    out("part 2: ", sumInvalidIds2); // = 25912654282
  }
}
