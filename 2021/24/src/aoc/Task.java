package aoc;

import aoc.util.Util;

import java.util.*;

/**
 * really good explanation for what is going on:
 * https://github.com/dphilipson/advent-of-code-2021/blob/master/src/days/day24.rs
 */
public class Task {
  private final ALU alu = new ALU();
  private final Stack<InputAdd> stack = new Stack<>();
  private final List<Integer> alternatingCheckOffset = new ArrayList<>();
  private final Map<Integer, InputAdd> inputPos2MatchingInputPosWithOffset = new HashMap<>();

  private boolean afterDivZ = false;
  private boolean afterAddYW = false;

  public void init() {
  }

  public void addLine(String input) {
    alu.addInstruction(input);
    // collect CHECKs and OFFSETs
    if (input.startsWith("div z"))
      afterDivZ = true;
    else if (input.startsWith("add y w"))
      afterAddYW = true;
    else if (afterDivZ || afterAddYW) {
      if (afterDivZ) afterDivZ = false;
      if (afterAddYW) afterAddYW = false;
      String[] split = input.split(" ");
      // String cmd = split[0];  int p1 = Integer.parseInt(split[1]);
      int p2 = Integer.parseInt(split[2]);
      alternatingCheckOffset.add(p2);
    }
  }

  private int getIntAtPos(int pos, long val) {
    long v = (long) (val / Math.pow(10, pos - 1));
    return (int) (v % 10);
  }

  public void afterParse() throws Exception {

    int inputPos = -1;

    while (!alternatingCheckOffset.isEmpty()) {
      inputPos++;
      int check = alternatingCheckOffset.removeFirst();
      int offset = alternatingCheckOffset.removeFirst();
      // push or pop?
      if (check > 0)// push
        stack.add(new InputAdd(inputPos, offset));
      else { // pop
        InputAdd inputAdd = stack.pop();
        int diffBetweenInputs = inputAdd.add + check;//+ because check is negative
        inputPos2MatchingInputPosWithOffset.put(inputPos, new InputAdd(inputAdd.inputPos, diffBetweenInputs));
        // other direction as well for easier search
        inputPos2MatchingInputPosWithOffset.put(inputAdd.inputPos, new InputAdd(inputPos, -diffBetweenInputs));
      }
    }

    // out(inputPos2MatchingInputPosWithOffset);

    List<Integer> list = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
    // find max
    for (int i = 0; i < 14; i++) {
      if (list.get(i) > 0) continue;
      InputAdd match = inputPos2MatchingInputPosWithOffset.get(i);
      if (match.add >= 0) {
        list.set(i, 9);
        list.set(match.inputPos, 9 - match.add);
      } else {
        list.set(i, 9 + match.add);
        list.set(match.inputPos, 9);
      }
    }

    long res = list2long(list);
    out("part 1", res);

    list = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
    // find min
    for (int i = 0; i < 14; i++) {
      if (list.get(i) > 0) continue;
      InputAdd match = inputPos2MatchingInputPosWithOffset.get(i);
      if (match.add < 0) {
        list.set(i, 1);
        list.set(match.inputPos, 1 - match.add);
      } else {
        list.set(i, 1 + match.add);
        list.set(match.inputPos, 1);
      }
    }

    res = list2long(list);
    out("part 2", res);
  }

  private static long list2long(List<Integer> list) {
    long res = 0;
    for (Integer i : list) {
      res = res * 10 + i;
    }
    return res;
  }


  public void out(Object... str) {
    Util.out(str);
  }

  record InputAdd(int inputPos, int add) {
  }
}
