import java.util.Arrays;
import java.util.List;

public class Task {
  private Stone head = null;

  public void init() {
  }

  public void addLine(String input) {
    Stone prev = null;
    List<Long> numbers = Arrays.stream(input.split(" ")).mapToLong(Long::parseLong).boxed().toList();
    for (Long number : numbers) {
      Stone stone = new Stone(number);
      if (head == null)
        head = stone;
      if (prev != null) {
        stone.prev = prev;
        prev.next = stone;
      }
      prev = stone;
    }
  }

  public void afterParse() {
    // out(toString());
    int noBlinks = 25;
    for (int i = 1; i <= noBlinks; i++) {

      blink();
      // out(toString());
      // out(i, "stones:", countStones());
    }

    out("part 1", "stones:", countStones());
  }

  private long countStones() {
    long count = 0;
    Stone actStone = head;
    while (actStone != null) {
      count++;

      actStone = actStone.next;
    }
    return count;
  }

  private void blink() {
    Stone actStone = head;
    while (actStone != null) {
      long number = actStone.number;

      String strNumber = String.valueOf(number);
      if (number == 0)
        actStone.number = 1;
      else if (strNumber.length() % 2 == 0) {
        long n1 = Long.parseLong(strNumber.substring(0, strNumber.length() / 2));
        long n2 = Long.parseLong(strNumber.substring(strNumber.length() / 2));

        Stone prev = actStone.prev;
        Stone next = actStone.next;

        Stone s1 = new Stone(n1);
        s1.prev = prev;
        if (prev != null)
          prev.next = s1;
        else head = s1;

        Stone s2 = new Stone(n2);
        s2.prev = s1;
        s1.next = s2;
        s2.next = next;
        if (next != null) next.prev = s2;

        actStone = s2;
      } else
        actStone.number *= 2024;

      actStone = actStone.next;
    }
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    Stone actStone = head;
    while (actStone != null) {
      builder.append(actStone);
      actStone = actStone.next;
    }
    return builder.toString();
  }

}
