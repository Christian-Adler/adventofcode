package aoc;

import java.util.List;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    Dial dial = new Dial();
    for (String line : lines) {
      dial.rotate(line);
      // out(dial);
    }

    out("part 1: ", dial.getCount0());

    out("part 2: ", (dial.getCount0() + dial.getCount0duringRotation()));
  }
}
