import java.util.Arrays;
import java.util.List;

public class Rule {
  public final AmountItem target;
  public final List<AmountItem> sources;
  public int depth = -1;

  public Rule(AmountItem target, List<AmountItem> sources) {
    this.target = target;
    this.sources = sources;
  }

  public boolean containsSource(String item) {
    for (AmountItem source : sources) {
      if (source.item.equals(item)) return true;
    }
    return false;
  }

  public static Rule parse(String input) {
    String[] split = input.trim().split("=>");
    AmountItem target = AmountItem.parse(split[1]);
    List<AmountItem> sources = Arrays.stream(split[0].trim()
        .split(",")).map(AmountItem::parse).toList();
    return new Rule(target, sources);
  }

  @Override
  public String toString() {
    return "Rule{" +
        sources +
        " => " + target +
        ", depth:" + depth +
        '}';
  }
}
