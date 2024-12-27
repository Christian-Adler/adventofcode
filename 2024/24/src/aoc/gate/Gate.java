package aoc.gate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class Gate {
  public final String type;
  public final String in1;
  public final String in2;
  public String out;
  protected Boolean in1Val = null;
  protected Boolean in2Val = null;
  protected Boolean outVal = null;
  private final List<Gate> connectedTo = new ArrayList<>();
  private final List<Gate> connectedFrom = new ArrayList<>();

  protected Gate(String type, String in1, String in2, String out) {
    this.type = type;
    this.in1 = in1;
    this.in2 = in2;
    this.out = out;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Gate gate = (Gate) o;
    return Objects.equals(in1, gate.in1) && Objects.equals(in2, gate.in2) && Objects.equals(out, gate.out);
  }

  @Override
  public int hashCode() {
    return Objects.hash(in1, in2, out);
  }

  public static Gate create(String input) {
    List<String> remove = List.of("->", "AND", "OR", "XOR");
    List<String> list = Arrays.stream(input.split(" ")).filter(i -> !remove.contains(i)).toList();
    if (input.contains("AND"))
      return new AND(list.get(0), list.get(1), list.get(2));
    else if (input.contains("XOR")) // before OR !!
      return new XOR(list.get(0), list.get(1), list.get(2));
    else if (input.contains("OR"))
      return new OR(list.get(0), list.get(1), list.get(2));
    else
      throw new IllegalArgumentException("No gate!: " + input);
  }

  public String getOut() {
    return out;
  }

  public void setOut(String out) {
    this.out = out;
  }

  public void addConnected(List<Gate> gates) {
    connectedTo.addAll(gates);
  }

  public void resetConnected() {
    connectedTo.clear();
  }

  public List<Gate> getConnectedTo() {
    return connectedTo;
  }

  public void addConnectedFrom(Gate gates) {
    connectedFrom.add(gates);
  }

  public List<Gate> getConnectedFrom() {
    return connectedFrom;
  }

  public void resetVals() {
    in1Val = null;
    in2Val = null;
    outVal = null;
  }

  public void in(String inWire, boolean val) {
    if (inWire.equals(in1))
      in1Val = val;
    else if (inWire.equals(in2))
      in2Val = val;
    // else
    //   throw new IllegalStateException("unknown in");

    if (in1Val != null && in2Val != null) {
      eval();
      for (Gate gate : connectedTo) {
        gate.in(out, outVal);
      }
    }
  }

  public int getOutNo() {
    return Integer.parseInt(out.substring(1));
  }

  public String getOutStr() {
    return outVal == null ? "INVALID" : (outVal ? "1" : "0");
  }

  protected abstract void eval();
}
