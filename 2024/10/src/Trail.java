import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trail {
  public static final int MAX_HEIGHT = 9;
  private final List<Pos> trail = new ArrayList<>();

  public Trail(List<Pos> positions) {
    trail.addAll(positions);
  }

  public Pos getTail() {
    return trail.getLast();
  }

  public List<Pos> getTrail() {
    return trail;
  }

  public static Map<Pos, List<Trail>> findTrails(List<Pos> trailHeads, Map<Pos, Integer> heightMap) {
    Map<Pos, List<Trail>> res = new HashMap<>();
    for (Pos trailHead : trailHeads) {
      res.put(trailHead, findTrails(trailHead, heightMap));
    }
    return res;
  }

  private static List<Trail> findTrails(Pos trailHead, Map<Pos, Integer> heightMap) {
    List<Pos> soFarTrail = new ArrayList<>();
    soFarTrail.add(trailHead);
    List<List<Pos>> trailsLists = findAllTrails(soFarTrail, heightMap);
    return trailsLists.stream().map(Trail::new).toList();
  }

  private static List<List<Pos>> findAllTrails(List<Pos> soFarTrail, Map<Pos, Integer> heightMap) {
    List<List<Pos>> result = new ArrayList<>();

    Pos lastPos = soFarTrail.getLast();
    Integer lastPosHeight = heightMap.get(lastPos);

    if (lastPosHeight == null) // not in map?
      return result;

    if (lastPosHeight == MAX_HEIGHT)
      result.add(soFarTrail);
    else {
      for (Pos dir : Pos.adjacent) {
        Pos nextPos = lastPos.addToNew(dir);
        Integer nextPosHeight = heightMap.get(nextPos);
        if (nextPosHeight != null && nextPosHeight == lastPosHeight + 1) {
          List<Pos> nextSoFarTrail = new ArrayList<>(soFarTrail);
          nextSoFarTrail.add(nextPos);
          result.addAll(findAllTrails(nextSoFarTrail, heightMap));
        }
      }
    }

    return result;
  }


}
