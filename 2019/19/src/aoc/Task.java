package aoc;

import aoc.computer.Computer;
import aoc.util.Img;
import aoc.util.Range;
import aoc.util.Util;
import aoc.util.Vec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class Task {
  Map<Long, Long> program;

  public void init() {
  }

  public void addLine(String input) {
    program = Computer.parseProgram(input);

  }

  public void afterParse() throws Exception {
    List<Range> ranges = new ArrayList<>();

    boolean solvedPart1 = false;
    boolean solvedPart2 = false;

    int squareSize = 100;

    Vec squareStart = null;

    int xStart = 0;
    int xEnd = 100;
    int y = -1;
    while (!solvedPart1 || !solvedPart2) {
      y++;

      // part 1
      if (y == 50) {
        long count = 0;
        for (Range range : ranges) {
          if (range != null)
            count += range.size();
        }
        out("part 1", count);

        solvedPart1 = true;
        // break;
      }

      int xMin = -1;
      int xMax = -1;
      int actX = xStart;
      while (true) {
        if (actX > xEnd) break;

        boolean pulled = isPulled(actX, y);
        if (pulled) {
          xMax = actX;
          if (xMin < 0) xMin = actX;
        } else {
          if (xMin >= 0)
            break;
        }
        actX++;
      }
      if (xMax >= 0) {
        Range range = new Range(xMin, xMax);
        ranges.add(range);
        xStart = xMin;
        xEnd = xMax + 10;

        // part 2
        if (range.size() >= squareSize && ranges.size() >= squareSize) {
          int squareStartIdx = ranges.size() - squareSize;
          Range squareStartRange = ranges.get(squareStartIdx);
          if (squareStartRange != null && squareStartRange.size() >= squareSize) {
            if (range.intersects(squareStartRange)) {
              Range intersection = range.intersect(squareStartRange);
              if (intersection != null && intersection.size() >= squareSize) {
                solvedPart2 = true;
                out("part 2", intersection.from() * 10000 + squareStartIdx);

                if (squareStart == null)
                  squareStart = new Vec((int) intersection.from(), squareStartIdx);
              }
            }
          }
        }
      } else
        ranges.add(null); // add null because of index should be y
    }


    if (squareStart != null && squareStart.y < 100) {
      Img img = new Img();
      for (int i = 0; i < ranges.size(); i++) {
        Range range = ranges.get(i); // i=y
        if (range == null) continue;
        for (int j = (int) range.from(); j <= range.to(); j++) {
          img.add(new Vec(j, i));
        }
      }
      img.add(squareStart, "O");
      out(img.toConsoleString(' '));
    }

  }

  private boolean isPulled(int x, int y) throws InterruptedException {
    LinkedBlockingQueue<Long> output = new LinkedBlockingQueue<>();
    Computer computer = new Computer(program, value -> {
      try {
        output.put(value);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });
    computer.addInput(x);
    computer.addInput(y);
    computer.exec();
    computer.join();
    return output.take() > 0;
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
