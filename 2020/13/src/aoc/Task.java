package aoc;

import aoc.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    runForInput("./input_example_1.txt", 0L);
    runForInput("./input_example_6.txt", 1000000000L);
    runForInput("./input.txt", 100000000000000L);
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    part1(lines);

    // part 2
    part2(lines);
  }

  private static void part1(List<String> lines) {
    long departure = Long.parseLong(lines.getFirst());
    List<Long> busIds = Arrays.stream(lines.get(1).split(",")).filter(s -> !s.equals("x")).mapToLong(Long::parseLong).boxed().sorted().toList();

    long minWaitTime = Long.MAX_VALUE;
    long minWaitTimeBusId = -1;

    for (Long busId : busIds) {
      long fullCycles = departure / busId;
      if (departure % busId == 0) {
        minWaitTime = 0;
        minWaitTimeBusId = busId;
        out("found wait 0");
        break;
      }
      long waitTime = (fullCycles + 1) * busId - departure;
      if (waitTime < minWaitTime) {
        minWaitTime = waitTime;
        minWaitTimeBusId = busId;
      }
    }

    out("part 1: ", minWaitTime * minWaitTimeBusId);
  }

  private static void part2(List<String> lines) {
    List<Pair<Long, Integer>> busIdWithIdx = new ArrayList<>();
    String[] split = lines.get(1).split(",");
    for (int i = 0; i < split.length; i++) {
      if (!split[i].equals("x")) {
        busIdWithIdx.add(new Pair<>(Long.parseLong(split[i]), i));
      }
    }

    long t = 0;

    Pair<Long, Integer> first = busIdWithIdx.removeFirst();
    // first index(=delta) irrelevant (=0)
    long step = first.key(); // busId = step width -> all t % busId(first) =0

    // for the rest include t by steps until (t+delta) % busId(...) ==0
    // then we found the next step width for which all so far busIds: step % busId ==0
    for (Pair<Long, Integer> idWithIdx : busIdWithIdx) {
      int delta = idWithIdx.value();
      long busId = idWithIdx.key();

      while ((t + delta) % busId != 0)
        t += step;
      step *= busId;
    }

    out("part 2: ", t);
  }

}
