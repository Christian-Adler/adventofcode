import java.util.Objects;

public record Edge(Component c1, Component c2) {
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge edge = (Edge) o;
    return Objects.equals(c1, edge.c1) && Objects.equals(c2, edge.c2) || Objects.equals(c2, edge.c1) && Objects.equals(c1, edge.c2);
  }

  @Override
  public int hashCode() {
    return Objects.hash("edge");
  }
}
