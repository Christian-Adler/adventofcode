package aoc;

import aoc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Task {
  private final List<String> lines = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    lines.add(input);
  }

  public void afterParse() throws Exception {
    int lineLen = lines.getFirst().length();

    StringBuilder gamaRate = new StringBuilder();
    StringBuilder epsilonRate = new StringBuilder();
    for (int i = 0; i < lineLen; i++) {
      Counter counter = countBit(i, lines);

      gamaRate.append(!counter.hasMoreZeros() ? "1" : "0");
      epsilonRate.append(counter.hasMoreZeros() ? "1" : "0");
    }
    int gama = Integer.parseInt(gamaRate.toString(), 2);
    // out(gamaRate, gama);
    int epsilon = Integer.parseInt(epsilonRate.toString(), 2);
    // out(epsilonRate, epsilon);
    out("part 1", "power consumption", gama * epsilon);

// part 2
    String oxygenGeneratorRating = "0";
    List<String> workLines = lines;
    for (int i = 0; i < lineLen; i++) {
      Counter counter = countBit(i, workLines);
      boolean keepOnce = (counter.hasEqualZerosAndOnce() || !counter.hasMoreZeros());
      int finalI = i;
      workLines = workLines.stream().filter(l -> l.charAt(finalI) == '1' && keepOnce || l.charAt(finalI) == '0' && !keepOnce).toList();
      if (workLines.size() == 1) {
        oxygenGeneratorRating = workLines.getFirst();
        break;
      }
    }
    out("oxygenGeneratorRating", oxygenGeneratorRating);

    String co2ScrubberRating = "0";
    workLines = lines;
    for (int i = 0; i < lineLen; i++) {
      Counter counter = countBit(i, workLines);
      boolean keepOnce = !(counter.hasEqualZerosAndOnce() || !counter.hasMoreZeros());
      int finalI = i;
      workLines = workLines.stream().filter(l -> l.charAt(finalI) == '1' && keepOnce || l.charAt(finalI) == '0' && !keepOnce).toList();
      if (workLines.size() == 1) {
        co2ScrubberRating = workLines.getFirst();
        break;
      }
    }
    out("co2ScrubberRating", co2ScrubberRating);
    out("part 2", "support ratting", Integer.parseInt(oxygenGeneratorRating, 2) * Integer.parseInt(co2ScrubberRating, 2));
  }

  private Counter countBit(int index, List<String> workLines) {
    int zeros = 0;
    int ones = 0;
    for (String line : workLines) {
      if (line.charAt(index) == '1')
        ones++;
      else zeros++;
    }
    return new Counter(zeros, ones);
  }

  public record Counter(int zeros, int ones) {
    public boolean hasMoreZeros() {
      return zeros > ones;
    }

    public boolean hasEqualZerosAndOnce() {
      return zeros == ones;
    }
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
