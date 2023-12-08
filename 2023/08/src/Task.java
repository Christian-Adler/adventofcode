import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
  private RLInstruction rlInstruction;
  private final Map<String, Node> nodeMap = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    if (input.trim().isEmpty()) return;

    if (rlInstruction == null) {
      rlInstruction = new RLInstruction(input);
      return;
    }

    String[] split = input.split("=");
    String[] splitLR = Util.cleanFrom(split[1], "(", ")").split(",");
    Node node = new Node(split[0].trim(), splitLR[0].trim(), splitLR[1].trim());
    nodeMap.put(node.node, node);
  }

  public void afterParse() {
//    out(nodeMap);
//    out(rlInstruction);

    long steps = 0;
    Node actNode = nodeMap.get("AAA");

    while (actNode != null && !actNode.node.equals("ZZZ")) {
      steps++;
      String next = rlInstruction.getNext();
      if (next.equals("L"))
        actNode = nodeMap.get(actNode.left);
      else
        actNode = nodeMap.get(actNode.right);
    }

    out("Part 1 steps:", steps);

    // Part 2
    List<Found2Z> actNodes = new ArrayList<>();
    for (Node node : nodeMap.values()) {
      if (node.node.endsWith("A"))
        actNodes.add(new Found2Z(node));
    }

    rlInstruction.reset();
    steps = 0;

    // laufen, bis alle 2x das z-Ende gefunden haben - 2tes -1 erstes  und dann von allem kgv

    while (!allEndsWithZ(actNodes)) {
      steps++;
      String next = rlInstruction.getNext();

      for (Found2Z found2Z : actNodes) {

        Node nextNode;
        if (next.equals("L"))
          nextNode = nodeMap.get(found2Z.getNode().left);
        else
          nextNode = nodeMap.get(found2Z.getNode().right);
        found2Z.setNode(nextNode, steps);
      }
    }

    long[] stepZDiffs = actNodes.stream().mapToLong(Found2Z::getFoundDiff).toArray();
    long actSteps = Util.kgV(stepZDiffs);

    out("Part 2 steps:", actSteps);
  }

  private static boolean allEndsWithZ(List<Found2Z> nodes) {
    for (Found2Z found2Z : nodes) {
//      if (!found2Z.found2Z.endsWith("Z"))
      if (!found2Z.found2Z())
        return false;
    }
    return true;
  }

  private static boolean allDividabel(long testValue, long[] values) {
    for (long val : values) {
      if (testValue % val != 0)
        return false;
    }
    return true;
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
