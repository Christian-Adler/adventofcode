package aoc;

import aoc.util.Img;
import aoc.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Task {
  private final List<Integer> randomNumbers = new ArrayList<>();
  private Board actBoard = null;
  private final List<Board> boards = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    if (randomNumbers.isEmpty())
      randomNumbers.addAll(Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).boxed().toList());
    else if (input.isEmpty()) {
      actBoard = new Board();
      boards.add(actBoard);
    } else {
      actBoard.addNumbers(input);
    }
  }

  public void afterParse() throws Exception {
    boolean foundFirstWinningBoard = false;
    for (Integer randomNo : randomNumbers) {
      Iterator<Board> it = boards.iterator();
      while (it.hasNext()) {
        Board board = it.next();
        if (board.randomNumber(randomNo)) {
          // out("fin at ", randomNo);
          if (!foundFirstWinningBoard) {
            foundFirstWinningBoard = true;
            out("part 1", "finished board score", board.score(randomNo));
          }
          it.remove();
          if (boards.isEmpty()) {
            out("part 2", "finished last board score", board.score(randomNo));
            return;
          }
        }

      }
    }
    // out(boards);
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return toStringConsole();
  }

  public void toBmp() throws Exception {
    Img img = new Img();
    img.writeBitmapAged();
  }

  public String toStringConsole() {
    Img img = new Img();
    return img.toConsoleString();
  }
}
