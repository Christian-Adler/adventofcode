package aoc;

import aoc.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public void exec(List<String> lines, Object... params) throws Exception {
    Interpreter interpreter = new Interpreter(lines);
    long res = interpreter.run();
    out("part 1: ", res);


    Map<Integer, String> visited = interpreter.getVisited();
    List<Integer> accIdxs = visited.entrySet().stream().filter(e -> e.getValue().equals("acc")).map(Map.Entry::getKey).toList();
    accIdxs.forEach(visited::remove);

    for (int changeIdx : visited.keySet()) {
      ArrayList<String> changedLines = new ArrayList<>(lines);
      String changeLine = changedLines.get(changeIdx);
      if (changeLine.startsWith("jmp"))
        changedLines.set(changeIdx, changeLine.replace("jmp", "nop"));
      else /*if(changeLine.startsWith("nop"))*/
        changedLines.set(changeIdx, changeLine.replace("nop", "jmp"));

      interpreter = new Interpreter(changedLines);
      res = interpreter.run();
      if (interpreter.isFoundLoop())
        continue;
      out("part 2: ", res);
      break;
    }
  }
}
