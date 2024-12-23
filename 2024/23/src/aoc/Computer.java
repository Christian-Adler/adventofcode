package aoc;

import java.util.HashSet;
import java.util.Set;

public class Computer {
  public final Set<Computer> connected = new HashSet<>();

  public final String name;

  public Computer(String name) {
    this.name = name;
  }

  public void addConnected(Computer computer) {
    connected.add(computer);
  }

  public boolean isConnectedTo(Computer... computers) {
    for (Computer computer : computers) {
      if (!connected.contains(computer))
        return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return name;
  }
}
