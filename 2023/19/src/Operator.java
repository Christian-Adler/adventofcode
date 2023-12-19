public enum Operator {
  LT("<"), GT(">");

  private final String displayname;

  Operator(String displayname) {
    this.displayname = displayname;
  }

  public static Operator from(String input) {
    for (Operator op : values()) {
      if (op.displayname.equalsIgnoreCase(input)) return op;
    }
    throw new IllegalStateException("Invalid input '" + input + "' for Operator!");
  }

  @Override
  public String toString() {
    return displayname;
  }
}
