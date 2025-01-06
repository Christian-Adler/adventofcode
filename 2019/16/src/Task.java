import java.util.*;
import java.util.stream.Collectors;

public class Task {

  private final Map<Integer, Pattern> patterns = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    List<Integer> inputL = Arrays.stream(input.split("")).mapToInt(Integer::parseInt).boxed().toList();

    ArrayList<Integer> inputList = new ArrayList<>(inputL);
    List<Integer> outputList = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      outputList = new ArrayList<>();
      ttf(inputList, outputList);
      // out(outputList);
      inputList = new ArrayList<>(outputList);
    }

    // out(outputList);
    out("part 1", outputList.subList(0, 8).stream().map(Object::toString).collect(Collectors.joining()));

    // part 2
    // didn't understand the hint with the offset - see https://todd.ginsberg.com/post/advent-of-code/2019/day16/
    // because offset is larger than half of the stretched input we don't have to care about the numbers before.
    // The second half of input is for each no *1
    Integer offset = inputL.subList(0, 7).stream().reduce((integer, integer2) -> integer * 10 + integer2).orElseThrow();
    // out(offset);
    // out(inputL.size() * 10000);
    ArrayList<Integer> stretchedInput = new ArrayList<>();
    for (int i = offset; i < 10000 * inputL.size(); i++) {
      stretchedInput.add(inputL.get(i % inputL.size()));
    }
    // out(stretchedInput.size());

    for (int j = 0; j < 100; j++) {
      int sumMod10 = 0;
      for (int i = stretchedInput.size() - 1; i >= 0; i--) {
        sumMod10 = (sumMod10 + stretchedInput.get(i)) % 10;
        stretchedInput.set(i, sumMod10);
      }
    }
    out("part 2", stretchedInput.subList(0, 8).stream().map(Object::toString).collect(Collectors.joining()));
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
