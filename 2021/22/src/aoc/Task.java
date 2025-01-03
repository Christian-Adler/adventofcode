package aoc;

import aoc.util.Cuboid;
import aoc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Task {
  private final ArrayList<Box> boxes = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    boxes.add(Box.parse(input));
  }

  public void afterParse() throws Exception {
    // out(boxes);

    List<Box> activeBoxes = new ArrayList<>();

    for (Box box : boxes) {
      if (activeBoxes.isEmpty()) {
        if (box.on)
          activeBoxes.add(box.copy());
      } else  // merge box
        merge(box, activeBoxes);

      // out("active cubes", countActiveCubes(activeBoxes));
    }
    out("part 2", "active cubes", countActiveCubes(activeBoxes));

    // remove cubes outside of init area
    Box off = Box.parse("off x=-100000..-51,y=-100000..1000000,z=-100000..100000");
    merge(off, activeBoxes);
    off = Box.parse("off x=-100000..1000000,y=-100000..-51,z=-100000..100000");
    merge(off, activeBoxes);
    off = Box.parse("off x=-100000..1000000,y=-100000..1000000,z=-100000..-51");
    merge(off, activeBoxes);
    off = Box.parse("off x=51..100000,y=-100000..1000000,z=-100000..100000");
    merge(off, activeBoxes);
    off = Box.parse("off x=-100000..1000000,y=51..100000,z=-100000..100000");
    merge(off, activeBoxes);
    off = Box.parse("off x=-100000..1000000,y=-100000..1000000,z=51..100000");
    merge(off, activeBoxes);

    out("part 1", "active cubes", countActiveCubes(activeBoxes));
  }

  private void merge(Box box, List<Box> activeBoxes) {
    if (box.isOn()) {
      // add cubes to active once -> split self
      List<Box> worklist = new ArrayList<>();
      worklist.add(box);
      while (!worklist.isEmpty()) {
        Box offBox = worklist.removeFirst();
        boolean foundIntersect = false;
        for (Box activeBox : activeBoxes) {
          if (activeBox.cuboid.intersects(offBox.cuboid)) {
            foundIntersect = true;
            List<Cuboid> split = offBox.cuboid.splitBy(activeBox.cuboid);
            for (Cuboid cuboid : split) {
              worklist.add(new Box(cuboid, offBox.on));
            }
            break;
          }
        }
        if (!foundIntersect)
          activeBoxes.add(offBox);
      }
    } else {
      // remove cubes from active once -> split active cuboids
      List<Box> toRemoveFromActive = new ArrayList<>();
      List<Box> toAddToActive = new ArrayList<>();
      for (Box activeBox : activeBoxes) {
        if (activeBox.cuboid.intersects(box.cuboid)) {
          toRemoveFromActive.add(activeBox);
          List<Cuboid> split = activeBox.cuboid.splitBy(box.cuboid);
          for (Cuboid cuboid : split) {
            toAddToActive.add(new Box(cuboid, activeBox.on));
          }
        }
      }
      activeBoxes.removeAll(toRemoveFromActive);
      activeBoxes.addAll(toAddToActive);
    }

    // out("activeBoxes", activeBoxes);
  }

  public long countActiveCubes(List<Box> boxes) {
    return boxes.stream().mapToLong(b -> b.cuboid.volume()).sum();
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
