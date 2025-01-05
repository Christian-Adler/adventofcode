package aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ALU {
  private static final List<String> knownVariables = List.of("w", "x", "y", "z");
  private final Map<String, Integer> variables = new HashMap<>();
  private final List<Instruction> instructions = new ArrayList<>();
  private final List<Integer> input = new ArrayList<>();

  public ALU() {
    knownVariables.forEach(v -> variables.put(v, 0));
  }

  public void setInput(long in) {
    input.clear();
    knownVariables.forEach(v -> variables.put(v, 0));

    long w = in;
    while (w > 0) {
      int v = (int) (w % 10);
      input.addFirst(v);
      w = w / 10;
    }
  }


  public void addInstruction(String instruction) {
    String[] split = instruction.split(" ");
    String cmd = split[0];
    String p1 = split[1];
    String p2 = split.length > 2 ? split[2] : null;
    switch (cmd) {
      case "inp" -> instructions.add(() -> setVar(p1, input.removeFirst()));
      case "add" -> instructions.add(() -> setVar(p1, variables.get(p1) + getByVarOrNo(p2)));
      case "mul" -> instructions.add(() -> setVar(p1, variables.get(p1) * getByVarOrNo(p2)));
      case "div" -> instructions.add(() -> setVar(p1, (variables.get(p1) / getByVarOrNo(p2))));
      case "mod" -> instructions.add(() -> setVar(p1, (variables.get(p1) % getByVarOrNo(p2))));
      case "eql" -> instructions.add(() -> setVar(p1, (variables.get(p1) == getByVarOrNo(p2) ? 1 : 0)));
      default -> throw new IllegalStateException("unexpected instruction");
    }
  }

  private void setVar(String var, int val) {
    variables.put(var, val);
    // if (var.equals("z"))
    //   System.out.println(variables);
  }

  private int getByVarOrNo(String p) {
    if (knownVariables.contains(p))
      return variables.get(p);
    return Integer.parseInt(p);
  }

  // public Integer exec() {
  //   for (Instruction instruction : instructions) {
  //     instruction.exec();
  //   }
  //   System.out.println(variables);
  //   return variables.get("z");
  // }
  public Map<String, Integer> exec() {
    for (Instruction instruction : instructions) {
      instruction.exec();
    }
    // System.out.println(variables);
    return variables;
  }


  @FunctionalInterface
  public interface Instruction {
    void exec();
  }

}
