import java.util.ArrayList;
import java.util.List;

public class Pattern {
  private static final int[] basePattern = new int[]{0, 1, 0, -1};

  private final List<Integer> pattern;

  public Pattern(int num) {
    pattern = new ArrayList<>();
    for (int i : basePattern) {
      for (int j = 0; j < num; j++) {
        pattern.add(i);
      }
    }
    pattern.add(pattern.removeFirst());
  }

  public int getPatternValAtIdx(int idx) {
    return pattern.get(idx % pattern.size());
  }

  @Override
  public String toString() {
    return "Pattern{" +
        "pattern=" + pattern +
        '}';
  }
}
