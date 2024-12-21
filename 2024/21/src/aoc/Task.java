package aoc;

import aoc.keypad.DirectionalKeypad;
import aoc.keypad.KeypadBase;
import aoc.keypad.NumericKeypad;
import aoc.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
  private final List<String> codes = new ArrayList<>();

  private final KeypadBase numPad = new NumericKeypad();
  private final KeypadBase dirPad = new DirectionalKeypad();
  private final Map<String, Long> codeDepth2LenCache = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    codes.add(input);
  }

  public void afterParse() throws Exception {
    long sumComplexity = getSumComplexity(2);
    out("Part 1", "sumComplexity", sumComplexity);
    sumComplexity = getSumComplexity(25);
    out("Part 2", "sumComplexity", sumComplexity);
  }

  private long getSumComplexity(int nunRobots) {
    long sumComplexity = 0;
    for (String code : codes) {
      long minLen = calcMinLen(code, nunRobots, numPad);
      int codeNumericPart = Integer.parseInt(code.replace("A", ""));
      long complexity = (long) codeNumericPart * minLen;
      // out(codeNumericPart, "*", minLen, "=", complexity);
      sumComplexity += complexity;
    }
    return sumComplexity;
  }

  private long calcMinLen(String code, int depth, KeypadBase keypad) {
    String cacheKey = code + "_" + depth;
    Long cached = codeDepth2LenCache.get(cacheKey);
    if (cached != null)
      return cached;

    // out("code", code);
    long len = 0;
    String prevC = "A";
    for (String c : Util.str2List(code)) {
      // out(c);
      List<String> paths = keypad.findShortestPaths(prevC, c);
      if (depth == 0)
        len += paths.getFirst().length() + 1; // +1 for Press = A after path
      else {
        long minSubLen = Long.MAX_VALUE;
        for (String path : paths) {
          long subLen = calcMinLen(path + "A", depth - 1, dirPad);
          if (subLen < minSubLen) minSubLen = subLen;
        }
        len += minSubLen;
      }
      prevC = c;
    }

    codeDepth2LenCache.put(cacheKey, len);
    return len;
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
