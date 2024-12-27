package aoc.gate;

public class XOR extends Gate {
  public XOR(String in1, String in2, String out) {
    super("XOR", in1, in2, out);
  }

  @Override
  protected void eval() {
    outVal = in1Val && !in2Val || !in1Val && in2Val;
  }

  @Override
  public String toString() {
    return "XOR{" +
        "in1='" + in1 + '\'' +
        ", in2='" + in2 + '\'' +
        ", out='" + out + '\'' +
        ", in1Val=" + in1Val +
        ", in2Val=" + in2Val +
        ", outVal=" + outVal +
        '}';
  }
}
