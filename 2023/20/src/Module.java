import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public abstract class Module {
  public final String name;
  public final ArrayList<String> destinationModules;

  protected Module(String name, ArrayList<String> destinationModules) {
    this.name = name;
    this.destinationModules = destinationModules;
  }

  /**
   * May be overridden to init depending on other modules
   */
  public void init(Collection<Module> allModules) {
  }

  public abstract void reset();

  public abstract void onPulse(Pulse pulse, LinkedList<Pulse> workList);

  @Override
  public String toString() {
    return "Module{" +
        "name='" + name + '\'' +
        ", destinationModules=" + destinationModules +
        '}';
  }

  public static Module from(String input) {
    String[] split = input.split(" -> ");
    ArrayList<String> destinations = new ArrayList<>(Arrays.stream(split[1].split(",\\s*")).toList());
    String moduleTypeAndName = split[0];
    if (moduleTypeAndName.equals("broadcaster")) return new BroadcastModule("broadcaster", destinations);
    if (moduleTypeAndName.startsWith("%")) return new FlipFlopModule(moduleTypeAndName.substring(1), destinations);
    if (moduleTypeAndName.startsWith("&")) return new ConjunctionModule(moduleTypeAndName.substring(1), destinations);
    throw new IllegalStateException("Unexpected module input");
  }

}
