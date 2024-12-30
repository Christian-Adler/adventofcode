package aoc;

import java.util.List;

public abstract class Packet {

  public final int packetVersion;
  public final int typeId;
  protected List<Packet> subPackets = null;
  public final int packetLength;


  protected Packet(int packetVersion, int typeId, int packetLength) {
    this.packetVersion = packetVersion;
    this.typeId = typeId;
    this.packetLength = packetLength;
  }

  public long sumVersionNos() {
    long sum = packetVersion;
    if (subPackets != null) {
      for (Packet subPacket : subPackets) {
        sum += subPacket.sumVersionNos();
      }
    }
    return sum;
  }

  public abstract long calc();

  protected static Packet parse(String bin) {
    // out(bin);

    int packetVersion = Integer.parseInt(bin.substring(0, 3), 2);
    int typeId = Integer.parseInt(bin.substring(3, 6), 2);
    // out("packetVersion", packetVersion);
    // out("typeId", typeId);

    if (typeId == 4)
      return Literal.parseLiteral(packetVersion, typeId, bin);

    return Operator.parseOperator(packetVersion, typeId, bin);
  }

  public static Packet eval(String binInput) {
    // out(binInput);

    return parse(binInput);
  }
}
