package aoc;

import aoc.util.Img;

import java.util.List;

public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    runForInput("./input_example_1.txt");
    // runForInput("./input.txt");
  }

  public String part1(List<String> lines) throws Exception {
    return null;
  }

  public String part2(List<String> lines) throws Exception {
    return null;
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
