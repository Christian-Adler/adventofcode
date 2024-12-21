package aoc.keypad;

import aoc.util.Vec;

public class DirectionalKeypad extends KeypadBase {
  public DirectionalKeypad() {
    keypad.put("^", new Vec(1, 0));
    keypad.put("A", new Vec(2, 0));
    keypad.put("<", new Vec(0, 1));
    keypad.put("v", new Vec(1, 1));
    keypad.put(">", new Vec(2, 1));

    availablePositions.addAll(keypad.values());

    startPos = keypad.get("A");
  }
}
