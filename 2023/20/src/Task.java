import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

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
      List<Long> countsList = clickButton();
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

    // &lx -low-> rx if all inputs from lx are high
    // inputs are
    //    &cl -> lx
    //    &rp -> lx
    //    &lb -> lx
    //    &nj -> lx

    // determine cycles for each of them and then kgv/lcm
    final AtomicLong clFirstHigh = new AtomicLong(0);
    final AtomicLong clSecondHigh = new AtomicLong(0);
    final AtomicLong rpFirstHigh = new AtomicLong(0);
    final AtomicLong rpSecondHigh = new AtomicLong(0);
    final AtomicLong lbFirstHigh = new AtomicLong(0);
    final AtomicLong lbSecondHigh = new AtomicLong(0);
    final AtomicLong njFirstHigh = new AtomicLong(0);
    final AtomicLong njSecondHigh = new AtomicLong(0);

    ConjunctionModule clMod = (ConjunctionModule) modules.get("cl");
    ConjunctionModule rpMod = (ConjunctionModule) modules.get("rp");
    ConjunctionModule lbMod = (ConjunctionModule) modules.get("lb");
    ConjunctionModule njMod = (ConjunctionModule) modules.get("nj");

    final AtomicLong countBtnPress = new AtomicLong(0);

    clMod.registerOnPulseHigh(bVal -> {
      if (bVal && clSecondHigh.get() == 0) {
        if (clFirstHigh.get() == 0) clFirstHigh.set(countBtnPress.get());
        else clSecondHigh.set(countBtnPress.get());
      }
    });
    rpMod.registerOnPulseHigh(bVal -> {
      if (bVal && rpSecondHigh.get() == 0) {
        if (rpFirstHigh.get() == 0) rpFirstHigh.set(countBtnPress.get());
        else rpSecondHigh.set(countBtnPress.get());
      }
    });
    lbMod.registerOnPulseHigh(bVal -> {
      if (bVal && lbSecondHigh.get() == 0) {
        if (lbFirstHigh.get() == 0) lbFirstHigh.set(countBtnPress.get());
        else lbSecondHigh.set(countBtnPress.get());
      }
    });
    njMod.registerOnPulseHigh(bVal -> {
      if (bVal && njSecondHigh.get() == 0) {
        if (njFirstHigh.get() == 0) njFirstHigh.set(countBtnPress.get());
        else njSecondHigh.set(countBtnPress.get());
      }
    });

    do {
      countBtnPress.incrementAndGet();
      clickButton();

    } while (clSecondHigh.get() <= 0 || rpSecondHigh.get() <= 0 || lbSecondHigh.get() <= 0 || njSecondHigh.get() <= 0);

    List<Long> diffs = new ArrayList<>();
    diffs.add(clSecondHigh.get() - clFirstHigh.get());
    diffs.add(rpSecondHigh.get() - rpFirstHigh.get());
    diffs.add(lbSecondHigh.get() - lbFirstHigh.get());
    diffs.add(njSecondHigh.get() - njFirstHigh.get());

    long[] array = new long[diffs.size()];
    for (int i = 0; i < diffs.size(); i++) {
      array[i] = diffs.get(i);
    }

    long clicks = Util.kgV(array);

    out("Part 2", clicks);
  }

  private List<Long> clickButton() {
    long countHighPulses = 0;
    long countLowPulses = 0;
    LinkedList<Pulse> workList = new LinkedList<>();
    workList.add(new Pulse("button", false, "broadcaster"));
    while (!workList.isEmpty()) {
      Pulse pulse = workList.removeFirst();
      if (pulse.high()) countHighPulses++;
      else countLowPulses++;
//      out(pulse);

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
