package aoc;

public class Literal extends Packet {
  public final long decimal;

  protected Literal(int packetVersion, int typeId, int packetLength, long decimal) {
    super(packetVersion, typeId, packetLength);
    this.decimal = decimal;
  }

  public static Literal parseLiteral(int packetVersion, int typeId, String binInput) {

    int actIdx = 6;
    // out("literal", binInput);

    StringBuilder binNo = new StringBuilder();
    while (true) {
      int groupPrefix = Integer.parseInt(binInput.substring(actIdx, actIdx + 1));

      binNo.append(binInput, actIdx + 1, actIdx + 5);

      actIdx += 5;

      if (groupPrefix == 0)
        break;
    }

    // out(binNo.toString());
    long decimal = Long.parseLong(binNo.toString(), 2);
    // out("literal", decimal);
    return new Literal(packetVersion, typeId, actIdx, decimal);
  }

  @Override
  public String toString() {
    return "Literal{" +
        "decimal=" + decimal +
        ", packetVersion=" + packetVersion +
        ", typeId=" + typeId +
        ", packetLength=" + packetLength +
        '}';
  }

  @Override
  public long calc() {
    return decimal;
  }
}
