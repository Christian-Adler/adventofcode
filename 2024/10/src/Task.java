import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Task {
  private final Map<Pos, Integer> heightMap = new HashMap<>();
  private int maxY = -1;
  private int maxX = -1;
  private final List<Pos> trailheads = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    List<String> heights = Util.str2List(input);
    maxY++;
    maxX = heights.size() - 1;
    for (int x = 0; x < heights.size(); x++) {
      String val = heights.get(x);
      if (val.equals("."))
        heightMap.put(new Pos(x, maxY), -1);
      else {
        int height = Integer.parseInt(val);
        heightMap.put(new Pos(x, maxY), height);
        if (height == 0)
          trailheads.add(new Pos(x, maxY));
      }
    }
  }

  public void afterParse() throws IOException {
    // out(toStringConsole());

    int score = 0;
    int score2 = 0;

    Map<Pos, List<Trail>> trailsMap = Trail.findTrails(trailheads, heightMap);
    for (List<Trail> trails : trailsMap.values()) {
      Set<Pos> trailEnd = new HashSet<>();
      for (Trail trail : trails) {
        trailEnd.add(trail.getTail());
      }
      score += trailEnd.size();
      score2 += trails.size();
    }
    // for (Map.Entry<Pos, List<Trail>> entry : trailsMap.entrySet()) {
    //   out(entry.getKey(), "score", entry.getValue().size());
    // }
    out("part 1", "score", score);
    out("part 2", "score", score2);


    Files.writeString(Path.of("./svg.svg"), toStringSVG());
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    return builder.toString();
  }

  public String toStringSVG() {
    Map<Integer, String> colorMap = new HashMap<>();
    colorMap.put(0, "#66ff00");
    colorMap.put(1, "#5ce600");
    colorMap.put(2, "#55d500");
    colorMap.put(3, "#4bbb00");
    colorMap.put(4, "#409f00");
    colorMap.put(5, "#378a00");
    colorMap.put(6, "#2e7200");
    colorMap.put(7, "#4b5d00");
    colorMap.put(8, "#825a00");
    colorMap.put(9, "#b77f00");
    SVG svg = new SVG();

    for (Map.Entry<Pos, Integer> entry : heightMap.entrySet()) {
      Integer height = entry.getValue();
      String c = height >= 0 ? colorMap.get(height) : null;
      svg.add(entry.getKey(), c);
    }


    return svg.toSVGString();
    // return svg.toSVGStringAged();
  }


  public String toStringConsole() {
    SVG svg = new SVG();

    for (Map.Entry<Pos, Integer> entry : heightMap.entrySet()) {
      Integer height = entry.getValue();
      String c = height >= 0 ? String.valueOf(height) : ".";
      svg.add(entry.getKey(), c);
    }

    for (Pos trailhead : trailheads) {
      svg.add(trailhead, "T");
    }

    return svg.toConsoleString();
  }
}
