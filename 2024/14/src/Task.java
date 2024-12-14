import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {

  private List<Robot> robots = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    robots.add(new Robot(input));
  }

  public void afterParse(int width, int height) throws IOException {
    // out(robots);
    // out(toStringConsole(width, height));
    for (int i = 1; i <= 100; i++) {
      robots.forEach(r -> r.move(width, height));

      // out();
      // out("After ", i);
      // out(toStringConsole(width, height));
      // out();
    }
    out();
    // out("After 100");
    // out(toStringConsole(width, height));

    // count safety
    int quadrantWidth = Math.floorDiv(width, 2);
    int quadrantHeight = Math.floorDiv(height, 2);
    // out("Quadrant width,height:", quadrantWidth, quadrantHeight);
    //
    int quadrantCountTopLeft = 0;
    int quadrantCountBottomLeft = 0;
    int quadrantCountTopRight = 0;
    int quadrantCountBottomRight = 0;

    for (Robot robot : robots) {
      Pos p = robot.getP();
      if (p.x < quadrantWidth) {
        if (p.y < quadrantHeight) {
          quadrantCountTopLeft++;
        } else if (p.y > quadrantHeight) {
          quadrantCountBottomLeft++;
        }
      } else if (p.x > quadrantWidth) {
        if (p.y < quadrantHeight) {
          quadrantCountTopRight++;
        } else if (p.y > quadrantHeight) {
          quadrantCountBottomRight++;
        }
      }
    }

    // out("quadrant count", quadrantCountTopLeft, quadrantCountBottomLeft, quadrantCountTopRight, quadrantCountBottomRight);
    out("part 1", "multiplied", quadrantCountTopLeft * quadrantCountBottomLeft * quadrantCountTopRight * quadrantCountBottomRight);

    // part 2
    for (int i = 101; i <= 10000; i++) {
      robots.forEach(r -> r.move(width, height));

      if (scanForTree(width, height)) {
        out("part 2", "found tree after seconds: ", i);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (Robot robot : robots) {
          img.setRGB(robot.getP().x, robot.getP().y, Color.RED.getRGB());
        }
        ImageIO.write(img, "bmp", new File("./img_" + i + ".bmp"));
        return;
      }

      // part 2 generate images and look manually ;)
      if (false) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (Robot robot : robots) {
          img.setRGB(robot.getP().x, robot.getP().y, Color.RED.getRGB());
        }
        ImageIO.write(img, "bmp", new File("./img/img_" + i + ".bmp"));
      }
    }
  }

  /**
   * Search in map if >=2 horizontal and vertical "lines" exist
   *
   * @param width
   * @param height
   * @return
   */
  private boolean scanForTree(int width, int height) {
    Map<Integer, Integer> countPerY = new HashMap<>();
    Map<Integer, Integer> countPerX = new HashMap<>();
    for (Robot robot : robots) {
      Pos p = robot.getP();
      Integer soFar = countPerY.getOrDefault(p.y, 0);
      countPerY.put(p.y, soFar + 1);
      soFar = countPerX.getOrDefault(p.x, 0);
      countPerX.put(p.x, soFar + 1);
    }
    int foundRows = 0;
    int foundColumns = 0;
    for (Integer value : countPerY.values()) {
      if (value > height / 4)
        foundRows++;
    }
    for (Integer value : countPerX.values()) {
      if (value > width / 4)
        foundColumns++;
    }
    return foundRows >= 2 && foundColumns >= 2;
  }

  public void out(Object... str) {
    Util.out(str);
  }


  public String toStringConsole(int width, int height) {
    Map<Pos, Integer> posMap = new HashMap<>();
    for (Robot robot : robots) {
      Integer count = posMap.getOrDefault(robot.getP(), 0);
      count++;
      posMap.put(robot.getP(), count);
    }

    SVG svg = new SVG();
    svg.add(new Pos(0, 0), ".");
    svg.add(new Pos(width - 1, height - 1), ".");
    for (Map.Entry<Pos, Integer> entry : posMap.entrySet()) {
      svg.add(entry.getKey(), String.valueOf(entry.getValue()));
    }
    return svg.toConsoleString();
  }

  public String toStringSvg(int width, int height) {
    SVG svg = new SVG();
    svg.add(new Pos(0, 0), ".");
    svg.add(new Pos(width - 1, height - 1), ".");
    robots.forEach(r -> svg.add(r.getP(), "red"));
    return svg.toSVGString();
  }
}
