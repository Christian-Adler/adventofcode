public enum PartCategory {
  x, m, a, s;

  public static PartCategory from(String input) {
    for (PartCategory pc : values()) {
      if (pc.name().equalsIgnoreCase(input)) return pc;
    }
    throw new IllegalStateException("Invalid input '" + input + "' for PartCategory!");
  }
}
