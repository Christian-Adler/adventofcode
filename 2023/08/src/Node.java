public class Node {
  public final String node;
  public final String left;
  public final String right;

  public Node(String node, String left, String right) {
    this.node = node;
    this.left = left;
    this.right = right;
  }

  @Override
  public String toString() {
    return "Node{" +
        "node='" + node + '\'' +
        ", left='" + left + '\'' +
        ", right='" + right + '\'' +
        '}';
  }
}
