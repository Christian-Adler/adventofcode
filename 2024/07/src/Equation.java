import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equation {
  private final long testValue;
  private final List<Long> numbers = new ArrayList<>();

  public Equation(String input) {
    String[] parts = input.split(":");
    testValue = Long.parseLong(parts[0]);
    numbers.addAll(Arrays.stream(parts[1].trim().split(" ")).mapToLong(Long::parseLong).boxed().toList());
  }

  public boolean isSolvable() {
    long start = numbers.getFirst();
    List<Long> nos = numbers.subList(1, numbers.size());

    return isSolvable(start, nos);
  }

  private boolean isSolvable(long soFarRes, List<Long> nos) {
    if (soFarRes > testValue) return false;

    long next = nos.getFirst();

    if (nos.size() == 1) {
      long nextRes = soFarRes * next;
      if (nextRes == testValue)
        return true;

      nextRes = soFarRes + next;
      return nextRes == testValue;
    }

    List<Long> nextNos = nos.subList(1, nos.size());

    if (isSolvable(soFarRes * next, nextNos))
      return true;

    return isSolvable(soFarRes + next, nextNos);
  }

  public boolean isSolvable2() {
    long start = numbers.getFirst();
    List<Long> nos = numbers.subList(1, numbers.size());

    return isSolvable2(start, nos);
  }

  private boolean isSolvable2(long soFarRes, List<Long> nos) {
    if (soFarRes > testValue) return false;

    long next = nos.getFirst();

    if (nos.size() == 1) {
      // *
      long nextRes = soFarRes * next;
      if (nextRes == testValue)
        return true;

      // +
      nextRes = soFarRes + next;
      if (nextRes == testValue)
        return true;

      // ||
      nextRes = concat(soFarRes, next);

      return nextRes == testValue;
    }

    List<Long> nextNos = nos.subList(1, nos.size());

    // *
    if (isSolvable2(soFarRes * next, nextNos))
      return true;

    // +
    if (isSolvable2(soFarRes + next, nextNos))
      return true;

    // ||
    long nextRes = concat(soFarRes, next);
    return isSolvable2(nextRes, nextNos);
  }

  private long concat(long val1, long val2) {
    return ((long) Math.pow(10, Util.numberLength(val2))) * val1 + val2;
  }

  public long getTestValue() {
    return testValue;
  }

  @Override
  public String toString() {
    return "Equation{" +
        "testValue=" + testValue +
        ", numbers=" + numbers +
        '}';
  }
}
