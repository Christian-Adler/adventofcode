package aoc;

import aoc.util.Img;
import aoc.util.Util;

public class Task {

  public void init() {
  }

  public void addLine(String input) {
    String binary = hex2bin(input);
    Packet packet = Packet.eval(binary);
    out("part 1", "sum version numbers", packet.sumVersionNos());
    out("part 2", "calculated", packet.calc());
  }

  public void afterParse() throws Exception {
  }

  private static String hex2bin(String hex) {
    // for short values ;)
    // return Long.toBinaryString(Long.parseLong(hex, 16));
    StringBuilder builder = new StringBuilder();
    for (String s : Util.str2List(hex)) {
      builder.append(Util.leftPad(Integer.toBinaryString(Integer.parseInt(s, 16)), 4, "0"));
    }
    return builder.toString();
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
