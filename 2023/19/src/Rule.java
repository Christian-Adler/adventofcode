public record Rule(PartCategory partCategory, Operator operator, int compareValue, String targetWorkflow) {
  @Override
  public String toString() {
    if (partCategory == null)
      return "Rule{" +
          targetWorkflow +
          '}';
    return "Rule{" +
        partCategory +
        operator +
        compareValue + ":" +
        targetWorkflow +
        '}';
  }

  public boolean matches(Rating rating) {
    if (partCategory == null) return true;
    for (RatingItem ratingItem : rating.ratingItems) {
      if (ratingItem.partCategory().equals(partCategory)) {
        if (operator.equals(Operator.LT))
          return ratingItem.value() < compareValue;
        if (operator.equals(Operator.GT))
          return ratingItem.value() > compareValue;
      }
    }

    return false;
  }
}
