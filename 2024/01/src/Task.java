import java.util.*;

public class Task {
  private final List<Integer> l1 = new ArrayList<>();
  private final List<Integer> l2 = new ArrayList<>();


  public void init() {
  }

  public void addLine(String input) {
    int[] vals = Arrays.stream(Util.cleanFrom(input.replaceFirst(" ", ","), " ").split(",")).mapToInt(Integer::parseInt).toArray();
    l1.add(vals[0]);
    l2.add(vals[1]);
  }

  public void afterParse() {
    Collections.sort(l1);
    Collections.sort(l2);

    // part 1
    long dist = 0;

    for (int i = 0; i < l1.size(); i++) {
      Integer val1 = l1.get(i);
      Integer val2 = l2.get(i);

      long add = Math.abs(val1 - val2);
      dist += add;
    }
    out("Part 1", "dist", dist);

    // part 2
    long similarityScore = 0;

    for (Integer leftVal : l1) {
      long add = leftVal * l2.stream().filter(v -> Objects.equals(v, leftVal)).count();
      similarityScore += add;
    }
    out("Part 2", "similarity score", similarityScore);
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
