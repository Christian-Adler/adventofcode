import java.util.ArrayList;
import java.util.List;

public class Task {

  private final List<Report> reports = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    Report report = new Report(input);
    report.checkSafe();
    report.checkSafe2();
    // out(report);
    reports.add(report);
  }

  public void afterParse() {
    // part 1
    out("safe reports:", reports.stream().filter(Report::isSafe1).count());
    out("safe reports 2:", reports.stream().filter(Report::isSafe2).count()); // > 369
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
