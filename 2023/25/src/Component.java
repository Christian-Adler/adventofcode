import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Component {
  public final String name;
  public final Set<Component> edges = new HashSet<>();

  public Component(String name) {
    this.name = name;
  }

  public String name() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Component) obj;
    return Objects.equals(this.name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Component[" +
        "name=" + name + ']';
  }

  public static void connectComponents(Component c1, Component c2) {
    c1.edges.add(c2);
    c2.edges.add(c1);
  }

  public static void disconnectComponents(Component c1, Component c2) {
    c1.edges.remove(c2);
    c2.edges.remove(c1);
  }
}
