public class Lens {
  public String label;
  public int focalLength;

  public Lens(String label, int focalLength) {
    this.label = label;
    this.focalLength = focalLength;
  }

  @Override
  public String toString() {
    return "[" + label + ' ' + focalLength + "]";
  }
}
