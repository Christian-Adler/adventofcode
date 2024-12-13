import java.util.ArrayList;
import java.util.List;

public class Task {
  private final List<Machine> machines = new ArrayList<>();
  private Machine actMachine = null;

  public void init() {
  }

  public void addLine(String input) {
    if (input.trim().isEmpty()) {
      actMachine = null;
      return;
    }
    if (actMachine == null) {
      actMachine = new Machine();
      machines.add(actMachine);
    }
    actMachine.input(input);
  }

  public void afterParse() {
    long sumTokens = 0;
    long sumTokens2 = 0;
    for (Machine machine : machines) {
      long tokens = machine.calcMinTokens();
      if (tokens >= 0)
        sumTokens += tokens;
      tokens = machine.calcMinTokens2();
      if (tokens >= 0)
        sumTokens2 += tokens;
    }
    // out(machines);
    out("part 1", "tokens", sumTokens);
    out("part 2", "tokens", sumTokens2);
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
