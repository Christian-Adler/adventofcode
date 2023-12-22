import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Task {
  private final Set<Pos> gardenPlots = new HashSet<>();
  private final Set<Pos> rocks = new HashSet<>();
  private Pos start = null;

  private int maxY = -1;

  public void init() {
  }

  public void addLine(String input) {
    maxY++;
    ArrayList<String> inputAsList = Util.str2List(input);
    for (int x = 0; x < inputAsList.size(); x++) {
      String s = inputAsList.get(x);
      switch (s) {
        case "S" -> {
          start = new Pos(x, maxY);
          gardenPlots.add(new Pos(x, maxY));
        }
        case "." -> gardenPlots.add(new Pos(x, maxY));
        case "#" -> rocks.add(new Pos(x, maxY));
      }
    }
  }

  public void afterParse() {
//    out(toStringConsole());

    int maxSteps = 64;

    int actStep = 0;

    Set<Pos> reachedAfterStep = new HashSet<>();
    reachedAfterStep.add(start);

    while (actStep < maxSteps) {
      actStep++;
      Set<Pos> workSet = new HashSet<>(reachedAfterStep);
      reachedAfterStep.clear();

      for (Pos pos : workSet) {
        for (Pos adj : Pos.adjacent) {
          Pos adjacent = pos.addToNew(adj);
          adjacent.color = "O";
          if (gardenPlots.contains(adjacent))
            reachedAfterStep.add(adjacent);
        }
      }

//      out("\r\nStep ", actStep);
//      SVG svg = prepareSvg();
//      reachedAfterStep.forEach(svg::add);
//      out(svg.toConsoleString());
    }

    out("\r\nStep ", actStep);
    SVG svg = prepareSvg();
    reachedAfterStep.forEach(svg::add);
    out(svg.toConsoleString());

    out("Part 1", "reachable garden plots with", maxSteps, "steps: ", reachedAfterStep.size());

  }


  public void out(Object... str) {
    Util.out(str);
  }

  public String toStringSVG() {
    SVG svg = new SVG();
    return svg.toSVGStringAged();
  }


  public String toStringConsole() {
    SVG svg = prepareSvg();
    return svg.toConsoleString();
  }

  private SVG prepareSvg() {
    SVG svg = new SVG();
    gardenPlots.forEach(gp -> {
      gp.color = ".";
      svg.add(gp);
    });
    rocks.forEach(svg::add);
    if (start != null) {
      start.color = "S";
      svg.add(start);
    }
    return svg;
  }
}
