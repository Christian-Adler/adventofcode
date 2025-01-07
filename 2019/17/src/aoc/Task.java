package aoc;

import aoc.computer.Computer;
import aoc.util.Img;
import aoc.util.Pair;
import aoc.util.Util;
import aoc.util.Vec;

import java.util.*;

public class Task {
  private final Set<Vec> map = new HashSet<>();
  private Vec pos = null;
  private Vec dir = null;
  private int maxY = -1;
  private int maxX = -1;
  private static final String NL = "\n";

  public void init() {
  }

  public void addLine(String input) {
    parseLineForMap(input, map);
  }

  private void parseLineForMap(String input, Set<Vec> map) {
    maxY++;
    maxX = input.length() - 1;
    int x = -1;
    for (String s : Util.str2List(input)) {
      x++;
      if (!s.equals(".")) {
        map.add(new Vec(x, maxY));
        if (!s.equals("#")) {
          pos = new Vec(x, maxY);
          dir = Vec.parseDir(s);
        }
      }
    }
  }

  public void afterParse(String intCode) throws Exception {
    // if (true) {
    //   out(mapStr2intCode("A"));
    //   out(mapStr2intCode(","));
    //   out(mapStr2intCode("B"));
    //   out(mapStr2intCode("8"));
    //
    //   return;
    // }
    if (map.isEmpty()) // for real task use intCod which builds the map
      buildMapFromIntCode(intCode, map);

    out(this);

    List<Vec> intersections = findIntersections();

    long sumAlignmentParameters = 0;
    for (Vec intersection : intersections) {
      sumAlignmentParameters += (long) intersection.x * intersection.y;
    }
    out("part 1", sumAlignmentParameters);

    // part 2


    // per regex from https://www.reddit.com/r/adventofcode/comments/ebr7dg/2019_day_17_solutions/?rdt=50511
    // ^(.{1,21})\1*(.{1,21})(?:\1|\2)*(.{1,21})(?:\1|\2|\3)*$

    // calc movement

    // Build the full path
    List<String> fullPath = buildFullPath();
    out(fullPath);

    // break full path into three repeating patterns

    // get from full path per regex from https://www.reddit.com/r/adventofcode/comments/ebr7dg/2019_day_17_solutions/?rdt=50511
    // ^(.{1,21})\1*(.{1,21})(?:\1|\2)*(.{1,21})(?:\1|\2|\3)*$
    // out(String.join(",", fullPath)+",");

    Map<List<String>, Set<Integer>> subPaths = findSubPaths(fullPath, new HashMap<>());
    if (subPaths == null) throw new IllegalStateException("Found no sub paths solution");

    List<String> nextLetters = new ArrayList<>(List.of("A", "B", "C"));
    List<Pair<String, Integer>> letter2index = new ArrayList<>();
    Map<String, List<String>> letter2subPath = new HashMap<>();

    for (Map.Entry<List<String>, Set<Integer>> entry : subPaths.entrySet()) {
      String letter = nextLetters.removeFirst();
      for (Integer index : entry.getValue()) {
        letter2index.add(new Pair<>(letter, index));
      }
      letter2subPath.put(letter, entry.getKey());
    }

    letter2index.sort(Comparator.comparingInt(Pair::value));
    List<String> movementMain = new ArrayList<>(letter2index.stream().map(Pair::key).toList());

    // run computer
    String intCodePrompted = "2" + intCode.substring(1);
    // out(intCodePrompted);
    Computer computer = new Computer(Computer.parseProgram(intCodePrompted), value -> {
      if (value > 0xFF) {
        System.out.println(value);
      } else {
        System.out.print((char) value.intValue());
      }
    });
    // computer.addInput(movementMain.stream().collect(Collectors.joining(",")));

    addComputerInputs(movementMain, computer);
    addComputerInputs(letter2subPath.get("A"), computer);
    addComputerInputs(letter2subPath.get("B"), computer);
    addComputerInputs(letter2subPath.get("C"), computer);
    addComputerInputs(List.of("n"), computer);

    computer.exec();
    computer.join();
  }

  private void addComputerInputs(List<String> stringList, Computer computer) {
    boolean addComma = false;
    for (String s : stringList) {
      if (addComma)
        computer.addInput(mapStr2intCode(","));
      addComma = true;
      for (String str : Util.str2List(s)) {
        computer.addInput(mapStr2intCode(str));
      }
    }
    computer.addInput(mapStr2intCode(NL));
  }

