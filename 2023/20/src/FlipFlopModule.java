import java.util.ArrayList;
import java.util.LinkedList;

public class FlipFlopModule extends Module {
  private boolean on = false;

  public FlipFlopModule(String name, ArrayList<String> destinationModules) {
    super(name, destinationModules);
  }

  @Override
  public void reset() {
    on = false;
  }

  @Override
  public void onPulse(Pulse pulse, LinkedList<Pulse> workList) {
    if (pulse.high())
      return;
    on = !on;
    for (String destinationModule : destinationModules) {
      workList.add(new Pulse(name, on, destinationModule));
    }
  }


  @Override
  public String toString() {
    return "FlipFlopModule %" + name + " => " + destinationModules;
  }
}
