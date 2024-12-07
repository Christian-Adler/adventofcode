import java.util.ArrayList;
import java.util.List;

public class Task {

  private final List<Equation> equations = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    equations.add(new Equation(input));
  }

  public void afterParse() {
    long sum = 0;
    long sum2 = 0;
    for (Equation equation : equations) {
      if (equation.isSolvable()) {
        sum += equation.getTestValue();
        sum2 += equation.getTestValue();
      } else if (equation.isSolvable2())
        sum2 += equation.getTestValue();
    }

    out("part 1", "sum", sum);
    out("part 2", "sum", sum2); // ! 3247067810227
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
