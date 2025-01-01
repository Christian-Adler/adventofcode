package aoc.dice;

public class DeterministicDice extends Dice {
  private int actPoints = 99;

  @Override
  protected int doRoll() {
    actPoints = (actPoints + 1) % 100;
    return actPoints + 1;
  }
}
