package aoc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IDRange {
  public long from;
  public long to;

  public IDRange(String input) {
    long[] larr = Arrays.stream(input.split("-", 2)).mapToLong(Long::parseLong).toArray();
    from = larr[0];
    to = larr[1];
  }

  public long sumInvalidIds() {
    String strFrom = String.valueOf(from);
    long halfFrom = 1;
    if (strFrom.length() % 2 != 0) {
      if (strFrom.length() > 1)
        strFrom = strFrom.substring(0, strFrom.length() - 1);
    }
    if (strFrom.length() > 1) {
      halfFrom = Long.parseLong(strFrom.substring(0, strFrom.length() / 2));
    }

    long sumInvalidIds = 0;

    long actVal = halfFrom - 1;
    long firstHalf = halfFrom;
    while (actVal <= to) {
      long factor = (long) Math.pow(10, String.valueOf(firstHalf).length());
      actVal = firstHalf * factor + firstHalf;
      if (actVal >= from && actVal <= to) sumInvalidIds += actVal;
      firstHalf++;
    }

    return sumInvalidIds;
  }

  public long sumInvalidIds2() {
    long sumInvalidIds = 0;
    Set<Long> invalidIds = new HashSet<>();

    int maxNoLen = String.valueOf(to).length() / 2;
    long no = 1;
    while (String.valueOf(no).length() <= maxNoLen) {
      long factor = (long) Math.pow(10, String.valueOf(no).length());
      long actVal = no;
      while (actVal <= to) {
        if (actVal > 10 && actVal >= from && actVal <= to && invalidIds.add(actVal)) { // >10 to prevent 1-9
          sumInvalidIds += actVal;
        }
        actVal = actVal * factor + no;
      }

      no++;
    }

    return sumInvalidIds;
  }

  @Override
  public String toString() {
    return "IDRange{" +
        "from=" + from +
        ", to=" + to +
        '}';
  }
}
