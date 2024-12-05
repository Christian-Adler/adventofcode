import java.util.ArrayList;
import java.util.List;

public class Task {

  private final List<PageOrderRule> rules = new ArrayList<>();
  private final List<Update> updates = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    if (input.trim().isEmpty()) return;
    if (input.contains("|"))
      rules.add(new PageOrderRule(input));
    else
      updates
          .add(new Update(input));
  }

  public void afterParse() {
    // out(rules);
    // out(updates);

    long sumCorrectUpdates = 0;
    List<Update> incorrectlyOrderedUpdates = new ArrayList<>();
    for (Update update : updates) {
      if (update.hasCorrectOrder(rules))
        sumCorrectUpdates += update.getMiddlePageNo();
      else
        incorrectlyOrderedUpdates.add(update);
    }

    out("Part 1", "sum", sumCorrectUpdates);

    long sumCorrectedUpdates = 0;
    for (Update incorrectlyOrderedUpdate : incorrectlyOrderedUpdates) {
      incorrectlyOrderedUpdate.correctOrder(rules);
      // out(incorrectlyOrderedUpdate);
      sumCorrectedUpdates += incorrectlyOrderedUpdate.getMiddlePageNo();
    }

    out("Part 2", "sum", sumCorrectedUpdates);
  }

  public void out(Object... str) {
    Util.out(str);
  }


}
