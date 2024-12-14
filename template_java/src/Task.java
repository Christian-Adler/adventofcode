public class Task {

  public void init() {
  }

  public void addLine(String input) {
  }

  public void afterParse() {
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    return toStringConsole();
  }

  public String toStringSVG() {
    Img img = new Img();
    return img.toSVGStringAged();
  }


  public String toStringConsole() {
    Img img = new Img();
    return img.toConsoleString();
  }
}
