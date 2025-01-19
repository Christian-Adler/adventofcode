package aoc;

import aoc.util.Range;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    Set<Long> seats = lines.stream().mapToLong(this::calcSeatId).sorted().boxed().collect(Collectors.toCollection(LinkedHashSet::new));
    long maxSeatId = seats.stream().mapToLong(value -> value).max().orElseThrow();
    out("part 1", maxSeatId);

    long missing = -1;
    long prev = -1;
    for (Long seat : seats) {
      if (prev >= 0) {
        if (prev + 1 != seat) {
          missing = prev + 1;
          break;
        }
      }
      prev = seat;
    }
    out("part 2", missing);
  }


  private long calcSeatId(String seat) {
    Range range = new Range(0, 127);
    for (int i = 0; i < 7; i++) {
      char fb = seat.charAt(i);
      if (fb == 'F')
        range = new Range(range.from(), range.to() - range.size() / 2);
      else
        range = new Range(range.from() + range.size() / 2, range.to());
    }
    long row = range.from();

    range = new Range(0, 7);
    for (int i = 7; i < 10; i++) {
      char fb = seat.charAt(i);
      if (fb == 'L')
        range = new Range(range.from(), range.to() - range.size() / 2);
      else
        range = new Range(range.from() + range.size() / 2, range.to());
    }
    long column = range.from();

    return row * 8 + column;
  }
}
