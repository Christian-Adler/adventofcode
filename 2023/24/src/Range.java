public record Range(long min, long max) {
  public boolean inRange(double val) {
    return val >= min && val <= max;
  }
}
