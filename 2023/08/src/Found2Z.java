public class Found2Z {
  private Node node;
  private long found1 = -1;
  private long found2 = -1;

  public Found2Z(Node node) {
    this.node = node;
  }

  boolean found2Z() {
    return found1 > 0 && found2 > 0;
  }

  long getFoundDiff() {
    return found2 - found1;
  }

  public void setNode(Node node, long step) {
    this.node = node;
    if (node.node.endsWith("Z"))
      foundZ(step);
  }

  public Node getNode() {
    return node;
  }

  private void foundZ(long step) {
    if (found1 < 0)
      found1 = step;
    else if (found2 < 0)
      found2 = step;
  }

  @Override
  public String toString() {
    return "Found2Z{" +
        "node=" + node +
        ", found1=" + found1 +
        ", found2=" + found2 +
        ", foundDiff=" + getFoundDiff() +
        '}';
  }
}
