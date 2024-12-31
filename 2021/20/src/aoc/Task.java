package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task {
  private static final List<Vec> square3x3 = new ArrayList<>();

  private final List<String> algo = new ArrayList<>();
  private final Set<Vec> map = new HashSet<>();

  private int maxY = -1;

  public void init() {
    square3x3.clear();
    algo.clear();
    square3x3.addAll(List.of(new Vec(-1, -1), new Vec(0, -1), new Vec(1, -1), new Vec(-1, 0), new Vec(0, 0), new Vec(1, 0), new Vec(-1, 1), new Vec(0, 1), new Vec(1, 1)));
  }

  public void addLine(String input) {
    if (input.isEmpty()) return;
    if (algo.isEmpty()) {
      algo.addAll(Util.str2List(input));
      return;
    }

    maxY++;
    int x = -1;
    for (String s : Util.str2List(input)) {
      x++;
      if (s.equals("#"))
        map.add(new Vec(x, maxY));
    }
  }

  public void afterParse() throws Exception {
    // if (true) {
    //   out(Integer.parseInt("111111111", 2));
    //   return;
    // }

    // besonderer Fall Echtdaten!
    // im Algo ist index 0 ein # - dadurch toggeln !alle! Felder ins unendliche
    // daher in diesem Fall alternierend in der map die lit und dann wieder die unlit pixel speichern!
    boolean realInput = algo.getFirst().equals("#");

    // out(this);
    for (int i = 0; i < 2; i++) {
      step(realInput, i % 2 == 0);
    }

    out("part 1", "lit pixels", map.size());

    for (int i = 0; i < 48; i++) {
      step(realInput, i % 2 == 0);
    }
    // out(this);
    out("part 2", "lit pixels", map.size());
  }

  private void step(boolean realInput, boolean lit) {
    Set<Vec> soFarImg = new HashSet<>(map);
    map.clear();
    int border = 2;
    int minX = soFarImg.stream().mapToInt(v -> v.x).min().orElseThrow() - border;
    int maxX = soFarImg.stream().mapToInt(v -> v.x).max().orElseThrow() + border;
    int minY = soFarImg.stream().mapToInt(v -> v.y).min().orElseThrow() - border;
    int maxY = soFarImg.stream().mapToInt(v -> v.y).max().orElseThrow() + border;
    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        Vec pos = new Vec(x, y);

        StringBuilder binBuilder = new StringBuilder();
        for (Vec vec : square3x3) {
          if (!realInput || lit)
            binBuilder.append(soFarImg.contains(pos.addToNew(vec)) ? "1" : "0");
          else // "inverted" img
            binBuilder.append(soFarImg.contains(pos.addToNew(vec)) ? "0" : "1");
        }
        String bin = binBuilder.toString();
        int idx = Integer.parseInt(bin, 2);

        boolean algoLit = algo.get(idx).equals("#");
        if (!realInput) {
          if (algoLit)
            map.add(pos);
        } else {
          if (lit && !algoLit || !lit && algoLit)
            map.add(pos);
        }
      }
    }
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
    for (Vec vec : map) {
      img.add(vec);
    }
    img.writeBitmapAged();
  }

  public String toStringConsole() {
    Img img = new Img();
    for (Vec vec : map) {
      img.add(vec);
    }
    return img.toConsoleString();
  }
}
