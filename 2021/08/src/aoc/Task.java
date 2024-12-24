package aoc;

import aoc.util.Img;
import aoc.util.Util;

import java.util.*;

public class Task {

  /*
   * 0 - 6
   * 1 - 2 - #
   * 2 - 5
   * 3 - 5
   * 4 - 4 - #
   * 5 - 5
   * 6 - 6
   * 7 - 3 - #
   * 8 - 7 - #
   * 9 - 6
   */


  /**
   * Segments:
   * 0
   * 1 2
   * 3
   * 4 5
   * 6
   */

  private static final Map<Integer, Set<Integer>> digit2segments = new HashMap<>();
  private static final Map<Set<Integer>, Integer> segments2digit = new HashMap<>();

  static {
    digit2segments.put(0, new HashSet<>(List.of(0, 1, 2, 4, 5, 6)));
    digit2segments.put(1, new HashSet<>(List.of(2, 5)));
    digit2segments.put(2, new HashSet<>(List.of(0, 2, 3, 4, 6)));
    digit2segments.put(3, new HashSet<>(List.of(0, 2, 3, 5, 6)));
    digit2segments.put(4, new HashSet<>(List.of(1, 2, 3, 5)));
    digit2segments.put(5, new HashSet<>(List.of(0, 1, 3, 5, 6)));
    digit2segments.put(6, new HashSet<>(List.of(0, 1, 3, 4, 5, 6)));
    digit2segments.put(7, new HashSet<>(List.of(0, 2, 5)));
    digit2segments.put(8, new HashSet<>(List.of(0, 1, 2, 3, 4, 5, 6)));
    digit2segments.put(9, new HashSet<>(List.of(0, 1, 2, 3, 5, 6)));

    for (Map.Entry<Integer, Set<Integer>> entry : digit2segments.entrySet()) {
      segments2digit.put(entry.getValue(), entry.getKey());
    }
  }


  private static final Map<Integer, Set<Integer>> signalLength2possibleDigits = new HashMap<>();

  static {
    signalLength2possibleDigits.put(2, new HashSet<>(List.of(1)));
    signalLength2possibleDigits.put(3, new HashSet<>(List.of(7)));
    signalLength2possibleDigits.put(4, new HashSet<>(List.of(4)));
    signalLength2possibleDigits.put(5, new HashSet<>(List.of(2, 3, 5)));
    signalLength2possibleDigits.put(6, new HashSet<>(List.of(0, 6, 9)));
    signalLength2possibleDigits.put(7, new HashSet<>(List.of(8)));
  }


  private int countEasySignals = 0;
  private long sumOutputs = 0;

  public void init() {
  }

  public void addLine(String input) {
    String[] split = input.split("\\|");
    List<String> signalPatterns = new ArrayList<>(Arrays.stream(split[0].trim().split(" ")).toList());
    List<String> outputValues = new ArrayList<>(Arrays.stream(split[1].trim().split(" ")).toList());

    // part 1
    HashSet<Integer> easyNos = new HashSet<>(List.of(2, 4, 3, 7)); // = digits 1, 4, 7, 8
    for (String splitOutput : outputValues) {
      if (easyNos.contains(splitOutput.length()))
        countEasySignals++;
    }

    // part 2
    signalPatterns.sort((o1, o2) -> o1.length() - o2.length());
    // out(signalPatterns);

    Map<String, Set<Integer>> signal2possibleSegments = new HashMap<>();

    // intersect digits segments per pattern
    for (String signalPattern : signalPatterns) {
      Set<Integer> possibleDigits = signalLength2possibleDigits.get(signalPattern.length());
      Set<Integer> possibleSegments = getPossibleSegmentsForDigits(possibleDigits);
      ArrayList<String> signals = Util.str2List(signalPattern);
      for (String signal : signals) {
        Set<Integer> soFarSegments = signal2possibleSegments.get(signal);
        if (soFarSegments == null)
          signal2possibleSegments.put(signal, new HashSet<>(possibleSegments));
        else
          soFarSegments.retainAll(possibleSegments);
      }
    }

    // out(signal2possibleSegments);

    // reduce if possible
    Set<Set<Integer>> alreadyChecked = new HashSet<>();
    boolean foundNext = true;
    while (foundNext) {
      foundNext = false;

      List<Set<Integer>> possibleSegmentsSortedByLen = new ArrayList<>(signal2possibleSegments.values().stream().sorted(Comparator.comparingInt(Set::size)).toList());
      // out(possibleSegmentsSortedByLen);
      Set<Integer> check = null;
      while (check == null && !possibleSegmentsSortedByLen.isEmpty()) {
        Set<Integer> toCheck = possibleSegmentsSortedByLen.removeFirst();
        if (!alreadyChecked.contains(toCheck)) {
          check = toCheck;
          alreadyChecked.add(check);
        }
      }

      if (check != null) {
        for (Set<Integer> possibleSegments : signal2possibleSegments.values()) {
          if (!possibleSegments.equals(check)) {
            if (possibleSegments.removeAll(check))
              foundNext = true;
          }
        }

        // out(signal2possibleSegments);
      }
      // else
      //   out("found no check");
    }
    // out(signal2possibleSegments);

    // recursive trial and error
    Map<String, Integer> signal2segment = trialAndError(signal2possibleSegments, signalPatterns);
    // out(signal2segment);

    long output = 0;
    for (String outputValue : outputValues) {
      Integer digit = getDigitForSignalPattern(outputValue, signal2segment);
      if (digit != null) {
        output *= 10;
        output += digit;
      }
    }
    // out(output);
    sumOutputs += output;
  }

