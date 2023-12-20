import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Task {
  private final Map<String, Module> modules = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    Module module = Module.from(input);
    modules.put(module.name, module);
  }

  public void afterParse() {
    for (Module module : modules.values()) {
      module.init(modules.values());
    }
    out(modules);

//    out("\r\nclick button:");
//    clickButton();
    long countHighPulses = 0;
    long countLowPulses = 0;
    for (int i = 0; i < 1000; i++) {
      List<Long> countsList = clickButton(new AtomicBoolean(false));
      countHighPulses += countsList.getFirst();
      countLowPulses += countsList.getLast();
    }

    out("low:", countLowPulses);
    out("high:", countHighPulses);
    out("Part 1", countHighPulses * countLowPulses);

    // Part 2
    for (Module module : modules.values()) {
      module.reset();
    }

    out("modules:", modules.size());
    if (true) return;

// TODO Pulse targetModule as Ref instead of Name -> in init
    AtomicBoolean firstLowToRx = new AtomicBoolean(false);
    long countBtnPress = 0;
    while (!firstLowToRx.get()) {
      countBtnPress++;
      if (countBtnPress % 1000000 == 0) out("presses:", countBtnPress);
      clickButton(firstLowToRx);
    }
    out("Part 2", countBtnPress);
  }

  private List<Long> clickButton(AtomicBoolean firstLowToRx) {
    long countHighPulses = 0;
    long countLowPulses = 0;
    LinkedList<Pulse> workList = new LinkedList<>();
    workList.add(new Pulse("button", false, "broadcaster"));
    while (!workList.isEmpty()) {
      Pulse pulse = workList.removeFirst();
      if (pulse.high()) countHighPulses++;
      else countLowPulses++;
//      out(pulse);

      if (!pulse.high() && pulse.targetModule().equals("rx"))
        firstLowToRx.set(true);

      Module module = modules.get(pulse.targetModule());
      if (module == null) {
//        out("no module for " + pulse.targetModule());
        continue;
      }
      module.onPulse(pulse, workList);
    }

    return Arrays.asList(countHighPulses, countLowPulses);
  }

  public void out(Object... str) {
    Util.out(str);
  }

}
