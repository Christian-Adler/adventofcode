package aoc;

import aoc.util.Util;
import aoc.util.Vec3d;

import java.util.*;
import java.util.function.Function;

public class Task {
  private final List<Scanner> scanners = new ArrayList<>();
  private Scanner actScanner = null;
  private int scannerId = -1;

  public void init() {
  }

  public void addLine(String input) {
    if (input.isEmpty()) return;
    if (input.contains("scanner")) {
      actScanner = new Scanner(++scannerId);
      scanners.add(actScanner);
    } else {
      actScanner.addBeacon(new Vec3d(input));
    }
  }

  public void afterParse() throws Exception {
    // out(scanners);
    for (Scanner scanner : scanners) {
      scanner.calcDistances();
    }

    // sort Scanner beacons by distance from one beacon
    // flip / rotate beacons

    List<Scanner> workList = new ArrayList<>(scanners);
    Scanner scanner0 = workList.removeFirst();

    List<Vec3d> scannerPositionsRelativeTo0 = new ArrayList<>();
    scannerPositionsRelativeTo0.add(new Vec3d(0, 0, 0));

    while (!workList.isEmpty()) {
      Scanner scanner = workList.removeFirst();
      Map<Vec3d, Vec3d> equalPointsMap = new HashMap<>();
      for (Map.Entry<Vec3d, List<Integer>> entry0 : scanner0.beaconDistances.entrySet()) {
        Vec3d vec0 = entry0.getKey();
        Vec3d vecEqual = getEqualVec(scanner, entry0);

        if (vecEqual != null)
          equalPointsMap.put(vec0, vecEqual);
      }

      if (equalPointsMap.size() >= 12) {
        // out(equalPointsMap);
        // translate scanner beacons into scanner 0
        Function<Vec3d, Vec3d> translate = null;

        // 2 Einträge aus der Map holen.
        // Jeweils den Richtungsvektor (differenz) erstellen.
        // Da die Punkte dieselben sind, muss der Richtungsvektor muss auch identisch sein - nur die Rotation stimmt nicht
        // Rotation ermitteln. Dadurch kann das Koordinatensystem in scanner schon mal gedreht werden und ist nur noch versetzt.
        // Versatz ermitteln, aus gedrehtem Punkt1 von Scanner von Punkt1 von Scanner0
        // alle Punkte aus Scanner in Koordinaten von Scanner0 übertragen


        Iterator<Map.Entry<Vec3d, Vec3d>> iterator = equalPointsMap.entrySet().iterator();
        Map.Entry<Vec3d, Vec3d> entry1 = iterator.next();
        Vec3d p1S0 = entry1.getKey();
        Vec3d p1S = entry1.getValue();
        Map.Entry<Vec3d, Vec3d> entry2 = iterator.next();
        Vec3d p2S0 = entry2.getKey();
        Vec3d p2S = entry2.getValue();
        Map.Entry<Vec3d, Vec3d> entry3 = iterator.next();
        Vec3d p3S0 = entry3.getKey();
        Vec3d p3S = entry3.getValue();

        // Richtungsvektoren
        Vec3d dir1S0 = p2S0.subToNew(p1S0);
        Vec3d dir1S = p2S.subToNew(p1S);
        Vec3d dir2S0 = p3S0.subToNew(p1S0);
        Vec3d dir2S = p3S.subToNew(p1S);
        // out(dir1S0, dir1S, dir2S0, dir2S);

        Vec3d xRot;
        Vec3d yRot;
        Vec3d zRot;
        if (isSameAbs(dir1S0.x, dir1S.x)) {
          xRot = new Vec3d(getFactor(dir1S0.x, dir1S.x), 0, 0);
        } else if (isSameAbs(dir1S0.x, dir1S.y)) {
          xRot = new Vec3d(0, getFactor(dir1S0.x, dir1S.y), 0);
        } else if (isSameAbs(dir1S0.x, dir1S.z)) {
          xRot = new Vec3d(0, 0, getFactor(dir1S0.x, dir1S.z));
        } else throw new IllegalStateException("found no xRot");
        if (isSameAbs(dir1S0.y, dir1S.x)) {
          yRot = new Vec3d(getFactor(dir1S0.y, dir1S.x), 0, 0);
        } else if (isSameAbs(dir1S0.y, dir1S.y)) {
          yRot = new Vec3d(0, getFactor(dir1S0.y, dir1S.y), 0);
        } else if (isSameAbs(dir1S0.y, dir1S.z)) {
          yRot = new Vec3d(0, 0, getFactor(dir1S0.y, dir1S.z));
        } else throw new IllegalStateException("found no yRot");
        if (isSameAbs(dir1S0.z, dir1S.x)) {
          zRot = new Vec3d(getFactor(dir1S0.z, dir1S.x), 0, 0);
        } else if (isSameAbs(dir1S0.z, dir1S.y)) {
          zRot = new Vec3d(0, getFactor(dir1S0.z, dir1S.y), 0);
        } else if (isSameAbs(dir1S0.z, dir1S.z)) {
          zRot = new Vec3d(0, 0, getFactor(dir1S0.z, dir1S.z));
        } else throw new IllegalStateException("found no zRot");

        // out("rotations", xRot, yRot, zRot);

        // p1S rotieren
        Vec3d p1SRotated = new Vec3d(multAndGetLen(p1S, xRot), multAndGetLen(p1S, yRot), multAndGetLen(p1S, zRot));
        // out("p1SRotated", p1SRotated);

        Vec3d translation = p1S0.subToNew(p1SRotated);
        scannerPositionsRelativeTo0.add(translation);
        // out("translation", translation);

        // out("size before", scanner0.beacons.size());

        for (Vec3d beacon : scanner.beacons) {
          // out(beacon);
          Vec3d rotated = new Vec3d(multAndGetLen(beacon, xRot), multAndGetLen(beacon, yRot), multAndGetLen(beacon, zRot));
          Vec3d translated = rotated.addToNew(translation);
          // out("-> ", translated);
          scanner0.beacons.add(translated);
        }

        // out("size after", scanner0.beacons.size());
        scanner0.calcDistances(); // calc distances again (for newly added beacons)
      } else
        workList.add(scanner); // if no matches found add at and of worklist again
    }

    out("part 1", "total beacons", scanner0.beacons.size());

    // out(scannerPositionsRelativeTo0);

    int maxDistance = 0;
    for (int i = 0; i < scannerPositionsRelativeTo0.size(); i++) {
      Vec3d item1 = scannerPositionsRelativeTo0.get(i);
      for (int j = i + 1; j < scannerPositionsRelativeTo0.size(); j++) {
        Vec3d item2 = scannerPositionsRelativeTo0.get(j);
        int dist = item1.manhattanDistance(item2);
        if (dist > maxDistance)
          maxDistance = dist;
      }
    }

    out("part 2", "max scanner distance", maxDistance);
  }

