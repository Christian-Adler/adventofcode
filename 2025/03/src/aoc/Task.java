package aoc;

import java.util.List;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {

    long jolatgeSum = 0;
    long jolatgeSum12 = 0;
    for (String line : lines) {
      long bankJoltage = Bank.calcBankJoltage(line);
      // out(bankJoltage);
      jolatgeSum += bankJoltage;

      bankJoltage = Bank.calcBankJoltage12(line);
      // out(bankJoltage);
      jolatgeSum12 += bankJoltage;
    }

    out("part 1: ", jolatgeSum);

    out("part 2: ", jolatgeSum12);
  }
}
