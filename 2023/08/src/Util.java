import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Util {
  public static ArrayList<String> str2List(String input) {
    return new ArrayList<String>(Arrays.asList(input.split("")));
  }

  public static String cleanFrom(String input, String... strings) {
    String result = input;
    for (String string : strings) {
      result = result.replace(string, "");
    }
    return result;
  }

  public static void out(Object... str) {
    StringBuilder out = new StringBuilder();
    for (Object o : str) {
      if (!out.isEmpty())
        out.append(" ");
      out.append(o);
    }
    System.out.println(out);
  }

  public static void writeToFile(String content, String fileName) throws IOException {
    Path path = Paths.get(fileName);
    byte[] strToBytes = content.getBytes();

    Files.write(path, strToBytes);
  }

  public static void writeToAOCSvg(String content) throws IOException {
    writeToFile(content, "C:\\Users\\chris\\Downloads\\aoc.svg");
  }

  public static long kgV(long... values) {
    if (values.length == 0) return 0;
    if (values.length == 1) return values[0];

    long maxVal = Arrays.stream(values).max().orElse(-1);
    long kgv = maxVal;
    while (!allDivisible(kgv, values))
      kgv += maxVal;
    return kgv;
  }

  private static boolean allDivisible(long testValue, long[] values) {
    for (long val : values) {
      if (testValue % val != 0)
        return false;
    }
    return true;
  }

}
