package aoc.util;

import java.util.Collection;

public class UnicodeCharacters {
  public static final String FULL_BLOCK = "█"; // https://www.compart.com/en/unicode/block/U+2580
  public static final String BLACK_SQUARE = "■"; // https://www.compart.com/en/unicode/block/U+25A0
  // https://www.compart.com/en/unicode/block/U+2500
  public static final String TOP = "╵";
  public static final String BOTTOM = "╷";
  public static final String LEFT = "╴";
  public static final String RIGHT = "╶";
  public static final String TOP_BOTTOM = "│";
  public static final String LEFT_RIGHT = "─";
  public static final String BOTTOM_LEFT = "┐";
  public static final String BOTTOM_RIGHT = "┌";
  public static final String TOP_RIGHT = "└";
  public static final String TOP_LEFT = "┘";
  public static final String TOP_BOTTOM_LEFT = "┤";
  public static final String TOP_BOTTOM_RIGHT = "├";
  public static final String TOP_LEFT_RIGHT = "┴";
  public static final String BOTTOM_LEFT_RIGHT = "┬";
  public static final String TOP_BOTTOM_LEFT_RIGHT = "┼";

  public static String toBox(Vec v, Collection<Vec> neighbours) {
    if (neighbours.contains(v.add(Vec.UP))) {
      if (neighbours.contains(v.add(Vec.DOWN))) {
        if (neighbours.contains(v.add(Vec.LEFT))) {
          if (neighbours.contains(v.add(Vec.RIGHT)))
            return TOP_BOTTOM_LEFT_RIGHT;
          else
            return TOP_BOTTOM_LEFT;

        } else if (neighbours.contains(v.add(Vec.RIGHT)))
          return TOP_BOTTOM_RIGHT;
        else
          return TOP_BOTTOM;

      } else if (neighbours.contains(v.add(Vec.LEFT))) {
        if (neighbours.contains(v.add(Vec.RIGHT)))
          return TOP_LEFT_RIGHT;
        else
          return TOP_LEFT;

      } else if (neighbours.contains(v.add(Vec.RIGHT)))
        return TOP_RIGHT;
      else
        return TOP;

    } else if (neighbours.contains(v.add(Vec.DOWN))) {
      if (neighbours.contains(v.add(Vec.LEFT))) {
        if (neighbours.contains(v.add(Vec.RIGHT)))
          return BOTTOM_LEFT_RIGHT;
        else
          return BOTTOM_LEFT;

      } else if (neighbours.contains(v.add(Vec.RIGHT)))
        return BOTTOM_RIGHT;
      else
        return BOTTOM;

    } else if (neighbours.contains(v.add(Vec.LEFT))) {
      if (neighbours.contains(v.add(Vec.RIGHT)))
        return LEFT_RIGHT;
      else
        return LEFT;

    } else if (neighbours.contains(v.add(Vec.RIGHT)))
      return RIGHT;

    return " ";
  }
}
