public class Task {

  long abbaCounter = 0;

  public void init() {
  }

  public void addLine(String input) {
    String[] parts = input.split("[\\[\\]]");
    boolean isAbbaEven = false;
    boolean isAbbaOdd = false;
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      boolean isPartAbba = isAbba(part);
      if (i % 2 == 0) {
        isAbbaEven |= isPartAbba;
      } else {
        isAbbaOdd |= isPartAbba;
      }
    }
    if (isAbbaEven && !isAbbaOdd) {
      abbaCounter++;
    }
  }

  public void afterParse() {
    out("abbaCount:", abbaCounter);
  }


  private boolean isAbba(String input) {
    if (input.length() < 4) {
      return false;
    }

    char c0 = input.charAt(0);
    char c1 = input.charAt(1);
    char c2 = input.charAt(2);

    for (int i = 3; i < input.length(); i++) {
      char c = input.charAt(i);

      if (c0 == c && c1 == c2 && c0 != c1) {
        return true;
      }

      c0 = c1;
      c1 = c2;
      c2 = c;
    }
    return false;
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    return builder.toString();
  }

}
