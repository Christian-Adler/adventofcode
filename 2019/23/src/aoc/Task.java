package aoc;

import aoc.computer.Computer;
import aoc.computer.IInput;
import aoc.computer.IOutput;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    runForInput("./input.txt");
  }

  private static final AtomicBoolean foundPart1 = new AtomicBoolean();

  public void exec(List<String> lines) throws InterruptedException {
    Map<Long, Long> program = Computer.parseProgram(lines.getFirst());

    Map<Long, Computer> computers = new HashMap<>();
    NAT nat = new NAT(computers);

    for (long i = 0; i < 50; i++) {
      computers.put(i, new Computer(program, new Output(i, computers, nat), new Input(i)));
    }

    for (Computer computer : computers.values()) {
      computer.exec();
    }

    nat.start();
    nat.join();

    for (Computer computer : computers.values()) {
      if (computer.isAlive())
        computer.join();
    }
  }

  private static class NAT extends Thread {
    private final Map<Long, Computer> computers;

    private long x;
    private long y;
    private long prevY = -1;

    private NAT(Map<Long, Computer> computers) {
      this.computers = computers;
    }

    public synchronized void onPacket(long x, long y) {
      // out("on packet", x, y);
      if (!foundPart1.get()) {
        foundPart1.set(true);
        out("part 1", "first y of target NAT", y);
      }
      this.x = x;
      this.y = y;
    }

    private boolean sendLastPacket() {
      // out("send last packet", x, y);
      if (prevY == y) {
        out("part 2", "twice in a row y", y);
        return true;
      }
      prevY = y;
      computers.get(0L).getInput().add(x, y);
      return false;
    }

    public void run() {
      long idleDelay = 50;
      while (!this.isInterrupted()) {
        boolean isIdle = true;
        for (Computer computer : computers.values()) {
          if (!((Input) computer.getInput()).isIdle(idleDelay)
              || !((Output) computer.getOutput()).isIdle(idleDelay)) {
            isIdle = false;
            break;
          }
        }

        if (isIdle) {
          // out("got idle");
          if (sendLastPacket()) {

            break;
          }
        }

        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          out("Exit on interrupt...");
        }
      }
      // out("Nat exit...");
      computers.values().forEach(Thread::interrupt);
    }

  }

  private static class Output implements IOutput {
    private final Map<Long, Computer> computers;
    private final NAT nat;
    private final long networkAddress;

    private final List<Long> outputs = new ArrayList<>();
    private long lastOutput = 0;

    private Output(long networkAddress, Map<Long, Computer> computers, NAT nat) {
      this.computers = computers;
      this.nat = nat;
      this.networkAddress = networkAddress;
    }

    @Override
    public void out(Long value) {
      // TaskBase.out("Out from", networkAddress,  "value:", value);
      lastOutput = System.currentTimeMillis();
      outputs.add(value);
      if (outputs.size() == 3) {
        Long address = outputs.getFirst();
        if (address == 255) {
          nat.onPacket(outputs.get(1), outputs.get(2));
          outputs.clear();
        } else {
          Computer targetComputer = this.computers.get(address);
          targetComputer.getInput().add(outputs.get(1), outputs.get(2));
          outputs.clear();
        }
      }
    }

    public synchronized boolean isIdle(long idleDelay) {
      return lastOutput < System.currentTimeMillis() - idleDelay;
    }
  }

  private static class Input implements IInput {
    private final ArrayList<Long> inputList = new ArrayList<>();
    private long lastInput = 0;

    public Input(long networkAddress) {
      add(networkAddress);
    }

    public synchronized boolean isIdle(long idleDelay) {
      return inputList.isEmpty() && lastInput < System.currentTimeMillis() - idleDelay;
    }

    @Override
    public synchronized void add(Long... values) {
      inputList.addAll(Arrays.asList(values));
    }

    @Override
    public synchronized long get() {
      if (inputList.isEmpty()) {
        return -1;
      }
      lastInput = System.currentTimeMillis();
      return inputList.removeFirst();
    }
  }
}
