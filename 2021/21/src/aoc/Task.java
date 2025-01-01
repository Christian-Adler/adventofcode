package aoc;

import aoc.dice.DeterministicDice;
import aoc.dice.Dice;
import aoc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Task {
  private final Player p1 = new Player(1000);
  private final Player p2 = new Player(1000);

  public void init() {
  }

  public void addLine(String input) {
    if (input.contains("Player 1"))
      p1.pos = Integer.parseInt(Util.cleanFrom(input, "Player 1 starting position: ")) - 1; // -1 to use 0-9 instead of 1-10
    else
      p2.pos = Integer.parseInt(Util.cleanFrom(input, "Player 2 starting position: ")) - 1;
  }

  public void afterParse() throws Exception {
    out(p1, p2);

    Dice dice = new DeterministicDice();

    List<Player> players = new ArrayList<>(List.of(p1, p2));

    while (true) {
      Player actPlayer = players.removeFirst();

      boolean wins = actPlayer.move(dice.roll() + dice.roll() + dice.roll());

      if (wins) {
        Player looser = players.removeFirst();
        out(looser, dice.rollsCount);
        out("part 1", looser.score * dice.rollsCount);
        break;
      }

      players.add(actPlayer);
    }

  }

  public void out(Object... str) {
    Util.out(str);
  }
}
