import java.util.*;

public class Task {
  private static final String FUEL = "FUEL";
  private static final String FUEL_MULT = "FUEL_MULT";
  private static final String ORE = "ORE";
  private final Map<String, Rule> mapItem2Rule = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    Rule rule = Rule.parse(input);
    Rule prev = mapItem2Rule.put(rule.target.item, rule);
    if (prev != null)
      out("Duplicate Rule Target!!!");
  }

  public void afterParse() {
    // out(mapItem2Rule);
    calcRuleDepth();
    // out(mapItem2Rule);

    // sort rules by depth
    List<Rule> sortedRules = new ArrayList<>(mapItem2Rule.values());
    sortedRules.sort((r1, r2) -> {
      // int depthCompare = Integer.compare(r2.depth, r1.depth);
      // if (depthCompare != 0) return depthCompare;

      if (r2.containsSource(r1.target.item))
        return 1;
      if (r1.containsSource(r2.target.item))
        return -1;
      return Integer.compare(r2.depth, r1.depth);
    });
    // out(sortedRules);
    // for (Rule sortedRule : sortedRules) {
    //   out(sortedRule);
    // }


    // go backwards from FUEl - deepest Rules first
    AmountItem fuel = new AmountItem(FUEL, 1);
    Map<String, AmountItem> map = new HashMap<>();
    map.put(fuel.item, fuel);

    long oreCount = calcOreCount(map, sortedRules);

    out("Part 1", "oreCount:", oreCount);

    // Part 2
    // neue Regel hinzu fuer die Multiplikation
    // In grossen Schritten bis ueber das Ziel - dann in 1er Schritten unter das Ziel
    AmountItem fuelCopy = fuel.copy();
    AmountItem fuelMult = new AmountItem(FUEL_MULT, 1);
    Rule ruleFuelMult = new Rule(fuelMult, Collections.singletonList(fuelCopy));
    sortedRules.add(ruleFuelMult);

    oreCount = 0;
    long fuelMultAmout = 0;
    out(">>>");
    while (oreCount < 1000000000000L) {
      fuelMultAmout += 100;
      fuelCopy.amount = fuelMultAmout;

      map = new HashMap<>();
      map.put(fuelMult.item, fuelMult);
      oreCount = calcOreCount(map, sortedRules);
      // out("Part 2", "oreCount:", oreCount, "fuelMultAmout:", fuelMultAmout);
    }
    out("<<<");
    while (oreCount > 1000000000000L) {
      fuelMultAmout -= 1;
      fuelCopy.amount = fuelMultAmout;

      map = new HashMap<>();
      map.put(fuelMult.item, fuelMult);
      oreCount = calcOreCount(map, sortedRules);
      // out("Part 2", "oreCount:", oreCount, "fuelMultAmout:", fuelMultAmout);
    }
    out("Part 2", "oreCount:", oreCount, "1 trillion ORE produce FUEL:", fuelMultAmout);
  }

  private static long calcOreCount(Map<String, AmountItem> map, List<Rule> sortedRules) {
    long oreCount = 0;
    while (!map.isEmpty()) {
      for (Rule rule : sortedRules) {
        AmountItem amountItem = map.remove(rule.target.item);
        if (amountItem != null) {
          // double div = ((double) amountItem.amount) / rule.target.amount;
          long factor = Math.ceilDiv(amountItem.amount, rule.target.amount);
          // out(div, factor);

          for (AmountItem source : rule.sources) {
            long add = source.amount * factor;
            if (source.item.equals(ORE)) {
              oreCount += add;
            } else {
              AmountItem mapItem = map.get(source.item);
              if (mapItem == null) {
                map.put(source.item, new AmountItem(source.item, add));
              } else {
                mapItem.amount += add;
              }
            }
          }

          break;
        }
      }
    }
    return oreCount;
  }

  private void calcRuleDepth() {
    calcRuleDepth(mapItem2Rule.get(FUEL));
  }

  private int calcRuleDepth(Rule rule) {
    if (rule.depth < 0) {
      int depth = 0;
      for (AmountItem source : rule.sources) {
        if (source.item.equals(ORE))
          depth += 1;
        else
          depth += calcRuleDepth(mapItem2Rule.get(source.item)) + 1;
      }
      rule.depth = depth;
    }
    return rule.depth;
  }

  public void out(Object... str) {
    Util.out(str);
  }


}
