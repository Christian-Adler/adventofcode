import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
  private final String regexDamaged = "(#+)";
  private final Pattern patternDamaged = Pattern.compile(regexDamaged, Pattern.MULTILINE);

  static AtomicLong sumArrangements = new AtomicLong(0);
  static AtomicLong sumArrangements2 = new AtomicLong(0);
  static AtomicInteger rowCount = new AtomicInteger(0);

  public void init() {
  }

  public void addLine(String input) {
    int rowC = rowCount.incrementAndGet();
    out(rowC);
    String[] split = input.split("\\s+", 2);
    String damagedRow = split[0];
    //    out("damagedRow", damagedRow);
    ArrayList<String> damagedRowAsList = Util.str2List(damagedRow);

    List<Integer> damagedBlocks = Arrays.stream(split[1].split(",")).mapToInt(i -> Integer.parseInt(i.trim())).boxed().toList();
    //    out("damagedBlocks", damagedBlocks);

    long countPossibleCombinations = calcCombinations(new ArrayList<>(damagedRowAsList), new ArrayList<>(damagedBlocks));

    //    out("countPossibleCombinations", countPossibleCombinations);

    sumArrangements.addAndGet(countPossibleCombinations);

    // Part 2
    ArrayList<String> damagedRowAsList2 = new ArrayList<>();
    List<Integer> damagedBlocks2 = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      damagedRowAsList2.addAll(damagedRowAsList);
      damagedBlocks2.addAll(damagedBlocks);
    }
    countPossibleCombinations = calcCombinations(new ArrayList<>(damagedRowAsList2), new ArrayList<>(damagedBlocks2));
    out(rowC + " done");
    sumArrangements2.addAndGet(countPossibleCombinations);
  }

  private record CacheKey2(ArrayList<String> damagedRow, List<Integer> damagedBlocks) {
  }

  private static final Map<CacheKey2, Long> cache2 = new HashMap<>();

  private long calcCombinations(ArrayList<String> damagedRow, List<Integer> damagedBlocks) {
    while (damagedRow.size() > 1 && damagedRow.get(0).equals(".")) {
      damagedRow.remove(0);
    }

    CacheKey2 cacheKey2 = new CacheKey2(damagedRow, damagedBlocks);
    Long cachedValue = cache2.get(cacheKey2);
    if (cachedValue != null) {
      return cachedValue;
    }

    if (damagedBlocks.size() == 1) {
      long res = calcCombinations(damagedRow, damagedBlocks.get(0)).size();
      cache2.put(cacheKey2, res);
      return res;
    }
    else {
      List<Integer> subDamagedBlocks = new ArrayList<>(damagedBlocks);
      int damagedBlock = subDamagedBlocks.remove(0);

      long combinations = 0;

      Set<String> soFarCombinationsFirstBlock = new HashSet<>();

      int whileMax = subDamagedBlocks.stream().mapToInt(Integer::intValue).sum() + subDamagedBlocks.size();

      int actToIdx = damagedBlock;
      while (actToIdx <= damagedRow.size() - whileMax) {
        // abort before end

        ArrayList<String> damagedRowSubList = new ArrayList<>(damagedRow.subList(0, actToIdx));
        Set<String> combinationsFirstBlock = calcCombinations(damagedRowSubList, damagedBlock);

        if (combinationsFirstBlock.isEmpty() && damagedRowSubList.get(damagedRowSubList.size() - 1).equals(".") && damagedRowSubList.contains("#")) {
          break;
        }

        // Diff to so far
        long diff = 0;
        for (String combinationFirstBlock : combinationsFirstBlock) {
          boolean foundSoFarMatch = false;
          for (String soFarCombinationFirstBlock : soFarCombinationsFirstBlock) {
            if (combinationFirstBlock.startsWith(soFarCombinationFirstBlock)) {
              foundSoFarMatch = true;
              break;
            }
          }
          if (!foundSoFarMatch)
            diff++;
        }

        if (diff > 0) {

          // Damaged End = '#' -> sublist2 must start with . (or ? but then set .)
          ArrayList<String> damagedRowSubList2 = new ArrayList<>(damagedRow.subList(actToIdx, damagedRow.size()));
          String first = damagedRowSubList2.get(0);
          if (!first.equals("#")) {
            if (first.equals("?"))
              damagedRowSubList2.set(0, ".");

            long combinationsRest = calcCombinations(damagedRowSubList2, subDamagedBlocks);

            if (combinationsRest > 0) {
              soFarCombinationsFirstBlock = combinationsFirstBlock;
              combinations += diff * combinationsRest;
            }
          }
        }
        actToIdx++;
      }

      cache2.put(cacheKey2, combinations);
      return combinations;
    }
  }

  private record CacheKey(ArrayList<String> damagedRow, int damagedBlock) {
  }

  private static final Map<CacheKey, Set<String>> cache = new HashMap<>();

  private Set<String> calcCombinations(ArrayList<String> damagedRow, int damagedBlock) {
    CacheKey cacheKey = new CacheKey(damagedRow, damagedBlock);
    Set<String> cachedValue = cache.get(cacheKey);
    if (cachedValue != null) {
      return cachedValue;
    }

    Set<String> countPossibleCombinations = new HashSet<>();

    ArrayList<String> damagedR = new ArrayList<>(damagedRow);

    List<Integer> unknownIdxList = new ArrayList<>();
    for (int i = 0; i < damagedRow.size(); i++) {
      if (damagedRow.get(i).equals("?"))
        unknownIdxList.add(i);
    }

    // no variables - ist has to match directly
    if (unknownIdxList.isEmpty()) {
      String damagedRowCheck = String.join("", damagedR);
      List<Integer> damagedGroups = getDamagedGroups(damagedRowCheck);

      if (damagedGroups.size() == 1 && damagedGroups.get(0) == damagedBlock)
        countPossibleCombinations.add(damagedRowCheck);

      cache.put(cacheKey, countPossibleCombinations);
      return countPossibleCombinations;
    }

    int i = -1;
    StringBuilder binary;
    while (true) {
      i++;
      binary = new StringBuilder(Integer.toBinaryString(i));
      if (binary.length() > unknownIdxList.size())
        break;

      while (binary.length() < unknownIdxList.size()) {
        binary.insert(0, "0");
      }

      //      out(i, binary.toString());

      ArrayList<String> binaryAsList = Util.str2List(binary.toString());
      for (int idx = 0; idx < binaryAsList.size(); idx++) {
        String toSet = binaryAsList.get(idx).equals("1") ? "#" : ".";
        damagedR.set(unknownIdxList.get(idx), toSet);
      }

      String damagedRowCheck = String.join("", damagedR);
      List<Integer> damagedGroups = getDamagedGroups(damagedRowCheck);

      if (damagedGroups.size() == 1 && damagedGroups.get(0) == damagedBlock) {
        countPossibleCombinations.add(damagedRowCheck);

        //        out("Found combination:", damagedRowCheck);
      }
    }

    //    out("countPossibleCombinations", countPossibleCombinations);

    cache.put(cacheKey, countPossibleCombinations);
    return countPossibleCombinations;
  }

  public static void afterParse() {
    out("Part 1", "sumArrangements:", sumArrangements);
    out("Part 2", "sumArrangements:", sumArrangements2);
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

  public static void out(Object... str) {
    Util.out(str);
  }

}
