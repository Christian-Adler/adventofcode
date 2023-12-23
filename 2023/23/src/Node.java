import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node {
  Pos pos;
  Map<Node, Integer> neighbors = new HashMap<>();

  public Node(Pos pos) {
    this.pos = pos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Node node = (Node) o;
    return Objects.equals(pos, node.pos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pos);
  }

  @Override
  public String toString() {
    return "Node{" +
        "pos=" + pos +
        ", neighbors=" + neighbors.entrySet().stream().map(n -> n.getKey().pos + ":" + n.getValue()).toList() +
        "}\r\n";
  }
}
