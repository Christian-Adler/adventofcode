import java.util.ArrayList;
import java.util.LinkedList;

public class BroadcastModule extends Module {
  public BroadcastModule(String name, ArrayList<String> destinationModules) {
    super(name, destinationModules);
  }

  @Override
  public void reset() {
  }

  @Override
  public void onPulse(Pulse pulse, LinkedList<Pulse> workList) {
    for (String destinationModule : destinationModules) {
      workList.add(new Pulse(name, pulse.high(), destinationModule));
    }
  }

  public String toString() {
    return "BroadcastModule " + name + " => " + destinationModules;
  }
}
