import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Task {

  Map<Integer, Map<String, Integer>> mapping = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    ArrayList<String> characters = Util.str2List(input);
    for (int i = 0; i < input.length(); i++) {
      String c = characters.get(i);
      Map<String, Integer> innerMap = mapping.computeIfAbsent(i, k -> new HashMap<>());
      innerMap.compute(c, (key, value) -> value == null ? 1 : (value + 1));
    }
  }

  public void afterParse(boolean part2) {
    ArrayList<Integer> idxs = new ArrayList<>(mapping.keySet());
    Collections.sort(idxs);
    out(idxs);

    StringBuilder password = new StringBuilder();
    for (Integer idx : idxs) {
      Map<String, Integer> innerMap = mapping.get(idx);
      String c = innerMap.entrySet().stream()
          .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
          .map(Entry::getKey).findFirst().orElse("INVALID");
      if (part2) {
        c = innerMap.entrySet().stream()
            .sorted(Comparator.comparingInt(Entry::getValue))
            .map(Entry::getKey).findFirst().orElse("INVALID");
      }
      password.append(c);
    }
    out("pw:", part2 ? "(part 2)" : "(part 1)", password.toString());
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return "";
  }
}
