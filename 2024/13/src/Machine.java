import java.util.Arrays;
import java.util.List;

public class Machine {
  Pos btnA = null;
  Pos btnB = null;
  Pos prize = null;
  int minTokens = -2;

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
    minTokens = getMinTokens(btnA, 3, btnB, 1);
    int tokens = getMinTokens(btnB, 1, btnA, 3);
    if (minTokens < 0 || tokens > 0 && tokens < minTokens)
      minTokens = tokens;
    return minTokens;
  }

  private int getMinTokens(Pos btn1, int factor1, Pos btn2, int factor2) {
    int minTokens = -1;

    int maxBtnPress = 100;

    Pos actPos = new Pos(0, 0);
    for (int btn1Pressed = 1; btn1Pressed <= maxBtnPress; btn1Pressed++) {
      actPos.add(btn1);
      if (actPos.equals(prize)) {
        int tokens = btn1Pressed * factor1;
        if (minTokens < 0 || tokens < minTokens)
          minTokens = tokens;
      }
      if (actPos.x > prize.x || actPos.y > prize.y)
        break;

      Pos checkPos = actPos.copy();
      for (int btn2Pressed = 1; btn2Pressed <= maxBtnPress; btn2Pressed++) {
        checkPos.add(btn2);
        if (checkPos.equals(prize)) {
          int tokens = btn1Pressed * factor1 + btn2Pressed * factor2;
          if (minTokens < 0 || tokens < minTokens)
            minTokens = tokens;
        }
        if (checkPos.x > prize.x || checkPos.y > prize.y)
          break;
      }
    }
    return minTokens;
  }

  @Override
  public String toString() {
    return
        "btnA=" + btnA +
            "\r\nbtnB=" + btnB +
            "\r\nprice=" + prize +
            "\r\nminTokens=" + minTokens;
  }
}
