package aoc;

import java.util.*;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  public void part1(List<String> lines, Object... params) throws Exception {
    Set<String> requiredFields = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"); //, "cid");

    List<Map<String, String>> passports = parsePassports(lines);

    long countValid = passports.stream().filter(p -> hasAllRequiredFields(p, requiredFields)).count();

    out("part 1", countValid);

    countValid = passports.stream().filter(p -> isValid(p, requiredFields)).count();

    out("part 2", countValid);
  }

  private static List<Map<String, String>> parsePassports(List<String> lines) {
    List<Map<String, String>> passports = new ArrayList<>();

    Map<String, String> actPassport = null;

    for (String line : lines) {
      if (line.isEmpty()) {
        actPassport = null;
      } else {
        if (actPassport == null) {
          actPassport = new HashMap<>();
          passports.add(actPassport);
        }
        String[] split = line.split(" ");
        for (String s : split) {
          String[] kv = s.split(":");
          actPassport.put(kv[0], kv[1]);
        }
      }
    }
    return passports;
  }

  private static boolean hasAllRequiredFields(Map<String, String> passport, Set<String> requiredFields) {
    return passport.keySet().containsAll(requiredFields);
  }

  private static boolean isValid(Map<String, String> passport, Set<String> requiredFields) {
    if (!hasAllRequiredFields(passport, requiredFields)) return false;
    try {
      for (String requiredField : requiredFields) {
        if (requiredField.equals("byr")) {
          int val = Integer.parseInt(passport.get(requiredField));
          if (val < 1920 || val > 2002) return false;
        } else if (requiredField.equals("iyr")) {
          int val = Integer.parseInt(passport.get(requiredField));
          if (val < 2010 || val > 2020) return false;
        } else if (requiredField.equals("eyr")) {
          int val = Integer.parseInt(passport.get(requiredField));
          if (val < 2020 || val > 2030) return false;
        } else if (requiredField.equals("hgt")) {
          String val = passport.get(requiredField);
          int height = Integer.parseInt(val.substring(0, val.length() - 2));
          if (val.endsWith("cm")) {
            if (height < 150 || height > 193) return false;
          } else if (val.endsWith("in")) {
            if (height < 59 || height > 76) return false;
          }
        } else if (requiredField.equals("hcl")) {
          String val = passport.get(requiredField);
          if (!val.matches("#[0-9a-f]{6}")) return false;
        } else if (requiredField.equals("ecl")) {
          String val = passport.get(requiredField);
          if (!val.matches("amb|blu|brn|gry|grn|hzl|oth")) return false;
        } else if (requiredField.equals("pid")) {
          String val = passport.get(requiredField);
          if (!val.matches("[0-9]{9}")) return false;
        }
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
