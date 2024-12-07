import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Equation {
  private final long testValue;
  private final List<Long> numbers = new ArrayList<>();

  public Equation(String input) {
    String[] parts = input.split(":");
    testValue = Long.parseLong(parts[0]);
    numbers.addAll(Arrays.stream(parts[1].trim().split(" ")).mapToLong(Long::parseLong).boxed().toList());
  }

  public boolean isSolvable() {
    List<BiFunction<Long, Long, Long>> operators = Arrays.asList(this::mult, Long::sum);
    return isSolvable(operators);
  }

  public boolean isSolvable2() {
    List<BiFunction<Long, Long, Long>> operators = Arrays.asList(this::mult, Long::sum, this::concat);
    return isSolvable(operators);
  }

  private boolean isSolvable(List<BiFunction<Long, Long, Long>> operators) {
    long start = numbers.getFirst();
    List<Long> nos = numbers.subList(1, numbers.size());

    return isSolvable(start, nos, operators);
  }

  private boolean isSolvable(long soFarRes, List<Long> nos, List<BiFunction<Long, Long, Long>> operators) {
    if (soFarRes == testValue && nos.isEmpty()) return true;
    if (soFarRes > testValue || nos.isEmpty()) return false;

    long next = nos.getFirst();
    List<Long> nextNos = nos.subList(1, nos.size());

    for (BiFunction<Long, Long, Long> operator : operators) {
      if (isSolvable(operator.apply(soFarRes, next), nextNos, operators)) return true;
    }
    return false;
  }

  private long mult(long val1, long val2) {
    return val1 * val2;
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
