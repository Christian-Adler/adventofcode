package aoc;

import aoc.util.Pair;
import aoc.util.Vec;

import java.util.List;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    runForInput("./input_example_1.txt");
    // runForInput("./input_example_2.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    List<Pair<String, Integer>> actions = lines.stream().map(Task::parseLine).toList();
    Vec dir = Vec.RIGHT;
    Vec act = Vec.ZERO;

    // out(act, dir);
    for (Pair<String, Integer> pair : actions) {
      String action = pair.key();
      int val = pair.value();
      switch (action) {
        case "N" -> act = act.add(Vec.UP.multiply(val));
        case "S" -> act = act.add(Vec.DOWN.multiply(val));
        case "E" -> act = act.add(Vec.RIGHT.multiply(val));
        case "W" -> act = act.add(Vec.LEFT.multiply(val));
        case "L" -> {
          for (int i = 0; i < val / 90; i++) {
            dir = dir.rotate90DegToNew(true);
          }
        }
        case "R" -> {
          for (int i = 0; i < val / 90; i++) {
            dir = dir.rotate90DegToNew(false);
          }
        }
        case "F" -> act = act.add(dir.multiply(val));
      }
      // out(act, dir);
    }

    // out(act, dir);

    out("part 1: ", act.manhattanDistance());

    // part 2
    act = Vec.ZERO;
    Vec waypoint = new Vec(10, -1); // waypoint is always relative to the ship

    // out(act, dir, waypoint);
    for (Pair<String, Integer> pair : actions) {
      String action = pair.key();
      int val = pair.value();
      switch (action) {
        case "N" -> waypoint = waypoint.add(Vec.UP.multiply(val));
        case "S" -> waypoint = waypoint.add(Vec.DOWN.multiply(val));
        case "E" -> waypoint = waypoint.add(Vec.RIGHT.multiply(val));
        case "W" -> waypoint = waypoint.add(Vec.LEFT.multiply(val));
        case "L" -> {
          for (int i = 0; i < val / 90; i++) {
            waypoint = waypoint.rotate90DegToNew(true);
          }
        }
        case "R" -> {
          for (int i = 0; i < val / 90; i++) {
            waypoint = waypoint.rotate90DegToNew(false);
          }
        }
        case "F" -> act = act.add(waypoint.multiply(val));
      }
      // out(act, dir, waypoint);
    }

    // out(act, dir, waypoint);
    out("part 2: ", act.manhattanDistance());
  }

  private static Pair<String, Integer> parseLine(String line) {
    return new Pair<>(line.substring(0, 1), Integer.parseInt(line.substring(1)));
  }
}
