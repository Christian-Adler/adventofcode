package aoc.interpreter;

import aoc.interpreter.instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Interpreter {
  AtomicLong accumulator = new AtomicLong(0);
  private final ArrayList<Instruction> instructions = new ArrayList<>();

  private final Map<Integer, String> visited = new HashMap<>();
  private boolean foundLoop = false;

  public Interpreter(List<String> lines) {
    instructions.addAll(parse(lines));
  }

  public long run() {
    int instructionPointer = 0;
    while (true) {


      if (instructionPointer < instructions.size()) {
        Instruction instruction = instructions.get(instructionPointer);
        if (visited.containsKey(instructionPointer)) {
          foundLoop = true;
          break;
        }
        visited.put(instructionPointer, instruction.getClass().getSimpleName());
        instructionPointer += instruction.exec(accumulator);
      } else
        break;
    }
    // System.out.println(visited);
    return accumulator.get();
  }

  public boolean isFoundLoop() {
    return foundLoop;
  }

  public Map<Integer, String> getVisited() {
    return visited;
  }

  public static List<Instruction> parse(List<String> lines) {
    List<Instruction> instructions = new ArrayList<>();
    for (String line : lines) {
      instructions.add(Instruction.parse(line));
    }
    return instructions;
  }
}
