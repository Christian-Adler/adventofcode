package aoc;

import aoc.util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Task {

  private final Map<String, Computer> computers = new HashMap<>();

  public void init() {
  }

  public void addLine(String input) {
    String[] split = input.split("-");

    Computer c0 = getOrCreateComputer(split[0]);
    Computer c1 = getOrCreateComputer(split[1]);
    c0.addConnected(c1);
    c1.addConnected(c0);
  }

  private Computer getOrCreateComputer(String name) {
    Computer computer = computers.get(name);
    if (computer == null) {
      computer = new Computer(name);
      computers.put(name, computer);
    }
    return computer;
  }

  public void afterParse() throws Exception {
    Set<Set<Computer>> setsOf3 = new HashSet<>();

    for (Computer computer : computers.values()) {
      setsOf3.addAll(
          findSetOfThree(computer)
      );
    }

    // out(setsOf3);
    int count = 0;
    for (Set<Computer> computerSet : setsOf3) {
      if (computerSet.stream().anyMatch(c -> c.name.startsWith("t")))
        count++;
    }
    out("part 1", "sets of three with at least on starting with t", count);


    Set<Computer> largestLanParty = null;

    for (Computer computer : computers.values()) {

      if (largestLanParty != null && largestLanParty.size() >= computer.connected.size())
        continue;

      Set<Computer> lanParty = findLargestLanParty(computer);
      if (lanParty != null) {
        if (largestLanParty == null || lanParty.size() > largestLanParty.size())
          largestLanParty = lanParty;
      }
    }

    if (largestLanParty == null) throw new IllegalStateException("Found no solution");
    String pw = largestLanParty.stream().map(c -> c.name).sorted().collect(Collectors.joining(","));
    out("part 2", pw);
  }

  private List<Set<Computer>> findSetOfThree(Computer computer) {
    List<Set<Computer>> result = new ArrayList<>();

    List<List<Computer>> pairs = Util.findPairs(computer.connected);
    for (List<Computer> pair : pairs) {
      Computer c1 = pair.getFirst();
      Computer c2 = pair.getLast();

      if (c1.isConnectedTo(computer, c2) && c2.isConnectedTo(computer, c1)) {
        result.add(new HashSet<>(List.of(computer, c1, c2)));
      }
    }

    return result;
  }

  private Set<Computer> findLargestLanParty(Computer computer) {
    return findLargestLanParty(computer, new HashSet<>());
  }

  private Set<Computer> findLargestLanParty(Computer computer, Set<Computer> soFarConnected) {
    if (computer.connected.containsAll(soFarConnected)) {
      soFarConnected.add(computer);

      Set<Computer> largestLanParty = null;

      for (Computer connected : computer.connected) {
        if (soFarConnected.contains(connected))
          continue;

        if (largestLanParty != null && largestLanParty.contains(connected))
          continue;

        Set<Computer> lanParty = findLargestLanParty(connected, new HashSet<>(soFarConnected));
        if (lanParty != null) {
          if (largestLanParty == null) {
            largestLanParty = lanParty;
          }
        }
      }

      if (largestLanParty != null)
        return largestLanParty;

      return soFarConnected;
    }
    return null;
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
