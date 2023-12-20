public record Pulse(String sourceModule, boolean high, String targetModule) {
  @Override
  public String toString() {
    return
        sourceModule +
            " -" + (high ? "high" : "low") +
            "-> " + targetModule;
  }
}
