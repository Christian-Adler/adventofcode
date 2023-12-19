import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Task2 {
  ArrayList<Vec> vecMap = new ArrayList<>();
  Pos actPos = new Pos(0, 0);
  int minY = 0;
  int maxY = 0;
  int minX = 0;
  int maxX = 0;

  public void init() {
  }

  public void addLine(String input, boolean part1) {
    Pos dir;
    int steps;
    // Part 1
    if (part1) {
      String[] split = input.split("\\s+");
      String direction = split[0];
      dir = getDir(direction);
      steps = Integer.parseInt(split[1]);
    }
    // Part 2
    else {
      String hex = Util.cleanFrom(input.split("\\s+")[2], "(", ")", "#");
      String hexSteps = hex.substring(0, 5);
      String hexDir = hex.substring(5);
      dir = getDir2(hexDir);
      steps = Integer.parseInt(hexSteps, 16);
      if (Long.parseLong(hexSteps, 16) != steps)
        throw new IllegalStateException("int is not enough");
    }

    Vec vec = new Vec(actPos, dir, steps);
    vecMap.add(vec);
    actPos = vec.getEnd();
    if (actPos.x < minX) minX = actPos.x;
    if (actPos.x > maxX) maxX = actPos.x;
    if (actPos.y < minY) minY = actPos.y;
    if (actPos.y > maxY) maxY = actPos.y;
  }

  private static Pos getDir(String direction) {
    return switch (direction) {
      case "U" -> Pos.UP;
      case "D" -> Pos.DOWN;
      case "L" -> Pos.LEFT;
      default -> Pos.RIGHT;
    };
  }

  private static Pos getDir2(String direction) {
    return switch (direction) {
      case "3" -> Pos.UP;
      case "1" -> Pos.DOWN;
      case "2" -> Pos.LEFT;
      default -> Pos.RIGHT;
    };
  }

  public void afterParse() {
    digLoopInside();
  }


  private void digLoopInside() {

    // dig loop inside

    long countDigs = 0;

    for (int y = minY; y <= maxY; y++) {
      out(y - minY, "of", (maxY - minY));
      Set<Integer> xVals = new HashSet<>();
      for (Vec vec : vecMap) {
        xVals.addAll(vec.getXValues(y));
      }
      ArrayList<Integer> xValues = new ArrayList<>(xVals);

      Set<Integer> xValsAbove = new HashSet<>();
      for (Vec vec : vecMap) {
        xValsAbove.addAll(vec.getXValues(y - 1));
      }
      Set<Integer> xValsBelow = new HashSet<>();
      for (Vec vec : vecMap) {
        xValsBelow.addAll(vec.getXValues(y + 1));
      }

      Collections.sort(xValues);

      boolean isInsideLoop = false;
      boolean isBorderInsideLoop = false;
      boolean isBorderAbo = false;
      boolean isBorderBel = false;
      int insideLoopStart = 0;
      int prevXValue = Integer.MIN_VALUE;
      for (Integer xValue : xValues) {
        int prevX = prevXValue;
        prevXValue = xValue;

//        boolean borderAbove = mapContains(xValue, y - 1);
//        boolean borderBelow = mapContains(xValue, y + 1);
        boolean borderAbove = xValsAbove.contains(xValue);
        boolean borderBelow = xValsBelow.contains(xValue);

        if (!isInsideLoop) {
          isInsideLoop = true;
          isBorderInsideLoop = false;
          insideLoopStart = xValue;
          isBorderAbo = borderAbove;
          isBorderBel = borderBelow;
        } else { // inside loop
          boolean isContinuousBorder = prevX == xValue - 1;

          //  v
          // ### keine Kurve nach oben oder unten - dann weiter
          if (!borderAbove && !borderBelow)
            continue;

          // #   #           v
          // #####  oder #####
          //     ^       #   #
          //
          if (isContinuousBorder && (isBorderAbo && borderAbove || isBorderBel && borderBelow)) {
            // sind wir noch innerhalb?
            if (isBorderInsideLoop) {
              isBorderAbo = false;
              isBorderBel = false;
              continue;
            } else {
              countDigs = addDigsDiff(insideLoopStart, xValue, countDigs);
              isBorderAbo = false;
              isBorderBel = false;
              isInsideLoop = false;
              isBorderInsideLoop = false;
              continue;
            }
          }

          // #   v           #
          // #####  oder #####
          //     #       #   ^
          //
          if (isContinuousBorder && (isBorderAbo && borderBelow || isBorderBel && borderAbove)) {
            // Kurve erreicht - Range ist fertig
            if (isBorderInsideLoop) {
              countDigs = addDigsDiff(insideLoopStart, xValue, countDigs);
              isBorderAbo = false;
              isBorderBel = false;
              isInsideLoop = false;
              isBorderInsideLoop = false;
              continue;
            } else {
              isBorderAbo = false;
              isBorderBel = false;
              isBorderInsideLoop = false;
              continue;
            }
          }

          // #
          // #
          // #
          //
          if (borderAbove && borderBelow) {
            countDigs = addDigsDiff(insideLoopStart, xValue, countDigs);
            isBorderAbo = false;
            isBorderBel = false;
            isInsideLoop = false;
            isBorderInsideLoop = false;
            continue;
          }

          // #        v
          // ### oder ###
          // ^        #
          //
          // Auf Grenze, aber immer noch innerhalb loop
          // Merken, woher die Grenze kam (oben oder unten)
          // und dass wir innerhalb des Loop auf die Grenze gestossen sind.
          if (borderAbove) {
            isBorderAbo = true;
            isBorderInsideLoop = true;
          } else
            isBorderAbo = false;
          if (borderBelow) {
            isBorderBel = true;
            isBorderInsideLoop = true;
          } else
            isBorderBel = false;
        }
      }
    }

    out("map size after dig inside loop", countDigs);
//    out(toStringConsole());
  }

  private long addDigsDiff(int insideLoopStart, int xValue, long countDigs) {
    int diff = xValue + 1 - insideLoopStart;
    out("add diff", diff);
    countDigs += diff;
    return countDigs;
  }

  private boolean mapContains(int x, int y) {
    Pos p = new Pos(x, y);
    for (Vec vec : vecMap) {
      if (vec.isOnVec(p))
        return true;
    }
    return false;
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
