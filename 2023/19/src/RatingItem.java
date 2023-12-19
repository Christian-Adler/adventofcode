public record RatingItem(PartCategory partCategory, long value) {
  @Override
  public String toString() {
    return partCategory + "=" + value;
  }

  public static RatingItem from(String input) {
    String[] split = input.split("=");
    return new RatingItem(PartCategory.from(split[0]), Long.parseLong(split[1]));
  }
}