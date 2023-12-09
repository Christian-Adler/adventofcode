import java.util.ArrayList;
import java.util.Arrays;

public class Task {
  long sum = 0;
  long sum2 = 0;

  public void init() {
  }

  public void addLine(String input) {
    ArrayList<Long> history = new ArrayList<>(Arrays.stream(input.split("\\s+")).mapToLong(Long::parseLong).boxed().toList());
    sum += extrapolateHistory(history);
    sum2 += extrapolateHistory2(history);
  }

  private long extrapolateHistory(ArrayList<Long> history) {
    ArrayList<ArrayList<Long>> lists = new ArrayList<>();
    lists.add(history);

    ArrayList<Long> actList = history;
    while (!isAll0(actList)) {
      ArrayList<Long> diffs = createDiffList(actList);
      lists.add(diffs);
      actList = diffs;
    }
//    out(lists);

    // rueckwaerts wieder nach oben
    ArrayList<Long> prevList = null;
    for (int i = lists.size() - 1; i >= 0; i--) {
      actList = lists.get(i);
      if (prevList == null)
        actList.add(0L);
      else {
        actList.add(actList.getLast() + prevList.getLast());
      }
      prevList = actList;
    }

//    out(lists);
    return history.getLast();
  }

  private long extrapolateHistory2(ArrayList<Long> history) {
    ArrayList<ArrayList<Long>> lists = new ArrayList<>();
    lists.add(history);

    ArrayList<Long> actList = history;
    while (!isAll0(actList)) {
      ArrayList<Long> diffs = createDiffList(actList);
      lists.add(diffs);
      actList = diffs;
    }
//    out(lists);

    // rueckwaerts wieder nach oben
    ArrayList<Long> prevList = null;
    for (int i = lists.size() - 1; i >= 0; i--) {
      actList = lists.get(i);
      if (prevList == null)
        actList.addFirst(0L);
      else {
        actList.addFirst(actList.getFirst() - prevList.getFirst());
      }
      prevList = actList;
    }

//    out(lists);
    return history.getFirst();
  }

  private static ArrayList<Long> createDiffList(ArrayList<Long> history) {
    ArrayList<Long> diffs = new ArrayList<>();
    Long prevValue = null;
    for (Long historyValue : history) {
      if (prevValue == null) prevValue = historyValue;
      else {
        long diff = historyValue - prevValue;
        diffs.add(diff);
        prevValue = historyValue;
      }
    }
    return diffs;
  }

  private static boolean isAll0(ArrayList<Long> values) {
    for (Long value : values) {
      if (value != 0)
        return false;
    }
    return true;
  }

  public void afterParse() {
    out("Part 1", sum);
    out("Part 2", sum2);
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
