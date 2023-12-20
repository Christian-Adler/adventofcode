import java.util.*;
import java.util.function.Consumer;

public class ConjunctionModule extends Module {
  private final Map<String, Boolean> inputs = new HashMap<>();
  private Consumer<Boolean> onPulse = null;

  public ConjunctionModule(String name, ArrayList<String> destinationModules) {
    super(name, destinationModules);
  }

  public void init(Collection<Module> allModules) {
    for (Module module : allModules) {
      if (module.destinationModules.contains(name))
        inputs.put(module.name, false);
    }
  }

  @Override
  public void reset() {
    inputs.replaceAll((s, v) -> false);
  }

  public boolean isAllInputHigh() {
    Optional<Boolean> first = inputs.values().stream().filter(b -> !b).findFirst();
    return first.isEmpty();
  }

  @Override
  public void onPulse(Pulse pulse, LinkedList<Pulse> workList) {
    inputs.put(pulse.sourceModule(), pulse.high());

    boolean allHigh = isAllInputHigh();

    if (onPulse != null && !allHigh)
      onPulse.accept(true);

    for (String destinationModule : destinationModules) {
      workList.add(new Pulse(name, !allHigh, destinationModule));
    }
  }

  @Override
  public String toString() {
    return "ConjunctionModule &" + name + " => " + destinationModules;
  }

  public void registerOnPulseHigh(Consumer<Boolean> listener) {
    onPulse = listener;
  }
}
