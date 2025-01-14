package aoc.technique;

import java.math.BigInteger;
import java.util.ArrayList;

public class IncrementN extends Technique {
  private final int n;

  public IncrementN(int n) {
    this.n = n;
  }

  @Override
  public void exec(ArrayList<Integer> list) {
    ArrayList<Integer> tmp = new ArrayList<>(list);

    int count = 0;
    while (count < tmp.size()) {
      int idx = (count * n) % tmp.size();
      list.set(idx, tmp.get(count));
      count++;
    }
  }

  @Override
  public void exec(BigInteger[] input, BigInteger deckSize) {
    final BigInteger p = getAsBigInt(n).modPow(deckSize.subtract(getAsBigInt(2)), deckSize);
    for (int i = 0; i < input.length; i++)
      input[i] = input[i].multiply(p);
  }

  @Override
  public String toString() {
    return super.toString() + " (" + n + ')';
  }
}
