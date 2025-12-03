package aoc;

import aoc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Bank {
  public static long calcBankJoltage(String input) {
    List<Integer> values = Util.str2List(input).stream().map(Integer::parseInt).toList();
    int maxVal1 = 0;
    int maxVal1Idx = -1;
    for (int i = 0; i < values.size() - 1; i++) {
      int val = values.get(i);
      if (val > maxVal1) {
        maxVal1 = val;
        maxVal1Idx = i;
      }
    }
    int maxVal2 = 0;
    for (int i = maxVal1Idx + 1; i < values.size(); i++) {
      int val = values.get(i);
      if (val > maxVal2) {
        maxVal2 = val;
      }
    }

    return maxVal1 * 10L + maxVal2;
  }

  public static long calcBankJoltage12(String input) {
    List<Integer> values = Util.str2List(input).stream().map(Integer::parseInt).toList();
    List<Integer> values12 = new ArrayList<>();
    int actMaxValIdx = -1;
    for (int n = 1; n <= 12; n++) {
      int maxVal = 0;
      for (int i = actMaxValIdx + 1; i < values.size() - (12 - n); i++) {
        int val = values.get(i);
        if (val > maxVal) {
          maxVal = val;
          actMaxValIdx = i;
          if (val == 9) break;
        }
      }
      values12.add(maxVal);
    }

    long result = 0;
    for (Integer i : values12) {
      result *= 10;
      result += i;
    }

    return result;
  }
}
