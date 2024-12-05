import java.util.Arrays;
import java.util.List;

public class PageOrderRule {
  public final int before;
  public final int after;

  public PageOrderRule(String input) {
    List<Integer> pages = Arrays.stream(input.split("\\|")).mapToInt(Integer::parseInt).boxed().toList();
    before = pages.getFirst();
    after = pages.getLast();
  }

  @Override
  public String toString() {
    return before + "|" + after;
  }
}