  private int mapStr2intCode(String s) {
    return s.codePointAt(0);
  }

  private Map<List<String>, Set<Integer>> findSubPaths(List<String> fullPath, Map<List<String>, Set<Integer>> m) {
    int searchStartIdx = 0;
    for (int i = 0; i < fullPath.size(); i++) {
      if (!fullPath.get(i).equals("_")) {
        searchStartIdx = i;
        break;
      }
    }

    // find as large as possible sub paths
    for (int i = 10; i >= 3; i--) { // max 10 items long because of 20chars limit (with commas)
      List<String> path = new ArrayList<>(fullPath);
      if (searchStartIdx + i > path.size()) continue;
      List<String> subPath = new ArrayList<>(path.subList(searchStartIdx, searchStartIdx + i));
      String subPathStr = String.join(",", subPath);
      if (subPathStr.length() > 20 || subPathStr.contains("_"))
        continue;

      Map<List<String>, Set<Integer>> map = new HashMap<>(m);

      int indexSubList = Collections.indexOfSubList(path, subPath);
      while (indexSubList >= 0) {
        Set<Integer> indexes = map.getOrDefault(subPath, new LinkedHashSet<>());
        indexes.add(indexSubList);
        map.put(subPath, indexes);
        for (int j = indexSubList; j < indexSubList + subPath.size(); j++) {
          path.set(j, "_");
        }
        indexSubList = Collections.indexOfSubList(path, subPath);
      }

      if (map.size() < 3) {
        Map<List<String>, Set<Integer>> map2 = findSubPaths(path, map);
        if (map2 != null)
          return map2;
      } else if (map.size() == 3 && path.stream().allMatch(s -> s.equals("_")))
        return map;
    }
    return null;
  }

  private List<String> buildFullPath() {
    List<String> fullPath = new ArrayList<>();
    Vec actDir = dir;
    Vec actPos = pos;
    int actStepsInSameDir = 0;
    while (true) {
      if (map.contains(actPos.add(actDir))) {
        actStepsInSameDir++;
        actPos = actPos.add(actDir);
      } else if (map.contains(actPos.add(actDir.rotate90DegLeft()))) {
        if (actStepsInSameDir > 0) fullPath.add(String.valueOf(actStepsInSameDir));
        actStepsInSameDir = 0;
        fullPath.add("L");
        actDir = actDir.rotate90DegLeft();
      } else if (map.contains(actPos.add(actDir.rotate90DegRight()))) {
        if (actStepsInSameDir > 0) fullPath.add(String.valueOf(actStepsInSameDir));
        actStepsInSameDir = 0;
        fullPath.add("R");
        actDir = actDir.rotate90DegRight();
      } else {
        // out("path finished");
        break;
      }
    }
    if (actStepsInSameDir > 0)
      fullPath.add(String.valueOf(actStepsInSameDir));

    return fullPath;
  }

  private void buildMapFromIntCode(String intCode, Set<Vec> map) throws InterruptedException {
    StringBuilder builder = new StringBuilder();

    Computer computer = new Computer(Computer.parseProgram(intCode), value -> {
      if (value == 35)
        builder.append("#");
      else if (value == 46)
        builder.append(".");
      else if (value == 10)
        builder.append("\r\n");
      else
        builder.append(Character.toChars(Math.toIntExact(value)));
    });
    computer.exec();
    computer.join();

    String[] split = builder.toString().split("\r\n");
    for (String s : split) {
      parseLineForMap(s, map);
    }
  }

  private List<Vec> findIntersections() {
    List<Vec> intersections = new ArrayList<>();
    for (Vec vec : map) {
      boolean found = true;
      for (Vec dir : Vec.adjacent) {
        if (!map.contains(vec.add(dir))) {
          found = false;
          break;
        }
      }
      if (found)
        intersections.add(vec);
    }
    return intersections;
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return toStringConsole(map);
  }

  public void toBmp() throws Exception {
    Img img = new Img();
    img.writeBitmapAged();
  }

  public String toStringConsole(Set<Vec> map) {
    Img img = new Img();
    img.add(new Vec(0, 0), ".");
    img.add(new Vec(maxX, maxY), ".");
    map.forEach(img::add);
    img.add(pos, dir.toDirString());
    return img.toConsoleString();
  }
}
