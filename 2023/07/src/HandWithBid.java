import java.util.Comparator;
import java.util.Objects;

public final class HandWithBid {
  private final String hand;
  private final long bid;
  private final HandType handType;
  private final HandType handType2;

  public HandWithBid(String hand, long bid) {
    this.hand = hand;
    this.bid = bid;
    handType = HandType.determineHandType(hand, false);
    handType2 = HandType.determineHandType(hand, true);
  }

  public long bid() {
    return bid;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (HandWithBid) obj;
    return Objects.equals(this.hand, that.hand) &&
        this.bid == that.bid;
  }

  @Override
  public int hashCode() {
    return Objects.hash(hand, bid);
  }

  @Override
  public String toString() {
    return "\r\nHandWithBid[" +
        "hand=" + hand + ", " +
        "handType=" + handType + ", " +
        "handType2=" + handType2 + ", " +
        "bid=" + bid + ']';
  }


  public static Comparator<HandWithBid> getHandWithBidComparator() {
    return (o1, o2) -> {
      int result = o1.handType.compareTo(o2.handType);
      if (result != 0)
        return result;

      for (int i = 0; i < 5; i++) {
        int idx = Cards.availableCardsOrder.indexOf(String.valueOf(o1.hand.charAt(i)));
        int idx2 = Cards.availableCardsOrder.indexOf(String.valueOf(o2.hand.charAt(i)));
        result = Integer.compare(idx, idx2);
        if (result != 0)
          return result;
      }

      return 0;
    };
  }

  public static Comparator<HandWithBid> getHandWithBidComparatorPart2() {
    return (o1, o2) -> {
      int result = o1.handType2.compareTo(o2.handType2);
      if (result != 0)
        return result;

      for (int i = 0; i < 5; i++) {
        int idx = Cards.availableCardsOrderPart2.indexOf(String.valueOf(o1.hand.charAt(i)));
        int idx2 = Cards.availableCardsOrderPart2.indexOf(String.valueOf(o2.hand.charAt(i)));
        result = Integer.compare(idx, idx2);
        if (result != 0)
          return result;
      }

      return 0;
    };
  }
}
