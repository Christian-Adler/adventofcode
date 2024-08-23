import java.util.*;
import java.util.stream.Collectors;

public class Task {

  private final Map<Integer, Pattern> patterns = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    List<Integer> inputList = Arrays.stream(input.split("")).mapToInt(Integer::parseInt).boxed().toList();

    List<Integer> outputList = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      outputList = new ArrayList<>();
      ttf(inputList, outputList);
      // out(outputList);
      inputList = new ArrayList<>(outputList);
    }

    // out(outputList);
    out(outputList.subList(0, 8).stream().map(Object::toString).collect(Collectors.joining()));
  }

  private void ttf(List<Integer> inputList, List<Integer> outputList) {
    for (int i = 0; i < inputList.size(); i++) {
      // get pattern
      int patternVal = i + 1;
      Pattern pattern = patterns.get(patternVal);
      if (pattern == null) {
        pattern = new Pattern(patternVal);
        patterns.put(patternVal, pattern);
      }

      // calc output
      long calcRes = 0;
      for (int j = 0; j < inputList.size(); j++) {
        calcRes += (long) inputList.get(j) * pattern.getPatternValAtIdx(j);
      }
      outputList.add(theOnesDigit(calcRes));
    }
  }

  private int theOnesDigit(long val) {
    return (int) (Math.abs(val) % 10);
  }

  public void afterParse() {
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
