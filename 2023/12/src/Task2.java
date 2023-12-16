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

  public void init() {
  }

  public void addLine(String input) {
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
    ArrayList<Integer> damagedRowAsList2 = new ArrayList<>();
    List<Integer> damagedBlocks2 = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      if (i > 0)
        damagedRowAsList2.add(UNKNOWN);
      damagedRowAsList2.addAll(damagedRowAsList);
      damagedBlocks2.addAll(damagedBlocks);
    }
    countPossibleCombinations = calcCombinations(new ArrayList<>(damagedRowAsList2), new ArrayList<>(damagedBlocks2));
    sumArrangements2.addAndGet(countPossibleCombinations);
  }

  private record CacheKey2(ArrayList<Integer> damagedRow, List<Integer> damagedBlocks) {
  }

  private static final Map<CacheKey2, Long> cache2 = new HashMap<>();

  private long calcCombinations(ArrayList<Integer> damagedRow, List<Integer> damagedBlocks) {
    while (damagedRow.size() > 1 && damagedRow.get(0) == WORKING) {
      damagedRow.remove(0);
    }

    CacheKey2 cacheKey2 = new CacheKey2(damagedRow, damagedBlocks);
    Long cachedValue = cache2.get(cacheKey2);
    if (cachedValue != null) {
      return cachedValue;
    }

    if (damagedBlocks.size() == 1) {
      Set<String> combinationsFirstBlock = calcCombinations(damagedRow, damagedBlocks.get(0));
      long res = combinationsFirstBlock.size();
      cache2.put(cacheKey2, res);
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

      cache2.put(cacheKey2, combinations);
      return combinations;
    }
  }

  private record CacheKey(ArrayList<Integer> damagedRow, int damagedBlock) {
  }

  private static final Map<CacheKey, Set<String>> cache = new HashMap<>();

  private static Set<String> calcCombinations(ArrayList<Integer> damagedRow, int damagedBlock) {
    CacheKey cacheKey = new CacheKey(damagedRow, damagedBlock);
    Set<String> cachedValue = cache.get(cacheKey);
    if (cachedValue != null) {
      return cachedValue;
    }

    // Pruefen, ob damageRow ueberhaupt fuer nur einen damagedBlock moeglich sein kann: gibt es funktionierende zwischen nicht funktionierenden? Dann nicht.
    boolean foundFirstDamaged = false;
    boolean foundWorkingAfterFirstDamaged = false;
    for (Integer i : damagedRow) {
      if (i == DAMAGED) {
        if (foundWorkingAfterFirstDamaged) {
          cache.put(cacheKey, new HashSet<>());
          return new HashSet<>();
        }
        foundFirstDamaged = true;
      }
      else if (i == WORKING) {
        if (foundFirstDamaged)
          foundWorkingAfterFirstDamaged = true;
      }
    }

    // Pruefen, ob es bereits mehr nicht funnktionierende gibt wie damageBlock
    if (damagedRow.stream().filter(i -> i == DAMAGED).count() > damagedBlock) {
      cache.put(cacheKey, new HashSet<>());
      return new HashSet<>();
    }

    Set<String> countPossibleCombinations = new HashSet<>();

    Set<Integer> fixDamagedIdxs = new HashSet<>();
    for (int i = 0; i < damagedRow.size(); i++) {
      if (damagedRow.get(i) == DAMAGED)
        fixDamagedIdxs.add(i);
    }

    for (int i = 0; i <= damagedRow.size() - damagedBlock; i++) {
      boolean foundOne = true;
      Set<Integer> checkNum = new HashSet<>(fixDamagedIdxs);
      for (int j = i; j < i + damagedBlock; j++) {
        if (damagedRow.get(j).equals(WORKING)) {
          foundOne = false;
          break;
        }
        else
          checkNum.add(j);
      }

      if (checkNum.size() != damagedBlock)
        foundOne = false;

      if (foundOne) {
        ArrayList<Integer> combination = new ArrayList<>();
        for (int j = 0; j < damagedRow.size(); j++) {
          combination.add(WORKING);
        }
        for (int j = i; j < i + damagedBlock; j++) {
          combination.set(j, DAMAGED);
        }
        String damagedRowCheck = String.join("", combination.stream().map(String::valueOf).collect(Collectors.joining()));
        countPossibleCombinations.add(damagedRowCheck);
      }
    }

    cache.put(cacheKey, countPossibleCombinations);
    return countPossibleCombinations;
  }

  public static void afterParse() {
    out("Part 1", "sumArrangements:", sumArrangements);
    out("Part 2", "sumArrangements:", sumArrangements2); // >2036407815228
  }

  public static void out(Object... str) {
    Util.out(str);
  }

}
