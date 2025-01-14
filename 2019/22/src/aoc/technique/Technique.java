package aoc.technique;

import aoc.util.Util;

import java.math.BigInteger;
import java.util.ArrayList;

public abstract class Technique {
  public abstract void exec(ArrayList<Integer> list);

  public abstract void exec(final BigInteger[] input, final BigInteger deckSize);

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }

  private static final String dealWithIncrement = "deal with increment";
  private static final String cutN = "cut";

  public static Technique parse(String input) {
    if (input.startsWith(dealWithIncrement))
      return new IncrementN(Integer.parseInt(Util.cleanFrom(input, dealWithIncrement, " ")));
    if (input.startsWith(cutN))
      return new CutN(Integer.parseInt(Util.cleanFrom(input, cutN, " ")));
    return new DealIntoNew();
  }

  public static BigInteger getAsBigInt(final long n) {
    return new BigInteger(Long.toString(n));
  }
}