  private static int multAndGetLen(Vec3d vec, Vec3d mult) {
    int len = 0;
    len += vec.x * mult.x;
    len += vec.y * mult.y;
    len += vec.z * mult.z;
    return len;
  }

  private static boolean isSameAbs(int val1, int val2) {
    return Math.abs(val1) == Math.abs(val2);
  }

  private static int getFactor(int val1, int val2) {
    return val1 / val2;
  }

  private static Vec3d getEqualVec(Scanner scanner, Map.Entry<Vec3d, List<Integer>> entry0) {
    List<Integer> distances0 = entry0.getValue();

    int maxEquality = -1;
    boolean foundDuplicate = false;
    Vec3d vecEqual = null;
    for (Map.Entry<Vec3d, List<Integer>> entryOther : scanner.beaconDistances.entrySet()) {
      Vec3d vecOther = entryOther.getKey();
      List<Integer> distancesOther = entryOther.getValue();

      ArrayList<Integer> testList = new ArrayList<>(distances0);
      testList.retainAll(distancesOther);

      if (testList.size() >= 11) {
        if (testList.size() > maxEquality) {
          maxEquality = testList.size();
          vecEqual = vecOther;
          foundDuplicate = false;
        } else if (testList.size() == maxEquality) {
          foundDuplicate = true;
          out("Found Duplicate");
        }

      }
    }
    if (foundDuplicate) return null;
    return vecEqual;
  }

  public static void out(Object... str) {
    Util.out(str);
  }

}
