import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {

  private final Map<Pos, String> map = new HashMap<>();
  private int maxY = -1;
  private int maxX = 0;
  private final String search = "XMAS";
  private final List<String> allowed = Util.str2List(search);

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    maxX = input.length() - 1;
    ArrayList<String> letters = Util.str2List(input);
    for (int i = 0; i < letters.size(); i++) {
      String letter = letters.get(i);
      if (allowed.contains(letter))
        map.put(new Pos(i, maxY), letter);
    }
  }

  public void afterParse() {
    // printMap();

    long count = 0;
    long count2 = 0;
    for (int y = 0; y <= maxY; y++) {
      for (int x = 0; x <= maxX; x++) {
        Pos pos = new Pos(x, y);
        String letter = map.get(pos);
        if ("X".equals(letter)) {
          count += countXmasFromPos(pos);
        } else if ("A".equals(letter)) {
          count2 += countMasFromPos(pos);
        }
      }
    }

    out("Part 1", "count", count);
    out("Part 2", "count", count2);
  }

  private long countXmasFromPos(Pos pos) {
    List<String> toFind = allowed.subList(1, allowed.size());
    // alle 8 Richtungen durchgehen
    long count = 0;
    for (Pos direction : Pos.adjacent8) {
      boolean found = true;
      Pos actPos = pos.copy();
      for (String letter : toFind) {
        actPos.add(direction);
        String letterAtPos = map.get(actPos);
        if (letterAtPos == null) {
          found = false;
          break;
        }
        if (!letter.equals(letterAtPos)) {
          found = false;
          break;
        }
      }
      if (found)
        count++;
    }

    return count;
  }

  private long countMasFromPos(Pos pos) {
    long count = 0;
    for (Pos direction : Pos.adjacentDiagonal) {
      Pos actPos = pos.addToNew(direction);

      String letterAtActPos = map.get(actPos);
      if (letterAtActPos == null)
        return 0;

      if (!"M".equals(letterAtActPos))
        continue;

      Pos secondPos = pos.addToNew(direction.multToNew(-1));
      String letterAtSecondPos = map.get(secondPos);
      if (letterAtSecondPos == null)
        return 0;

      if (!"S".equals(letterAtSecondPos))
        continue;

      count++;
    }

    return count == 2 ? 1 : 0;
  }

  private void printMap() {
    StringBuilder builder = new StringBuilder();
    for (int y = 0; y <= maxY; y++) {
      for (int x = 0; x <= maxX; x++) {
        String letter = map.get(new Pos(x, y));
        if (letter != null) builder.append(letter);
        else builder.append(".");
      }
      builder.append("\r\n");
    }
    out(builder.toString());
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
