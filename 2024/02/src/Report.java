import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Report {
  private final List<Long> levels = new ArrayList<>();
  private boolean safe1 = false;
  private boolean safe2 = false;

  public Report(String input) {
    levels.addAll(Arrays.stream(input.split(" ")).mapToLong(Long::parseLong).boxed().toList());
  }

  private Report(List<Long> levels) {
    this.levels.addAll(levels);
  }

  public void checkSafe() {
    if (levels.size() < 2) return;
    if (levels.get(0).equals(levels.get(1))) return;
    boolean increasing = levels.getLast() > levels.getFirst();
    long prevValue = -1;
    for (Long level : levels) {
      if (prevValue >= 0) {
        long difference = Math.abs(level - prevValue);
        if (difference < 1 || difference > 3)
          return;
        if (increasing) {
          if (level <= prevValue)
            return;
        } else {
          if (level >= prevValue)
            return;
        }
      }
      prevValue = level;
    }
    safe1 = true;
  }

  public void checkSafe2() {
    if (levels.size() < 4) {
      Util.out("Report size < 4");
      return;
    }

    // if part 1 safe part 2 is also safe
    checkSafe();
    if (isSafe1()) {
      safe2 = true;
      return;
    }

    // check decreasing / increasing by 3 neighbours!
    boolean increasing = isIncreasing();
    // Util.out("increasing", increasing);

    for (int i = 1; i < levels.size(); i++) {
      long prevValue = levels.get(i - 1);
      long level = levels.get(i);

      long difference = Math.abs(level - prevValue);
      if (difference < 1 || difference > 3) {
        checkForSafeWithRemovedLevel(i);
        return;
      }
      if (increasing) {
        if (level <= prevValue) {
          checkForSafeWithRemovedLevel(i);
          return;
        }
      } else {
        if (level >= prevValue) {
          checkForSafeWithRemovedLevel(i);
          return;
        }
      }
    }
  }

  private void checkForSafeWithRemovedLevel(int i) {
    List<Long> ls1 = new ArrayList<>(levels);
    List<Long> ls2 = new ArrayList<>(levels);
    ls1.remove(i - 1);
    ls2.remove(i);

    Report r1 = new Report(ls1);
    Report r2 = new Report(ls2);

    r1.checkSafe();
    r2.checkSafe();

    if (r1.isSafe1() || r2.isSafe1())
      safe2 = true;
  }

  private boolean isIncreasing() {
    int increases = 0;
    int decreases = 0;

    long prevValue = -1;
    List<Long> subList = levels.subList(0, 4);
    for (Long level : subList) {
      if (prevValue >= 0) {
        if (level > prevValue) increases++;
        if (level < prevValue) decreases++;
      }
      prevValue = level;
    }

    return increases > decreases;
  }

  public boolean isSafe1() {
    return safe1;
  }

  public boolean isSafe2() {
    return safe2;
  }

  @Override
  public String toString() {
    return "Report{" +
        "levels=" + levels +
        ", safe1=" + (safe1 ? "Safe" : "Unsafe") +
        ", safe2=" + (safe2 ? "Safe" : "Unsafe") +
        '}';
  }
}
