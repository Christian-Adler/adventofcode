package aoc.technique;

import java.math.BigInteger;
import java.util.ArrayList;

public class CutN extends Technique {
  private final int n;

  public CutN(int n) {
    this.n = n;
  }

  @Override
  public void exec(ArrayList<Integer> list) {
    ArrayList<Integer> tmp = new ArrayList<>(list);
    list.clear();
    if (n > 0) {
      list.addAll(tmp.subList(n, tmp.size()));
      list.addAll(tmp.subList(0, n));
    } else {
      list.addAll(tmp.subList(tmp.size() + n, tmp.size()));
      list.addAll(tmp.subList(0, tmp.size() + n));
    }
  }

  @Override
  public void exec(BigInteger[] input, BigInteger deckSize) {
    input[1] = input[1].add(getAsBigInt(n));
  }

  @Override
  public String toString() {
    return super.toString() + " (" + n + ')';
  }
}
