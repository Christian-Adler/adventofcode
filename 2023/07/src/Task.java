import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task {
  List<HandWithBid> hands = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    String[] split = input.trim().split("\\s+");
    hands.add(new HandWithBid(split[0], Long.parseLong(split[1])));
  }

  public void afterParse() {
//    out(hands);
    hands.sort(HandWithBid.getHandWithBidComparator());
    Collections.reverse(hands);
//    out(hands);

    long sum = 0;
    for (int i = 0; i < hands.size(); i++) {
      HandWithBid hand = hands.get(i);
      sum += (i + 1) * hand.bid();
    }

    out("Part 1", sum);

//    out(hands);
    hands.sort(HandWithBid.getHandWithBidComparatorPart2());
    Collections.reverse(hands);
//    out(hands);

    sum = 0;
    for (int i = 0; i < hands.size(); i++) {
      HandWithBid hand = hands.get(i);
      sum += (i + 1) * hand.bid();
    }

    out("Part 2", sum);
  }


  public void out(Object... str) {
    Util.out(str);
  }


}
