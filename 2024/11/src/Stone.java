public class Stone {
  public long number;
  public Stone prev = null;
  public Stone next = null;

  public Stone(long number) {
    this(number, null, null);
  }

  public Stone(long number, Stone prev, Stone next) {
    this.number = number;
    this.prev = prev;
    this.next = next;
  }

  @Override
  public String toString() {
    return " " + number;
  }
}