  private static Map<String, Integer> trialAndError(Map<String, Set<Integer>> signal2possibleSegments, List<String> signalPatterns) {
    // recursion finished?
    boolean foundOnlySingleEntrySets = true;

    for (Map.Entry<String, Set<Integer>> entry : signal2possibleSegments.entrySet()) {
      ArrayList<Integer> possibleSegments = new ArrayList<>(entry.getValue());
      if (possibleSegments.size() > 1) {
        foundOnlySingleEntrySets = false;
        String signal = entry.getKey();

        for (Integer segment : possibleSegments) {
          Map<String, Set<Integer>> nextMap = new HashMap<>(signal2possibleSegments);
          nextMap.remove(signal);
          nextMap.put(signal, new HashSet<>(List.of(segment))); // add only one segment

          Map<String, Integer> recursionResult = trialAndError(nextMap, signalPatterns);
          if (recursionResult != null)
            return recursionResult;
        }
        break;
      }
    }

    if (foundOnlySingleEntrySets) {
      Set<Integer> duplicateSegmentsCheck = new HashSet<>();
      Map<String, Integer> map = new HashMap<>();
      for (Map.Entry<String, Set<Integer>> entry : signal2possibleSegments.entrySet()) {
        if (!duplicateSegmentsCheck.addAll(entry.getValue()))
          return null; // duplicate segment found
        map.put(entry.getKey(), entry.getValue().iterator().next());
      }

      Set<Integer> foundDigits = new HashSet<>();
      for (String signalPattern : signalPatterns) {
        Integer digit = getDigitForSignalPattern(signalPattern, map);
        if (digit == null)
          return null;
        else foundDigits.add(digit);
      }
      if (foundDigits.size() != 10)
        return null;
      return map;
    }

    return null;
  }

  private static Integer getDigitForSignalPattern(String signalPattern, Map<String, Integer> signal2segment) {
    Set<Integer> activeSegmentsForPattern = new HashSet<>();
    for (String signal : Util.str2List(signalPattern)) {
      activeSegmentsForPattern.add(signal2segment.get(signal));
    }
    return segments2digit.get(activeSegmentsForPattern);
  }

  private static Set<Integer> getPossibleSegmentsForDigits(Set<Integer> possibleDigits) {
    Set<Integer> digitPossibleSegments = new HashSet<>();
    for (Integer possibleDigit : possibleDigits) {
      digitPossibleSegments.addAll(digit2segments.get(possibleDigit));
    }
    return digitPossibleSegments;
  }

  public void afterParse() throws Exception {
    out("part 1", "count easy signals", countEasySignals);
    out("part 2", "sum digits", sumOutputs);
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return toStringConsole();
  }

  public void toBmp() throws Exception {
    Img img = new Img();
    img.writeBitmapAged();
  }

  public String toStringConsole() {
    Img img = new Img();
    return img.toConsoleString();
  }
}
