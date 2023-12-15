import java.util.*;

public class Task {

  Map<Pos, Integer> map = new HashMap<>();
  int maxY = -1;
  int maxX = 0;

  public void init() {
  }

  public void addLine(String input) {
    maxX = input.length() - 1;
    maxY++;
    ArrayList<String> row = Util.str2List(input);
    for (int x = 0; x < row.size(); x++) {
      String s = row.get(x);
      if (s.equals("O"))
        map.put(new Pos(x, maxY), 0);
      else if (s.equals("#"))
        map.put(new Pos(x, maxY), 1);
    }
  }

  public void afterParse() {
//    out(toStringConsole());

    tiltNorth();

    out();
    out("Part 1");
//    out(toStringConsole());

    long totalLoad = getTotalLoad();

    out("totalLoad", totalLoad);

    // Part 2
    out("Part 2");

    Map<Long, Set<Pos>> store = new HashMap<>();

    long foundFirst = -1;
    long foundSecond = -1;

    // Find repeat...
    for (long i = 0; i < 1000000000; i++) {
      cycle();
      HashSet<Pos> movable = new HashSet<>(getMovableRocks());

      if (store.containsValue(movable)) {
        for (Map.Entry<Long, Set<Pos>> entry : store.entrySet()) {
          if (entry.getValue().equals(movable)) {
            out("first repeated state", entry.getKey());
            foundFirst = entry.getKey();
            break;
          }
        }
        out("found repeat state" + i);
        foundSecond = i;
        break;
      }
      store.put(i, movable);
    }

    // skip repeating steps
    long diff = foundSecond - foundFirst;
//    out("diff", diff);
    long actStep = foundSecond + 1;
    while (actStep + diff <= 1000000000)
      actStep += diff;

    // do rest of cycles
    while (actStep < 1000000000) {
      cycle();
      actStep++;
    }

    totalLoad = getTotalLoad();
    out("totalLoad", totalLoad);
  }

  private long getTotalLoad() {
    long totalLoad = 0;

    for (Pos pos : map.entrySet().stream().filter(e -> e.getValue() == 0).map(Map.Entry::getKey).toList()) {
      int load = maxY + 1 - pos.y;
      totalLoad += load;
    }
    return totalLoad;
  }

  private void cycle() {
    tiltNorth();
//        out("\r\nafter n");
//        out(toStringConsole());
    tiltWest();
//        out("\nafter w");
//        out(toStringConsole());
    tiltSouth();
//        out("\nafter s");
//        out(toStringConsole());
    tiltEast();
//        out("\nafter east");
//        out(toStringConsole());
  }

  private void tiltNorth() {
    ArrayList<Pos> list = getMovableRocks();
    list.sort(Comparator.comparingInt(o -> o.y));
    for (Pos pos : list) {
      Integer rock = map.get(pos);
      if (rock != null && rock == 0) {
        int actY = pos.y;
        while (actY > 0) {
          if (map.containsKey(new Pos(pos.x, actY - 1))) break;
          actY--;
        }
        map.remove(pos);
        map.put(new Pos(pos.x, actY), 0);
      }
    }
  }

  private void tiltSouth() {
    ArrayList<Pos> list = getMovableRocks();
    list.sort(Comparator.comparingInt(o -> o.y));
    Collections.reverse(list);
    for (Pos pos : list) {
      Integer rock = map.get(pos);
      if (rock != null && rock == 0) {
        int actY = pos.y;
        while (actY < maxY) {
          if (map.containsKey(new Pos(pos.x, actY + 1))) break;
          actY++;
        }
        map.remove(pos);
        map.put(new Pos(pos.x, actY), 0);
      }
    }
  }

  private void tiltWest() {
    ArrayList<Pos> list = getMovableRocks();
    list.sort(Comparator.comparingInt(o -> o.x));
    for (Pos pos : list) {
      Integer rock = map.get(pos);
      if (rock != null && rock == 0) {
        int actX = pos.x;
        while (actX > 0) {
          if (map.containsKey(new Pos(actX - 1, pos.y))) break;
          actX--;
        }
        map.remove(pos);
        map.put(new Pos(actX, pos.y), 0);
      }
    }
  }

  private void tiltEast() {
    ArrayList<Pos> list = getMovableRocks();
    list.sort(Comparator.comparingInt(o -> o.x));
    Collections.reverse(list);
    for (Pos pos : list) {
      Integer rock = map.get(pos);
      if (rock != null && rock == 0) {
        int actX = pos.x;
        while (actX < maxX) {
          if (map.containsKey(new Pos(actX + 1, pos.y))) break;
          actX++;
        }
        map.remove(pos);
        map.put(new Pos(actX, pos.y), 0);
      }
    }
  }

  private ArrayList<Pos> getMovableRocks() {
    return new ArrayList<>(map.entrySet().stream().filter(e -> e.getValue() == 0).map(Map.Entry::getKey).toList());
  }

  public void out(Object... str) {
    Util.out(str);
  }


  public String toStringConsole() {
    SVG svg = new SVG();
    for (Map.Entry<Pos, Integer> entry : map.entrySet()) {
      svg.add(entry.getKey(), entry.getValue().toString());
    }
    return svg.toConsoleString();
  }
}
