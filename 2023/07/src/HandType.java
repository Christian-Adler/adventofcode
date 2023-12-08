import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum HandType implements Comparable<HandType> {
  FIVE_OF_A_KIND,
  FOUR_OF_A_KIND,
  FULL_HOUSE,
  THREE_OF_A_KIND,
  TWO_PAIRS,
  ONE_PAIR,
  HIGH_CARD;

  public static HandType determineHandType(String input, boolean part2) {
    long countJokers = part2 ? countChar(input, 'J') : 0;
    Map<String, Long> charCount = new HashMap<>();
    for (String availableCard : Cards.availableCardsOrder) {
      if (part2 && availableCard.equals("J")) continue;
      charCount.put(availableCard, countChar(input, availableCard.charAt(0)));
    }
    ArrayList<Long> counts = new ArrayList<>(charCount.values());
    Collections.sort(counts);
    Collections.reverse(counts);
    Long first = counts.getFirst();

    if (part2)
      first = first + countJokers;

    Long second = counts.get(1);
    if (first == 5)
      return FIVE_OF_A_KIND;
    if (first == 4)
      return FOUR_OF_A_KIND;
    if (first == 3) {
      if (second == 2)
        return FULL_HOUSE;
      return THREE_OF_A_KIND;
    }
    if (first == 2) {
      if (second == 2)
        return TWO_PAIRS;
      return ONE_PAIR;
    }
    return HIGH_CARD;
  }

  private static long countChar(String input, char c) {
    return input.chars().filter(ch -> ch == c).count();
  }
}
