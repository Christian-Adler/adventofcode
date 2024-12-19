package aoc;

import aoc.util.Img;
import aoc.util.Util;

public class Task {

  public void init() {
  }

  public void addLine(String input) {
  }

  public void afterParse() throws Exception {
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
