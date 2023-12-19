import java.util.HashMap;
import java.util.Map;

public class Range {
  public Map<PartCategory, CategoryRange> categoryRanges = new HashMap<>();

  public void put(PartCategory partCategory, CategoryRange categoryRange) {
    categoryRanges.put(partCategory, categoryRange);
  }

  public CategoryRange get(PartCategory partCategory) {
    return categoryRanges.get(partCategory);
  }

  public long combinations() {
    long res = 1;
    for (CategoryRange categoryRange : categoryRanges.values()) {
      res *= (categoryRange.max() - categoryRange.min() + 1);
    }
    if (res < 0)
      return 0;
    return res;
  }

  public Range copy() {
    Range range = new Range();
    for (PartCategory partCategory : PartCategory.values()) {
      CategoryRange rangeItem = get(partCategory);
      range.put(partCategory, new CategoryRange(rangeItem.min(), rangeItem.max()));
    }
    return range;
  }
}
