import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {
  private final String regexDamaged = "(#+)";
  private final String regexUnknown = "(\\?)";
  private final Pattern patternDamaged = Pattern.compile(regexDamaged, Pattern.MULTILINE);
  private final Pattern patternUnknown = Pattern.compile(regexUnknown, Pattern.MULTILINE);

  long sumArrangements = 0;

  public void init() {
  }

  public void addLine(String input) {
    String[] split = input.split("\\s+", 2);
    String damagedRow = split[0];
    out("damagedRow", damagedRow);
    List<Integer> damagedBlocks = Arrays.stream(split[1].split(",")).mapToInt(i -> Integer.parseInt(i.trim())).boxed().toList();
    out("damagedBlocks", damagedBlocks);

    List<Integer> unknownIdxList = getUnknownIdxList(damagedRow);
    ArrayList<String> damagedRowAsList = Util.str2List(damagedRow);

    int countPossibleCombinations = 0;

    int i = -1;
    StringBuilder binary = new StringBuilder("0");
    while (true) {
      i++;
      binary = new StringBuilder(Integer.toBinaryString(i));
      if (binary.length() > unknownIdxList.size())
        break;

      while (binary.length() < unknownIdxList.size())
        binary.insert(0, "0");

//      out(i, binary.toString());

      ArrayList<String> binaryAsList = Util.str2List(binary.toString());
      for (int idx = 0; idx < binaryAsList.size(); idx++) {
        String toSet = binaryAsList.get(idx).equals("1") ? "#" : ".";
        damagedRowAsList.set(unknownIdxList.get(idx), toSet);
      }

      String damagedRowCheck = String.join("", damagedRowAsList);
      List<Integer> damagedGroups = getDamagedGroups(damagedRowCheck);

      if (damagedBlocks.equals(damagedGroups)) {
        countPossibleCombinations++;
//        out("Found combination:", damagedRowCheck);
      }
    }

    out("countPossibleCombinations", countPossibleCombinations);

    sumArrangements += countPossibleCombinations;
  }

  public void afterParse() {
    out("Part 1", "sumArrangements:", sumArrangements); // > 6868
  }

  private List<Integer> getDamagedGroups(String damagedRow) {
    Matcher matcher = patternDamaged.matcher(damagedRow);
    List<Integer> damagedGroups = new ArrayList<>();
    while (matcher.find()) {
//      out("Full match: " + matcher.group(0));
      int startIdx = matcher.start();
      int endIdx = matcher.end();
//      out(startIdx, endIdx);
      damagedGroups.add(endIdx - startIdx);
    }
    return damagedGroups;
  }

  private List<Integer> getUnknownIdxList(String damagedRow) {
    Matcher matcher = patternUnknown.matcher(damagedRow);
    List<Integer> unknownIdxList = new ArrayList<>();
    while (matcher.find()) {
      int startIdx = matcher.start();
      unknownIdxList.add(startIdx);
    }
    return unknownIdxList;
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
