package aoc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

  public static String leftPad(Object input, int length, String padStr) {
    StringBuilder builder = new StringBuilder(input.toString());
    int padLength = length - input.toString().length();
    if (padLength > 0)
      builder.append(padStr.repeat(padLength));
    return builder.toString();
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
    List<Long> asList = new ArrayList<>();
    for (long value : values) {
      asList.add(value);
    }
    return kgV(asList);
  }

  public static long kgV(List<Long> values) {
    long actLcm = 1;
    for (Long value : values) {
      actLcm = lcm(actLcm, value);
    }
    return actLcm;
  }


  public static long lcm(long number1, long number2) {
    if (number1 == 0 || number2 == 0) {
      return 0;
    }

    Map<Long, Long> primeFactorsForNum1 = getPrimeFactors(number1);
    Map<Long, Long> primeFactorsForNum2 = getPrimeFactors(number2);

    Set<Long> primeFactorsUnionSet = new HashSet<>(primeFactorsForNum1.keySet());
    primeFactorsUnionSet.addAll(primeFactorsForNum2.keySet());

    long lcm = 1;

    for (Long primeFactor : primeFactorsUnionSet) {
      lcm *= (long) Math.pow(primeFactor,
          Math.max(primeFactorsForNum1.getOrDefault(primeFactor, 0L),
              primeFactorsForNum2.getOrDefault(primeFactor, 0L)));
    }

    return lcm;
  }

  public static Map<Long, Long> getPrimeFactors(long number) {
    long absNumber = Math.abs(number);

    Map<Long, Long> primeFactorsMap = new HashMap<>();

    for (long factor = 2; factor <= absNumber; factor++) {
      while (absNumber % factor == 0) {
        Long power = primeFactorsMap.get(factor);
        if (power == null) {
          power = 0L;
        }
        primeFactorsMap.put(factor, power + 1);
        absNumber /= factor;
      }
    }

    return primeFactorsMap;
  }
}
