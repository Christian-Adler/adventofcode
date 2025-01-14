package aoc.technique;

import java.math.BigInteger;
import java.util.ArrayList;

public class DealIntoNew extends Technique {
  @Override
  public void exec(ArrayList<Integer> list) {
    ArrayList<Integer> tmp = new ArrayList<>(list);
    list.clear();
    list.addAll(tmp.reversed());
  }

  @Override
  public void exec(BigInteger[] input, BigInteger deckSize) {
    input[0] = input[0].multiply(getAsBigInt(-1));
    input[1] = input[1].add(getAsBigInt(1)).multiply(getAsBigInt(-1));
  }
}
