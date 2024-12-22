package aoc;

import aoc.util.Util;
import aoc.util.Vec;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
  private final Set<Vec> marked = new HashSet<>();
  private final Map<Integer, Vec> numberPositions = new HashMap<>();

  private final static List<List<Vec>> finishPossibilities = new ArrayList<>();

  static {
    for (int x = 0; x < 5; x++) {
      List<Vec> list = new ArrayList<>();
      for (int y = 0; y < 5; y++) {
        list.add(new Vec(x, y));
      }
      finishPossibilities.add(list);
    }
    for (int y = 0; y < 5; y++) {
      List<Vec> list = new ArrayList<>();
      for (int x = 0; x < 5; x++) {
        list.add(new Vec(x, y));
      }
      finishPossibilities.add(list);
    }
  }

  public void addNumbers(String input) {
    int y = (numberPositions.size() / 5);
    List<Integer> ints = Arrays.stream(input.split(" ")).filter(s -> !s.trim().isEmpty()).mapToInt(Integer::parseInt).boxed().toList();
    for (int x = 0; x < ints.size(); x++) {
      numberPositions.put(ints.get(x), new Vec(x, y));
    }
  }

  public boolean randomNumber(int no) {
    Vec pos = numberPositions.get(no);
    if (pos != null)
      marked.add(pos);
    return checkForFinished();
  }

  public boolean checkForFinished() {
    for (List<Vec> finishPossibility : finishPossibilities) {
      if (marked.containsAll(finishPossibility))
        return true;
    }
    return false;
  }

  public int score(int mult) {
    int sum = 0;
    for (Map.Entry<Integer, Vec> entry : numberPositions.entrySet()) {
      if (!marked.contains(entry.getValue()))
        sum += entry.getKey();
    }
    return sum * mult;
  }

  @Override
  public String toString() {
    Map<Vec, Integer> inverted = invertMapUsingMapper(numberPositions);
    StringBuilder builder = new StringBuilder("\r\n");
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        Vec pos = new Vec(x, y);
        if (marked.contains(pos))
          builder.append("(").append(Util.leftPad(inverted.getOrDefault(pos, -1), 2, " ")).append(")");
        else
          builder.append(" ").append(Util.leftPad(inverted.getOrDefault(pos, -1), 2, " ")).append(" ");
        if (x < 4)
          builder.append(" ");
      }
      builder.append("\r\n");
    }
    return builder.toString();
  }

  private static <K, V> Map<V, K> invertMapUsingMapper(Map<K, V> sourceMap) {
    return sourceMap.entrySet()
        .stream().collect(
            Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (oldValue, newValue) -> oldValue)
        );
  }
}
