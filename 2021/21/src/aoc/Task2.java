package aoc;

import aoc.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Task2 {
  private final Player p1 = new Player(21);
  private final Player p2 = new Player(21);

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

    AtomicLong p1Wins = new AtomicLong();
    AtomicLong p2Wins = new AtomicLong();

    roll(p1, p2, 1, 1, p1Wins, p2Wins);

    // out(p1Wins);
    // out(p2Wins);

    out("part 2", Math.max(p1Wins.get(), p2Wins.get()));
  }


  /*
   * for 3 rolls there could be summed eyes from 3 to 9
   * these numbers could occur in several ways (universes)
   *            Sum Eyes
   *       / 1   3
   *   / 1 - 2   4
   *       \ 3   5
   *       / 1   4
   * 1 - 2 - 2   5
   *       \ 3   6
   * ...
   * 3
   *   \ 3
   *       \ 3   9
   *
   */

  private static final Map<Integer, Long> sumEyes2Universes = new HashMap<>();

  static {
    sumEyes2Universes.put(3, 1L);
    sumEyes2Universes.put(4, 3L);
    sumEyes2Universes.put(5, 6L);
    sumEyes2Universes.put(6, 7L);
    sumEyes2Universes.put(7, 6L);
    sumEyes2Universes.put(8, 3L);
    sumEyes2Universes.put(9, 1L);
  }


  private void roll(Player actPlayer, Player nextPlayer, long actPlayerUniverses, long nextPlayerUniverses, AtomicLong actPlayerWins, AtomicLong nextPlayerWins) {
    for (int rolled = 3; rolled <= 9; rolled++) {
      long universes = sumEyes2Universes.get(rolled);
      Player player = actPlayer.copy();
      if (player.move(rolled))
        actPlayerWins.addAndGet(actPlayerUniverses * universes);
      else
        roll(nextPlayer, player, nextPlayerUniverses * universes, actPlayerUniverses * universes, nextPlayerWins, actPlayerWins);
    }
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
