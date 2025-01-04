package aoc;

import aoc.util.Vec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Amphipod {
  public static int spotHeight = 0;

  public final String type;
  public Vec position;
  public int usedEnergy = 0;

  public Amphipod(String type, Vec position) {
    this(type, position, 0);
    spotHeight = Math.max(spotHeight, position.y - 1);
  }

  private Amphipod(String type, Vec position, int usedEnergy) {
    this.type = type;
    this.position = position;
    this.usedEnergy = usedEnergy;
  }

  public Amphipod copy() {
    return new Amphipod(type, position.copy(), usedEnergy);
  }

  public boolean isInTarget(Map<Vec, Amphipod> pos2A) {
    Vec target = target();
    if (position.x != target.x)
      return false;

    for (int yAdd = spotHeight - 1; yAdd >= 0; yAdd--) {
      Vec checkPos = target.add(0, yAdd);
      if (position.equals(checkPos))
        return true;
      Amphipod other = pos2A.get(checkPos);
      if (other == null)
        return false;
      if (!other.type.equals(type))
        return false;
    }
    return false;
  }

  public boolean isInHallway() {
    return position.y == 1;
  }

  public List<Vec> wayToTarget(Map<Vec, Amphipod> pos2A) {
    List<Vec> res = new ArrayList<>();
    if (isInTarget(pos2A)) return res;

    Vec target = target();

    // is target free
    Vec checkPos = null;
    for (int yAdd = spotHeight - 1; yAdd >= 0; yAdd--) {
      checkPos = target.add(0, yAdd);

      Amphipod other = pos2A.get(checkPos);
      if (other == null)
        break;
      if (!other.type.equals(type))
        return null;
    }
    if (checkPos == null)
      return null;

    if (position.y > 1) { // in spot?
      res.addAll(position.pathBetween(position.withY(1)));
      res.add(position.withY(1));
      res.addAll(position.withY(1).pathBetween(checkPos.withY(1)));
    } else {
      res.addAll(position.pathBetween(checkPos.withY(1)));
    }
    res.add(checkPos.withY(1));
    res.addAll(checkPos.withY(1).pathBetween(checkPos));
    res.add(checkPos);

    for (Vec vec : res) {
      if (pos2A.containsKey(vec))
        return null;
    }

    return res;
  }

  public List<Vec> wayToHallway(Vec target, Map<Vec, Amphipod> pos2A) {
    if (isInHallway()) // already in hallway - not allowed to move
      return null;

    List<Vec> res = new ArrayList<>();

    // noinspection CollectionAddAllCanBeReplacedWithConstructor
    res.addAll(position.pathBetween(position.withY(1)));
    res.add(position.withY(1));
    res.addAll(position.withY(1).pathBetween(target));
    res.add(target);

    for (Vec vec : res) {
      if (pos2A.containsKey(vec))
        return null;
    }

    return res;
  }

  public int energyPerStep() {
    return switch (type) {
      case "A" -> 1;
      case "B" -> 10;
      case "C" -> 100;
      case "D" -> 1000;
      default -> throw new IllegalStateException("invalid type");
    };
  }

  public Vec target() {
    return switch (type) {
      case "A" -> new Vec(3, 2);
      case "B" -> new Vec(5, 2);
      case "C" -> new Vec(7, 2);
      case "D" -> new Vec(9, 2);
      default -> throw new IllegalStateException("invalid type");
    };
  }

  public int getUsedEnergy() {
    return usedEnergy;
  }

  public int costs(int steps) {
    return steps * energyPerStep();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Amphipod amphipod = (Amphipod) o;
    return Objects.equals(type, amphipod.type) && Objects.equals(position, amphipod.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, position);
  }
}
