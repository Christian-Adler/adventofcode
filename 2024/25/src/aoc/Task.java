package aoc;

import aoc.util.Img;
import aoc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Task {
  private final List<Schema> schemas = new ArrayList<>();
  private Schema actSchema = null;
  private final List<Schema> locks = new ArrayList<>();
  private final List<Schema> keys = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    if (input.isEmpty()) {
      actSchema = null;
      return;
    }

    if (actSchema == null) {
      actSchema = new Schema();
      schemas.add(actSchema);
    }
    actSchema.addInput(input);
  }

  public void afterParse() throws Exception {
    for (Schema schema : schemas) {
      schema.evalHeights();
      // out();
      // out(schema);
      // out(schema.getHeights());
      if (schema.isLock())
        locks.add(schema);
      else
        keys.add(schema);
    }

    int fitCounter = 0;
    for (Schema key : keys) {
      for (Schema lock : locks) {
        if (lock.fits(key))
          fitCounter++;
      }
    }
    out("part 1", "fit count", fitCounter);
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
