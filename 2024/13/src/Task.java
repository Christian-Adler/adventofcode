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
    for (Machine machine : machines) {
      int tokens = machine.calcMinTokens();
      if (tokens >= 0) sumTokens += tokens;
    }
    // out(machines);
    out("part 1", "tokens", sumTokens);
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    return builder.toString();
  }

  public String toStringSVG() {
    SVG svg = new SVG();
    return svg.toSVGStringAged();
  }


  public String toStringConsole() {
    SVG svg = new SVG();
    return svg.toConsoleString();
  }
}
