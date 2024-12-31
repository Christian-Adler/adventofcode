package aoc;

import aoc.util.Vec3d;

import java.util.*;

public class Scanner {
  public final int id;

  public final Set<Vec3d> beacons = new LinkedHashSet<>();
  public final Map<Vec3d, List<Integer>> beaconDistances = new HashMap<>();

  public Scanner(int id) {
    this.id = id;
  }

  public void addBeacon(Vec3d beacon) {
    beacons.add(beacon);
  }

  public void calcDistances() {
    for (Vec3d beacon : beacons) {
      List<Integer> distances = new ArrayList<>();
      for (Vec3d other : beacons) {
        if (beacon.equals(other)) continue;
        distances.add(beacon.manhattanDistance(other));
      }
      Collections.sort(distances);
      beaconDistances.put(beacon, distances);
    }
  }

  @Override
  public String toString() {
    return "Scanner{" +
        "id=" + id +
        ", beacons=" + beacons.size() +
        "}";
  }
}
