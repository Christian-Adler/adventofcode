package aoc;

import aoc.technique.Technique;
import aoc.util.Util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Task {
  private List<Technique> techniques = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    techniques.add(Technique.parse(input));
  }

  public void afterParse(int cards) throws Exception {
    // out(techniques);

    ArrayList<Integer> deck = new ArrayList<>(IntStream.range(0, cards).boxed().toList());

    // out(deck);

    for (Technique technique : techniques) {
      technique.exec(deck);
      // out(deck);
    }

    if (cards < 2019) {
      out("part 1", deck.indexOf(4));
      out(deck);
      return;
    }

    out("part 1", deck.indexOf(2019));

    // part 2
    // adapting the solution from
    // https://github.com/SimonBaars/AdventOfCode-Java/blob/master/src/main/java/com/sbaars/adventofcode/year19/days/Day22.java
    // had not even a clou how to - don't fully understand that solution
    String cardAt = seekPosition(Technique.getAsBigInt(119315717514047L), Technique.getAsBigInt(101741582076661L),
        2020).toString();
    out("part 2", cardAt);
  }

  private BigInteger seekPosition(final BigInteger deckSize,
                                  final BigInteger timesShuffled,
                                  final int position) {
    final BigInteger[] calc = new BigInteger[]{Technique.getAsBigInt(1), Technique.getAsBigInt(0)};
    for (final Technique technique : techniques.reversed()) {
      technique.exec(calc, deckSize);
      for (int i = 0; i < calc.length; i++)
        calc[i] = calc[i].mod(deckSize);
    }
    final BigInteger pow = calc[0].modPow(timesShuffled, deckSize);
    return pow.multiply(Technique.getAsBigInt(position))
        .add(calc[1].multiply(pow.add(deckSize).subtract(Technique.getAsBigInt(1))).multiply(calc[0]
            .subtract(Technique.getAsBigInt(1)).modPow(deckSize.subtract(Technique.getAsBigInt(2)), deckSize)))
        .mod(deckSize);
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
