package aoc.dice;

public abstract class Dice {
  public int rollsCount = 0;

  public int roll() {
    rollsCount++;
    return doRoll();
  }

  protected abstract int doRoll();
}
