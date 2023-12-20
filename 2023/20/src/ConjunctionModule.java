import java.util.*;

public class ConjunctionModule extends Module {
  private final Map<String, Boolean> inputs = new HashMap<>();

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
    for (String s : inputs.keySet()) {
      inputs.put(s, false);
    }
  }

  @Override
  public void onPulse(Pulse pulse, LinkedList<Pulse> workList) {
    inputs.put(pulse.sourceModule(), pulse.high());

    boolean sendLow = true;
    for (Boolean lastInputVal : inputs.values()) {
      sendLow = sendLow && lastInputVal;
    }

    for (String destinationModule : destinationModules) {
      workList.add(new Pulse(name, !sendLow, destinationModule));
    }
  }

  @Override
  public String toString() {
    return "ConjunctionModule &" + name + " => " + destinationModules;
  }
}
