import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {

  private final String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
  private final Pattern pattern = Pattern.compile(regex);

  final String regex2 = "_|(do\\(\\))|don't\\(\\)|(mul\\((\\d{1,3}),(\\d{1,3})\\))";
  private final Pattern pattern2 = Pattern.compile(regex2);


  long sum = 0;

  long sum2 = 0;
  boolean enabled = true;
  int count = 0;

  public void init() {
  }

  public void addLine(String input) {
    final Matcher matcher = pattern.matcher(input);

    while (matcher.find()) {
      // System.out.println("Full match: " + matcher.group(0));
      // for (int i = 1; i <= matcher.groupCount(); i++) {
      //   System.out.println("Group " + i + ": " + matcher.group(i));
      // }
      long val1 = Long.parseLong(matcher.group(1));
      long val2 = Long.parseLong(matcher.group(2));
      sum += val1 * val2;
    }

    final Matcher matcher2 = pattern2.matcher(input);

    while (matcher2.find()) {
      String full = matcher2.group(0);
      // System.out.println("Full match: " + full);
      // for (int i = 1; i <= matcher2.groupCount(); i++) {
      //   System.out.println("Group " + i + ": " + matcher2.group(i));
      // }
      if (full.equals("do()"))
        enabled = true;
      else if (full.equals("don't()")) {
        enabled = false;
      } else if (enabled && full.startsWith("mul")) {
        count++;
        long val1 = Long.parseLong(matcher2.group(3));
        long val2 = Long.parseLong(matcher2.group(4));
        sum2 += val1 * val2;

        // out(count, sum2, "v1", val1, "v2", val2);
      }
    }
  }

  public void afterParse() {
    out("count", count);
    out("Part 1", "sum", sum);
    out("Part 2", "sum", sum2);
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
