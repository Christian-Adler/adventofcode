package aoc;

import java.util.ArrayList;
import java.util.List;

public class Operator extends Packet {
  protected Operator(int packetVersion, int typeId, int packetLength, List<Packet> subPackets) {
    super(packetVersion, typeId, packetLength);
    this.subPackets = subPackets;
  }

  public static Operator parseOperator(int packetVersion, int typeId, String binInput) {

    int actIdx = 6;
    String bin = binInput.substring(actIdx);
    // out(bin);

    int lengthTypeId = Integer.parseInt(binInput.substring(actIdx, actIdx + 1), 2);
    // out("lengthTypeId", lengthTypeId);
    actIdx += 1;

    List<Packet> subPackets = new ArrayList<>();

    if (lengthTypeId == 0) {
      // next 15 bits
      int totalLengthInBitsOfSubPackages = Integer.parseInt(binInput.substring(actIdx, actIdx + 15), 2);
      actIdx += 15;

      // out("totalLengthInBitsOfSubPackages", totalLengthInBitsOfSubPackages);
      String binSubPackages = binInput.substring(actIdx, actIdx + totalLengthInBitsOfSubPackages);

      while (!binSubPackages.isEmpty()) {
        Packet packet = Packet.parse(binSubPackages);
        binSubPackages = binSubPackages.substring(packet.packetLength);
        subPackets.add(packet);
      }

      actIdx += totalLengthInBitsOfSubPackages;
    } else { // lengthTypeId == 1
      // next 11 bits
      int numberOfSubPacketsContained = Integer.parseInt(binInput.substring(actIdx, actIdx + 11), 2);
      actIdx += 11;

      // out("numberOfSubPacketsContained", numberOfSubPacketsContained);
      for (int i = 0; i < numberOfSubPacketsContained; i++) {
        Packet packet = Packet.parse(binInput.substring(actIdx));
        actIdx += packet.packetLength;
        subPackets.add(packet);
      }
    }

    return new Operator(packetVersion, typeId, actIdx, subPackets);
  }

  @Override
  public String toString() {
    return "Operator{" +
        "packetVersion=" + packetVersion +
        ", typeId=" + typeId +
        ", packetLength=" + packetLength +
        ", subPackets=" + subPackets.size() +
        '}';
  }

  @Override
  public long calc() {
    long res = 0;
    if (typeId == 0) {
      for (Packet subPacket : subPackets) {
        res += subPacket.calc();
      }
    } else if (typeId == 1) {
      res = 1;
      for (Packet subPacket : subPackets) {
        res *= subPacket.calc();
      }
    } else if (typeId == 2) {
      res = Long.MAX_VALUE;
      for (Packet subPacket : subPackets) {
        long subCalc = subPacket.calc();
        if (subCalc < res) res = subCalc;
      }
    } else if (typeId == 3) {
      res = Long.MIN_VALUE;
      for (Packet subPacket : subPackets) {
        long subCalc = subPacket.calc();
        if (subCalc > res) res = subCalc;
      }
    } else if (typeId == 5) {
      long val1 = subPackets.get(0).calc();
      long val2 = subPackets.get(1).calc();
      res = val1 > val2 ? 1 : 0;
    } else if (typeId == 6) {
      long val1 = subPackets.get(0).calc();
      long val2 = subPackets.get(1).calc();
      res = val1 < val2 ? 1 : 0;
    } else if (typeId == 7) {
      long val1 = subPackets.get(0).calc();
      long val2 = subPackets.get(1).calc();
      res = val1 == val2 ? 1 : 0;
    }
    return res;
  }
}
