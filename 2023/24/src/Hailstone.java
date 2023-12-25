public record Hailstone(Pos pos, Pos velocity) {

  public static Hailstone from(String input) {
    String[] split = input.split("@");
    return new Hailstone(Pos.from(split[0]), Pos.from(split[1]));
  }

  public void step() {
    pos.add(velocity);
  }

  public Hailstone stepToNew() {
    return new Hailstone(pos.addToNew(velocity), velocity.copy());
  }
}
