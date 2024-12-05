import java.util.*;


public class Update {
  private List<Integer> pages = new ArrayList<>();

  public Update(String input) {
    pages.addAll(Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).boxed().toList());
    if (pages.size() % 2 == 0)
      throw new RuntimeException("even page size!");
    Set<Integer> pagesSet = new HashSet<>(pages);
    if (pagesSet.size() != pages.size())
      throw new RuntimeException("duplicate pages!");
  }

  @Override
  public String toString() {
    return "Update{" +
        "pages=" + pages +
        '}';
  }

  public boolean hasCorrectOrder(List<PageOrderRule> rules) {
    for (int i = 0; i < pages.size(); i++) {
      int page = pages.get(i);

      List<PageOrderRule> beforeRules = rules.stream().filter(r -> r.before == page).toList();
      List<PageOrderRule> afterRules = rules.stream().filter(r -> r.after == page).toList();

      for (PageOrderRule beforeRule : beforeRules) {
        int afterIdx = pages.indexOf(beforeRule.after);
        if (afterIdx >= 0 && afterIdx < i)
          return false;
      }
      for (PageOrderRule afterRule : afterRules) {
        int beforeIdx = pages.indexOf(afterRule.before);
        if (beforeIdx >= 0 && beforeIdx > i)
          return false;
      }
    }
    return true;
  }

  public void correctOrder(List<PageOrderRule> rules) {
    pages.sort((p1, p2) -> {
      PageOrderRule rule = rules.stream().filter(r -> r.before == p1 && r.after == p2 || r.before == p2 && r.after == p1).findFirst().orElse(null);
      if (rule == null) return 0;

      if (rule.before == p1)
        return -1;
      return 1;
    });
  }

  public int getMiddlePageNo() {
    return pages.get((int) (double) (pages.size() / 2));
  }
}
