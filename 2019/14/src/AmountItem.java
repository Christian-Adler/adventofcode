public class AmountItem {
  public long amount = 0;
  public final String item;

  public AmountItem(String item, long amount) {
    this.item = item;
    this.amount = amount;
  }

  public AmountItem copy() {
    return new AmountItem(item, amount);
  }

  public static AmountItem parse(String string) {
    String[] split = string.trim().split(" ");
    return new AmountItem(split[1].trim(), Long.parseLong(split[0].trim()));
  }

  @Override
  public String toString() {
    return "{" + amount + " " + item + '}';
  }
}
