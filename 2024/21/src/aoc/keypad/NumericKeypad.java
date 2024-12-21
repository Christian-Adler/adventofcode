package aoc.keypad;

import aoc.util.Vec;

public class NumericKeypad extends KeypadBase {
  public NumericKeypad() {
    keypad.put("7", new Vec(0, 0));
    keypad.put("8", new Vec(1, 0));
    keypad.put("9", new Vec(2, 0));
    keypad.put("4", new Vec(0, 1));
    keypad.put("5", new Vec(1, 1));
    keypad.put("6", new Vec(2, 1));
    keypad.put("1", new Vec(0, 2));
    keypad.put("2", new Vec(1, 2));
    keypad.put("3", new Vec(2, 2));
    keypad.put("0", new Vec(1, 3));
    keypad.put("A", new Vec(2, 3));

    availablePositions.addAll(keypad.values());

    startPos = keypad.get("A");
  }
}
