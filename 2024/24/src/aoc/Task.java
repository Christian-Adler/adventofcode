package aoc;

import aoc.gate.Gate;
import aoc.util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Task {

  private final Map<String, Boolean> initialWireVals = new HashMap<>();
  private final List<Gate> gates = new ArrayList<>();
  private final List<Gate> zOutGates = new ArrayList<>();
  private final Map<String, List<Gate>> in2gates = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    if (input.trim().isEmpty()) return;
    if (input.contains("->")) {
      Gate gate = Gate.create(input);
      gates.add(gate);
      addToIn2Gates(gate.in1, gate);
      addToIn2Gates(gate.in2, gate);
      if (gate.out.startsWith("z"))
        zOutGates.add(gate);
    } else {
      String[] split = Util.cleanFrom(input, " ").split(":");
      initialWireVals.put(split[0], split[1].equals("1"));
    }
  }

  private void addToIn2Gates(String gateIn, Gate gate) {
    List<Gate> inGates = in2gates.getOrDefault(gateIn, new ArrayList<>());
    inGates.add(gate);
    in2gates.put(gateIn, inGates);
  }

  public void afterParse() throws Exception {
    // order zOut gates
    zOutGates.sort((o1, o2) -> o2.getOutNo() - o1.getOutNo());
    // out(zOutGates);

    // connect all gates
    connectGates();

    // out(gates);
    initGates(initialWireVals);

    long number = getZOutput();
    out("part 1", "decimal output", number);

    // if (true) {
    //   // buildYFilesInput();
    //   return;
    // }

    // part 2
    // to visualize see https://lukas.fyi/day24/

    int maxX = findMaxIn("x");
    // int maxY = findMaxIn("y");
    //
    // out("maxX", maxX);
    // out("maxY", maxY);

    /*
     * For every x??, y?? (?>0 && < maxX) it should look like
     *   x?? AND1 y?? => OR1
     *   x?? XOR1 y?? => AND2
     *   OR-1 AND2 XOR1 => OR1
     *   OR-1 XOR2 XOR1 => z??
     */

    // find first error
    int firstErr = -1;
    for (int i = 0; i <= maxX; i++) {
      if (!checkUpTo(i, maxX)) {
        out("First wrong x in", i);
        firstErr = i;
        break;
      }
    }

    Set<Gate> wrongOutGates = new LinkedHashSet<>();
    Gate previousOr = null;

    for (int x = firstErr - 1; x < maxX; x++) {
      String xIn = "x" + Util.leftPad(String.valueOf(x), 2, "0");
      List<Gate> xInGates = in2gates.get(xIn);
      if (xInGates.size() != 2) throw new IllegalStateException("Unexpected in gates!");
      Gate AND1 = xInGates.getFirst().type.equals("AND") ? xInGates.getFirst() : xInGates.getLast();
      Gate XOR1 = xInGates.getFirst().type.equals("AND") ? xInGates.getLast() : xInGates.getFirst();

      if (!AND1.type.equals("AND") || !XOR1.type.equals("XOR"))
        throw new IllegalStateException("Unexpected gate types");

      if (previousOr == null && x >= firstErr) out("previous or is null!");

      Gate AND2 = null;

      List<Gate> xor1inGates = XOR1.getConnectedTo();
      if (xor1inGates.size() != 2) {
        out("Unexpected XOR1 (", XOR1.out, ") outs num for x", x);
        wrongOutGates.add(XOR1);
      } else {
        AND2 = xor1inGates.getFirst().type.equals("AND") ? xor1inGates.getFirst() : xor1inGates.getLast();
        Gate XOR2 = xor1inGates.getFirst().type.equals("AND") ? xor1inGates.getLast() : xor1inGates.getFirst();

        if (!AND2.type.equals("AND") || !XOR2.type.equals("XOR"))
          throw new IllegalStateException("Unexpected gate types");

        if (previousOr != null) {
          if (!AND2.getConnectedFrom().contains(previousOr)) {
            Gate unexpectedInputGate = AND2.getConnectedFrom().stream().filter(g -> !g.out.equals(XOR1.out)).findFirst().orElseThrow();
            out("Unexpected AND2 (", unexpectedInputGate.out, ") connected from for x", x);
            wrongOutGates.add(unexpectedInputGate);
          }
          if (!XOR2.getConnectedFrom().contains(previousOr)) {
            Gate unexpectedInputGate = AND2.getConnectedFrom().stream().filter(g -> !g.out.equals(XOR1.out)).findFirst().orElseThrow();
            out("Unexpected XOR2 (", unexpectedInputGate.out, ") connected from for x", x);
            wrongOutGates.add(unexpectedInputGate);
          }
        }

        if (!XOR2.out.equals("z" + Util.leftPad(String.valueOf(x), 2, "0"))) {
          out("Unexpected XOR2 (", XOR2.out, ") out (expected z..) for x", x);
          wrongOutGates.add(XOR2);
        }
      }

      previousOr = null; // reset

      List<Gate> and1inGates = AND1.getConnectedTo();
      if (and1inGates.size() != 1) {
        out("Unexpected AND1 (", AND1.out, ") outs num for x", x);
        wrongOutGates.add(AND1);
      } else {
        Gate OR1 = and1inGates.getFirst();
        if (!OR1.type.equals("OR"))
          throw new IllegalStateException("Unexpected gate type for and1in");
        previousOr = OR1;

        if (OR1.getConnectedTo().size() != 2) {
          out("Unexpected OR1 (", OR1.out, ") outs num for x", x);
          wrongOutGates.add(OR1);
        }

        if (AND2 != null) {
          if (!OR1.getConnectedFrom().contains(AND2)) {
            out("Unexpected AND2 (", AND2.out, ") out for x", x);
            wrongOutGates.add(AND2);
          }
        }
      }
    }

    out("wrongOutGates size", wrongOutGates.size()); // == 8? Dann eigentlich schon fertig ;)
    String orderedOutsBeforeSwap = wrongOutGates.stream().map(g -> g.out).sorted().collect(Collectors.joining(","));
    out("part 2", orderedOutsBeforeSwap); // cqr,ncd,nfj,qnw,vkg,z15,z20,z37

    // check pairs
    ArrayList<Gate> list = new ArrayList<>(wrongOutGates);
    for (int i = 0; i < list.size(); i += 2) {
      Gate g1 = list.get(i);
      Gate g2 = list.get(i + 1);
      switchGatesOut(g1, g2);
    }
    connectGates();
    if (checkUpTo(0, maxX)) {
      out("Found solution :D");
    }

  }

  private boolean checkUpTo(int toX, int maxX) {
    for (int i = 0; i <= toX; i++) {
      Map<String, Boolean> initVals = getFalseInitializedMap(maxX);
      String xIn = "x" + Util.leftPad(String.valueOf(i), 2, "0");
      initVals.put(xIn, true);
      initGates(initVals);
      long zOutput = getZOutput();
      // out("x", i, zOutput);
      double expectedResult = Math.pow(2, i);
      if (zOutput != expectedResult)
        return false;
    }
    return true;
  }

  private static void switchGatesOut(Gate g1, Gate g2) {
    String g1Out = g1.getOut();
    String g2Out = g2.getOut();

    g1.setOut(g2Out);
    g2.setOut(g1Out);
  }

  private void connectGates() {
    for (Gate gate : gates) {
      gate.resetConnected();
    }
    for (Gate gate : gates) {
      List<Gate> connected = in2gates.get(gate.getOut());
      if (connected == null) continue;
      gate.addConnected(connected);
      for (Gate c : connected) {
        c.addConnectedFrom(gate);
      }
    }
  }

  private void buildYFilesInput() {
    // https://www.yworks.com/products/yfiles/playground#create-graph
    Set<String> inOuts = new HashSet<>();
    List<String> nodes = new ArrayList<>();
    List<String> edges = new ArrayList<>();
    for (Gate gate : gates) {
      inOuts.add(gate.in1);
      inOuts.add(gate.in2);
      inOuts.add(gate.out);

      edges.add("{source:\"" + gate.in1 + "\",target:\"" + gate.out + "\"}");
      edges.add("{source:\"" + gate.in2 + "\",target:\"" + gate.out + "\"}");
    }


    for (String inOut : inOuts) {
      nodes.add("{id:\"" + inOut + "\"}");
    }

    out(String.join(",", nodes));
    out(String.join(",", edges));
  }

  private static Map<String, Boolean> getFalseInitializedMap(int maxX) {
    Map<String, Boolean> initValsPrepare = new HashMap<>();
    for (int j = 0; j <= maxX; j++) {
      initValsPrepare.put("x" + Util.leftPad(String.valueOf(j), 2, "0"), false);
      initValsPrepare.put("y" + Util.leftPad(String.valueOf(j), 2, "0"), false);
    }
    return initValsPrepare;
  }

  private int findMaxIn(@SuppressWarnings("SameParameterValue") String inLetter) {
    int maxX = 0;
    while (true) {
      String in = inLetter + Util.leftPad(String.valueOf(maxX + 1), 2, "0");
      if (!in2gates.containsKey(in))
        break;
      maxX++;
    }
    return maxX;
  }

  private long getZOutput() {
    // out(zOutGates);
    String bin = zOutGates.stream().map(Gate::getOutStr).collect(Collectors.joining());
    // out(bin);
    try {
      return Long.parseLong(bin, 2);
    } catch (NumberFormatException numberFormatException) {
      return -1;
    }
  }

  private void initGates(Map<String, Boolean> initVals) {
    resetGates();
    for (Map.Entry<String, Boolean> entry : initVals.entrySet()) {
      String in = entry.getKey();
      Boolean val = entry.getValue();

      List<Gate> inGates = in2gates.get(in);
      for (Gate gate : inGates) {
        gate.in(in, val);
      }
    }
  }

  private void resetGates() {
    gates.forEach(Gate::resetVals);
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
