import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Task2 {
  static AtomicLong sumArrangements = new AtomicLong(0);
  static AtomicLong sumArrangements2 = new AtomicLong(0);


  static final int WORKING = 1;
  static final int DAMAGED = 0;
  static final int UNKNOWN = 5;

  private static String inputFileName = "";

  private static final Map<Long, Long> mapRow2Combinations = new HashMap<>();

  private static void putRow2Combination(long row, long combinations) {
    synchronized (mapRow2Combinations) {
      mapRow2Combinations.put(row, combinations);
      try {
        Files.writeString(new File("./evaluated_" + inputFileName).toPath(), mapRow2Combinations.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      }
      catch (IOException e) {
        out("Failed to write file", e);
      }
    }
  }

  public static void readEvaluated(String inputFileName) {
    Task2.inputFileName = inputFileName.replace("./", "");
    try {
      // {1=1, 2=16384, 3=1, 4=16, 5=2500, 6=506250}
      String[] evaluated = Util.cleanFrom(Files.readString(new File("./evaluated_" + Task2.inputFileName).toPath()), "{", "}").split(",");
      for (String eval : evaluated) {
        long[] entry = Arrays.stream(eval.trim().split("=")).mapToLong(Long::parseLong).toArray();
        mapRow2Combinations.put(entry[0], entry[1]);
      }
    }
    catch (IOException e) {
      //
      System.out.println(e);
    }
  }

  public void init() {
  }

  public void addLine(String input, int rowC) {
    out(rowC);
    String[] split = input.split("\\s+", 2);
    String damagedRow = split[0];
    //    out("damagedRow", damagedRow);
    ArrayList<Integer> damagedRowAsList = new ArrayList<>(Util.str2List(damagedRow).stream()
                                                            .map(s -> s.equals(".") ? WORKING : (s.equals("#") ? DAMAGED : UNKNOWN)).toList());

    List<Integer> damagedBlocks = Arrays.stream(split[1].split(",")).mapToInt(i -> Integer.parseInt(i.trim())).boxed().toList();
    //    out("damagedBlocks", damagedBlocks);

    long countPossibleCombinations = calcCombinations(new ArrayList<>(damagedRowAsList), new ArrayList<>(damagedBlocks));

    //    out("countPossibleCombinations", countPossibleCombinations);

    sumArrangements.addAndGet(countPossibleCombinations);

    // Part 2
    Long alreadyEvaluated = mapRow2Combinations.get((long) rowC);
    if (alreadyEvaluated != null) {
      out(rowC + " done (eval)");
      sumArrangements2.addAndGet(alreadyEvaluated);
      return;
    }

    ArrayList<Integer> damagedRowAsList2 = new ArrayList<>();
    List<Integer> damagedBlocks2 = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      if (i > 0)
        damagedRowAsList2.add(UNKNOWN);
      damagedRowAsList2.addAll(damagedRowAsList);
      damagedBlocks2.addAll(damagedBlocks);
    }
    countPossibleCombinations = calcCombinations(new ArrayList<>(damagedRowAsList2), new ArrayList<>(damagedBlocks2));
    out(rowC + " done");
    sumArrangements2.addAndGet(countPossibleCombinations);

    putRow2Combination(rowC, countPossibleCombinations);
  }

  //  private record CacheKey2(ArrayList<Integer> damagedRow, List<Integer> damagedBlocks) {
  //  }
  //
  //  private static final Map<CacheKey2, Long> cache2 = new HashMap<>();

  private long calcCombinations(ArrayList<Integer> damagedRow, List<Integer> damagedBlocks) {
    while (damagedRow.size() > 1 && damagedRow.get(0) == WORKING) {
      damagedRow.remove(0);
    }

    //    CacheKey2 cacheKey2 = new CacheKey2(damagedRow, damagedBlocks);
    //    Long cachedValue = cache2.get(cacheKey2);
    //    if (cachedValue != null) {
    //      return cachedValue;
    //    }

    if (damagedBlocks.size() == 1) {
      long res = calcCombinations(damagedRow, damagedBlocks.get(0)).size();
      //      cache2.put(cacheKey2, res);
      return res;
    }
    else {
      List<Integer> subDamagedBlocks = new ArrayList<>(damagedBlocks);
      int damagedBlock = subDamagedBlocks.remove(0);

      long combinations = 0;

      Set<String> soFarCombinationsFirstBlock = new HashSet<>();

      int atLeastRequiredForSubDamagedBlocks = subDamagedBlocks.stream().mapToInt(Integer::intValue)
                                                 .sum() + subDamagedBlocks.size(); // Summe + fuer jeden DamagedBlock muss eine arbeitende Feder dazwischen sein
      int whileMax = damagedRow.size() - atLeastRequiredForSubDamagedBlocks;

      // Suche, ob es nicht eine bessere Abbruchedinungung gibt
      int idxFirstDamaged = damagedRow.indexOf(DAMAGED);
      if (idxFirstDamaged >= 0) {
        int idxFirstWorkingAfterFirstDamaged = damagedRow.subList(idxFirstDamaged, damagedRow.size()).indexOf(WORKING);
        if (idxFirstWorkingAfterFirstDamaged > -1) {
          if (idxFirstDamaged + idxFirstWorkingAfterFirstDamaged < whileMax)
            whileMax = idxFirstDamaged + idxFirstWorkingAfterFirstDamaged;
        }
      }

      //      int firstDamageEndIdx = String.join("", damagedRow).indexOf("#.");
      //      if (firstDamageEndIdx >= 0 && firstDamageEndIdx < whileMax) {
      //        whileMax = firstDamageEndIdx;
      //      }

      int actToIdx = damagedBlock;
      while (actToIdx <= whileMax) {
        // abort before end

        ArrayList<Integer> damagedRowSubList = new ArrayList<>(damagedRow.subList(0, actToIdx));
        Set<String> combinationsFirstBlock = calcCombinations(damagedRowSubList, damagedBlock);

        if (combinationsFirstBlock.isEmpty() && damagedRowSubList.get(damagedRowSubList.size() - 1) == WORKING && damagedRowSubList.contains(DAMAGED)) {
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
          ArrayList<Integer> damagedRowSubList2 = new ArrayList<>(damagedRow.subList(actToIdx, damagedRow.size()));
          Integer first = damagedRowSubList2.get(0);
          if (first != DAMAGED) {
            if (first == UNKNOWN)
              damagedRowSubList2.set(0, WORKING);

            long combinationsRest = calcCombinations(damagedRowSubList2, subDamagedBlocks);

            if (combinationsRest > 0) {
              soFarCombinationsFirstBlock = combinationsFirstBlock;
              combinations += diff * combinationsRest;
            }
          }
        }
        actToIdx++;
      }

      //      cache2.put(cacheKey2, combinations);
      return combinations;
    }
  }

  private record CacheKey(ArrayList<Integer> damagedRow, int damagedBlock) {
  }

  private static final Map<CacheKey, Set<String>> cache = new HashMap<>();

  private Set<String> calcCombinations(ArrayList<Integer> damagedRow, int damagedBlock) {

    // Pruefen, ob damageRow ueberhaupt fuer nur einen damagedBlock moeglich sein kann: gibt es funktionierende zwischen nicht funktionierenden? Dann nicht.
    boolean foundFirstDamaged = false;
    boolean foundWorkingAfterFirstDamaged = false;
    for (Integer i : damagedRow) {
      if (i == DAMAGED) {
        if (foundWorkingAfterFirstDamaged)
          return new HashSet<>();
        foundFirstDamaged = true;
      }
      else if (i == WORKING) {
        if (foundFirstDamaged)
          foundWorkingAfterFirstDamaged = true;
      }
    }

    // Pruefen, ob es bereits mehr nicht funnktionierende gibt wie damageBlock
    if (damagedRow.stream().filter(i -> i == DAMAGED).count() > damagedBlock)
      return new HashSet<>();

    CacheKey cacheKey = new CacheKey(damagedRow, damagedBlock);
    Set<String> cachedValue = cache.get(cacheKey);
    if (cachedValue != null) {
      return cachedValue;
    }

    Set<String> countPossibleCombinations = new HashSet<>();

    ArrayList<Integer> damagedR = new ArrayList<>(damagedRow);

    List<Integer> unknownIdxList = new ArrayList<>();
    for (int i = 0; i < damagedRow.size(); i++) {
      if (damagedRow.get(i) == UNKNOWN)
        unknownIdxList.add(i);
    }

    // no variables - ist has to match directly
    if (unknownIdxList.isEmpty()) {
      String damagedRowCheck = String.join("", damagedR.stream().map(String::valueOf).collect(Collectors.joining()));
      List<Integer> damagedGroups = getDamagedGroups(damagedR);

      if (damagedGroups.size() == 1 && damagedGroups.get(0) == damagedBlock)
        countPossibleCombinations.add(damagedRowCheck);

      cache.put(cacheKey, countPossibleCombinations);
      return countPossibleCombinations;
    }

    int i = -1;
    while (true) {
      i++;
      StringBuilder binary = new StringBuilder(Integer.toBinaryString(i));
      if (binary.length() > unknownIdxList.size())
        break;

      while (binary.length() < unknownIdxList.size()) {
        binary.insert(0, "0");
      }

      //      out(i, binary.toString());

      ArrayList<String> binaryAsList = Util.str2List(binary.toString());
      for (int idx = 0; idx < binaryAsList.size(); idx++) {
        Integer toSet = binaryAsList.get(idx).equals("1") ? DAMAGED : WORKING;
        damagedR.set(unknownIdxList.get(idx), toSet);
      }

      List<Integer> damagedGroups = getDamagedGroups(damagedR);

      if (damagedGroups.size() == 1 && damagedGroups.get(0) == damagedBlock) {
        String damagedRowCheck = String.join("", damagedR.stream().map(String::valueOf).collect(Collectors.joining()));
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

  private static List<Integer> getDamagedGroups(ArrayList<Integer> damagedR) {
    List<Integer> damagedGroupsTest = new ArrayList<>();
    boolean foundDamage = false;
    int actDamageLength = 0;
    for (Integer s : damagedR) {
      if (s == DAMAGED) {
        foundDamage = true;
        actDamageLength++;
      }
      else {
        if (foundDamage) {
          damagedGroupsTest.add(actDamageLength);
          actDamageLength = 0;
        }
        foundDamage = false;
      }
    }
    if (foundDamage) {
      damagedGroupsTest.add(actDamageLength);
    }
    return damagedGroupsTest;
  }

  public static void out(Object... str) {
    Util.out(str);
  }

}
