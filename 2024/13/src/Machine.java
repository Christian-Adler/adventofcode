import java.util.Arrays;
import java.util.List;

public class Machine {
  Pos btnA = null;
  Pos btnB = null;
  Pos prize = null;


  public void input(String input) {
    if (input.contains("Button")) {
      List<Integer> parts = Arrays.stream(Util.cleanFrom(input.substring(9), " ", "X", "Y", "+").split(",")).mapToInt(Integer::parseInt).boxed().toList();
      Pos pos = new Pos(parts.getFirst(), parts.getLast());
      if (input.contains("A"))
        btnA = pos;
      else
        btnB = pos;
    } else if (input.contains("Prize")) {
      List<Integer> parts = Arrays.stream(Util.cleanFrom(input, "Prize:", " ", "X", "Y", "=").split(",")).mapToInt(Integer::parseInt).boxed().toList();
      prize = new Pos(parts.getFirst(), parts.getLast());
    } else
      throw new IllegalArgumentException("unexpected input:" + input);
  }

  public int calcMinTokens() {
    int minTokens = -2;
    int maxBtnPress = 100;

    Pos actPos = new Pos(0, 0);
    for (int btnAPressed = 0; btnAPressed <= maxBtnPress; btnAPressed++) {
      if (btnAPressed > 0)
        actPos.add(btnA);
      if (actPos.equals(prize)) {
        int tokens = btnAPressed * 3;
        if (minTokens < 0 || tokens < minTokens)
          minTokens = tokens;
      }
      if (actPos.x > prize.x || actPos.y > prize.y)
        break;

      Pos checkPos = actPos.copy();
      for (int btnBPressed = 0; btnBPressed <= maxBtnPress; btnBPressed++) {
        if (btnBPressed > 0)
          checkPos.add(btnB);
        if (checkPos.equals(prize)) {
          int tokens = btnAPressed * 3 + btnBPressed;
          if (minTokens < 0 || tokens < minTokens)
            minTokens = tokens;
        }
        if (checkPos.x > prize.x || checkPos.y > prize.y)
          break;
      }
    }
    return minTokens;
  }

  public long calcMinTokens2() {
    long px = prize.x + 10000000000000L;
    long py = prize.y + 10000000000000L;
    long minTokens = -2;

    /*  Gleichungssystem aufgeloest:
     * Px = Ax * v1 + Bx * v2
     * Py = Ay * v1 + By * v2
     *
     * => v2 = (Py - Ay * v1) / By
     * => v1 = (Px * By - Py * Bx) / (Ax * By - Bx * Ay)
     *
     * Nur ganzahlig loesen
     */

    long numerator = px * btnB.y - py * btnB.x;
    long denominator = btnA.x * btnB.y - btnB.x * btnA.y;
    if (denominator == 0)
      throw new IllegalArgumentException("div 0");
    if (numerator % denominator != 0)
      return minTokens;
    long v1 = numerator / denominator;

    numerator = py - btnA.y * v1;
    denominator = btnB.y;
    if (numerator % denominator != 0)
      return minTokens;
    long v2 = numerator / denominator;

    minTokens = v1 * 3 + v2;

    return minTokens;
  }

  @Override
  public String toString() {
    return
        "btnA=" + btnA +
            "\r\nbtnB=" + btnB +
            "\r\nprice=" + prize;
  }
}
