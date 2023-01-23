import java.util.Objects;

public class Node {
    final String name;
    final long size;
    long used;

    public Node(String name, long size, long used) {
        this.name = name;
        this.size = size;
        this.used = used;
    }

    public Node copy() {
        return new Node(name, size, used);
    }

    long available() {
        return size - used;
    }

    boolean isEmpty() {
        return used == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return size == node.size && Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("name='").append(name).append('\'');
        sb.append(", size=").append(size);
        sb.append(", used=").append(used);
        sb.append('}');
        return sb.toString();
    }
}
