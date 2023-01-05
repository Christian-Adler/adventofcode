import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node {
    final String name;

    final Map<Node, Integer> neighbors = new HashMap<>();

    public Node(String name) {
        this.name = name;
    }

    void addNeighbor(Node node, int weight) {
        neighbors.put(node, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("name='").append(name).append('\'');
        sb.append(", neighbors=").append(neighbors.size());
        sb.append('}');
        return sb.toString();
    }
}
