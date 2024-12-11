import java.util.*;

public class Task2 {
  Map<Long, Long> mapMark2Count = new HashMap<>();
  Map<Long, List<Long>> mappingMark2FollowingMark = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    Stone prev = null;
    List<Long> numbers = Arrays.stream(input.split(" ")).mapToLong(Long::parseLong).boxed().toList();
    for (Long number : numbers) {
      Long count = mapMark2Count.getOrDefault(number, 0L);
      count++;
      mapMark2Count.put(number, count);
    }
  }

  public void afterParse() {
    // out(toString());
    int noBlinks = 75;
    for (int i = 1; i <= noBlinks; i++) {

      blink();
      // out(toString());
      // out(i, "stones:", countStones());
    }


    out("part 2", "stones:", countStones());
  }

  private long countStones() {
    return mapMark2Count.values().stream().reduce(Long::sum).orElse(0L);
  }

  private void blink() {
    Map<Long, Long> nextMap = new HashMap<>();
    for (Map.Entry<Long, Long> mark2count : mapMark2Count.entrySet()) {
      long mark = mark2count.getKey();
      long count = mark2count.getValue();

      List<Long> following = mappingMark2FollowingMark.get(mark);
      if (following == null) {
        // Not yet determined?
        String strNumber = String.valueOf(mark);
        if (mark == 0)
          following = new ArrayList<>(List.of(1L));
        else if (strNumber.length() % 2 == 0) {
          Long n1 = Long.parseLong(strNumber.substring(0, strNumber.length() / 2));
          Long n2 = Long.parseLong(strNumber.substring(strNumber.length() / 2));

          following = new ArrayList<>(List.of(n1, n2));
        } else
          following = new ArrayList<>(List.of(mark * 2024));

        mappingMark2FollowingMark.put(mark, following);
      }

      for (Long l : following) {
        Long soFarCount = nextMap.getOrDefault(l, 0L);
        nextMap.put(l, soFarCount + count);
      }
    }
    mapMark2Count = nextMap;
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return "Task2{" +
        "map=" + mapMark2Count +
        ", mapping=" + mappingMark2FollowingMark +
        '}';
  }
}
