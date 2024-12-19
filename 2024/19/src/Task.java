import java.util.*;
import java.util.stream.Stream;

public class Task {

  private final List<List<String>> patterns = new ArrayList<>();
  private final List<List<String>> desiredDesigns = new ArrayList<>();
  private final Set<List<String>> notPossible = new HashSet<>();
  private final Map<List<String>, Long> possible2Variants = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    if (input.isEmpty()) return;
    if (patterns.isEmpty()) {
      patterns.addAll(Stream.of(Util.cleanFrom(input, " ").split(",")).map(Util::str2List).toList());
      patterns.sort((o1, o2) -> o2.size() - o1.size());
    } else {
      desiredDesigns.add(Util.str2List(input));
    }
  }

  public void afterParse() throws Exception {
    long possibleDesigns = 0;
    // int count = 0;
    for (List<String> desiredDesign : desiredDesigns) {
      // count++;      out(count, "/", desiredDesigns.size());
      if (isDesignPossible(desiredDesign))
        possibleDesigns++;
    }
    out("part 1", "possibleDesigns", possibleDesigns);

    long sumVariants = 0;
    // count = 0;
    for (List<String> desiredDesign : desiredDesigns) {
      // count++;      out(count, "/", desiredDesigns.size());
      sumVariants += countVariantsDesignsPossible(desiredDesign);
    }
    out("part 2", "sum variants", sumVariants);
  }

  private boolean isDesignPossible(List<String> desiredDesign) {
    // out(desiredDesign);
    if (notPossible.contains(desiredDesign)) return false;

    for (List<String> pattern : patterns) {
      if (desiredDesign.equals(pattern))
        return true;
      if (desiredDesign.size() >= pattern.size() && desiredDesign.subList(0, pattern.size()).equals(pattern)) {
        if (isDesignPossible(desiredDesign.subList(pattern.size(), desiredDesign.size())))
          return true;
      }
    }

    notPossible.add(desiredDesign);
    return false;
  }

  private long countVariantsDesignsPossible(List<String> desiredDesign) {
    // out(desiredDesign);
    if (notPossible.contains(desiredDesign)) return 0;
    Long alreadyDetermined = possible2Variants.get(desiredDesign);
    if (alreadyDetermined != null)
      return alreadyDetermined;

    long sum = 0;

    for (List<String> pattern : patterns) {
      if (desiredDesign.equals(pattern)) {
        sum++;
        continue;
      }
      if (desiredDesign.size() >= pattern.size() && desiredDesign.subList(0, pattern.size()).equals(pattern)) {
        sum += countVariantsDesignsPossible(desiredDesign.subList(pattern.size(), desiredDesign.size()));
      }
    }

    if (sum == 0)
      notPossible.add(desiredDesign);
    else
      possible2Variants.put(desiredDesign, sum);
    return sum;
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
