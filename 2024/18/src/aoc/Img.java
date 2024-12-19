package aoc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;


public class Img {
  private int xMin = Integer.MAX_VALUE;
  private int yMin = Integer.MAX_VALUE;
  private int xMax = Integer.MIN_VALUE;
  private int yMax = Integer.MIN_VALUE;
  private final List<Vec> imgPositions = new LinkedList<>();
  private int numPosWithoutColor = 0;

  public void add(Vec vec) {
    add(vec, null);
  }

  @SuppressWarnings("SameParameterValue")
  public void add(Vec vec, Object color) {
    int x = vec.x;
    int y = vec.y;
    this.xMax = Math.max(this.xMax, x);
    this.xMin = Math.min(this.xMin, x);
    this.yMax = Math.max(this.yMax, y);
    this.yMin = Math.min(this.yMin, y);

    Vec p = new Vec(x, y, color != null ? color : vec.color);
    if (p.color == null)
      numPosWithoutColor++;

    imgPositions.add(p);
  }

  private Set<Vec> getPaintPositions() {
    return new LinkedHashSet<>(imgPositions.reversed());
  }

  public void writeBitmap() throws IOException {
    writeBitmap(null, false);
  }

  public void writeBitmapAged() throws IOException {
    writeBitmap(null, true);
  }

  public void writeBitmap(String path, boolean aged) throws IOException {
    Set<Vec> positions = getPaintPositions();

    int steps = Math.max(1, numPosWithoutColor);
    int width = Math.max(xMax - xMin, 2);
    int height = Math.max(yMax - yMin, 2);
    int count = 0;
    BufferedImage img = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);
    for (Vec vec : positions) {
      int yIdx = vec.y - this.yMin;
      int xIdx = vec.x - this.xMin;

      // bitmap needs int
      int rgb = Color.RED.getRGB();
      if (vec.color instanceof Color col)
        rgb = col.getRGB();
      else if (vec.color instanceof Integer i)
        rgb = i;
      else if (aged) {
        rgb = getAgedColor(count, steps).getRGB();
        count++;
      }

      img.setRGB(xIdx, yIdx, rgb);
    }
    ImageIO.write(img, "bmp", new File(path == null ? "./img.bmp" : path));
  }

  String toConsoleString() {
    Set<Vec> positions = getPaintPositions();
    List<List<String>> console = new ArrayList<>();
    for (int y = 0; y <= yMax - yMin; y++) {
      List<String> row = new ArrayList<>();
      console.add(row);
      for (int x = 0; x <= xMax - xMin; x++) {
        row.add(".");
      }
    }

    for (Vec vec : positions) {
      int yIdx = vec.y - this.yMin;
      int xIdx = vec.x - this.xMin;

      String c = "#";
      if (vec.color instanceof String cStr && !cStr.isEmpty())
        c = cStr.substring(0, 1);

      console.get(yIdx).set(xIdx, c);
    }

    StringBuilder builder = new StringBuilder();
    for (List<String> row : console) {
      if (!builder.isEmpty())
        builder.append("\r\n");
      builder.append(String.join("", row));
    }
    return builder.toString();
  }


  String toSVGStringAged() {
    Set<Vec> positions = getPaintPositions();
    int steps = Math.max(1, numPosWithoutColor);

    StringBuilder res = new StringBuilder("\r\n");

    res.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"").append(this.xMax - this.xMin + 3).append("\" height=\"").append(this.yMax - this.yMin + 3).append("\">\r\n");
    res.append("<rect style=\"fill:#000000;\" width=\"").append(this.xMax - this.xMin + 3).append("\" height=\"").append(this.yMax - this.yMin + 3).append("\" x=\"0\" y=\"0\" />\r\n");
    res.append("<g transform=\"translate(1,1)\">\r\n");

    int count = 0;
    for (Vec vec : positions) {
      String rgb; // = "#ff0000";
      if (vec.color instanceof String strCol) {
        rgb = strCol;
      } else if (vec.color instanceof Color color) {
        rgb = "#" + String.format("%02X", (0xFF & color.getRed())) + String.format("%02X", (0xFF & color.getGreen())) + String.format("%02X", (0xFF & color.getBlue()));
      } else {
        Color agedColor = getAgedColor(count, steps);
        rgb = color2Hex(agedColor);
        count++;
      }
      res.append("<rect style=\"fill:").append(rgb).append(";\" width=\"1\" height=\"1\" x=\"").append(vec.x - this.xMin).append("\" y=\"").append(vec.y - this.yMin).append("\" />\r\n");
    }
    res.append("</g>\r\n");
    res.append("</svg>\r\n");

    return res.toString();
  }

  String toSVGString() {
    Set<Vec> positions = getPaintPositions();
    StringBuilder res = new StringBuilder("\r\n");

    res.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"").append(this.xMax - this.xMin + 3).append("\" height=\"").append(this.yMax - this.yMin + 3).append("\">\r\n");
    res.append("<rect style=\"fill:#000000;\" width=\"").append(this.xMax - this.xMin + 3).append("\" height=\"").append(this.yMax - this.yMin + 3).append("\" x=\"0\" y=\"0\" />\r\n");
    res.append("<g transform=\"translate(1,1)\">\r\n");
    for (Vec vec : positions) {
      String rgb = "#ff0000";
      if (vec.color instanceof String strCol) {
        rgb = strCol;
      } else if (vec.color instanceof Color color) {
        rgb = "#" + String.format("%02X", (0xFF & color.getRed())) + String.format("%02X", (0xFF & color.getGreen())) + String.format("%02X", (0xFF & color.getBlue()));
      }
      res.append("<rect style=\"fill:").append(rgb).append(";\" width=\"1\" height=\"1\" x=\"").append(vec.x - this.xMin).append("\" y=\"").append(vec.y - this.yMin).append("\" />\r\n");
    }
    res.append("</g>\r\n");
    res.append("</svg>\r\n");

    return res.toString();
  }


  /**
   * @param h Hue is specified as degrees in the range 0 - 360.
   * @param s Saturation is specified as a percentage in the range 1 - 100.
   * @param l Luminance is specified as a percentage in the range 1 - 100.
   */
  public static String hsl2Hex(float h, float s, float l) {
    int[] rgb = hsla2rgbIntArray(h, s, l, 1);
    return intArr2Hex(rgb);
  }

  public static String color2Hex(Color color) {
    return "#" + Integer.toHexString(color.getRGB()).substring(2); // Color hat alpha vorne!?
  }

  /**
   * @param rgb 3int [0-255]
   * @return HexString #ffffff
   */
  private static String intArr2Hex(int[] rgb) {
    String res = "#";
    res += int2hex2(rgb[0]);
    res += int2hex2(rgb[1]);
    res += int2hex2(rgb[2]);
    return res;
  }

  private static String int2hex2(int val) {
    String hex = Integer.toHexString(val);
    if (hex.length() < 2)
      hex = "0" + hex;
    return hex;
  }

  private static Color getAgedColor(int step, int steps) {
    int startL = 40;
    int endL = 50;
    int startS = 40;
    int endS = 100;
    int startH = 260;
    int endH = 70;

    float stepL = (endL - startL) / (float) steps;
    float stepS = (endS - startS) / (float) steps;
    float stepH = (endH - startH) / (float) steps;

    float h = startH + step * stepH;
    float s = startS + step * stepS;
    float l = startL + step * stepL;
    int[] rgb = hsla2rgbIntArray(h, s, l, 1);

    return new Color(rgb[0], rgb[1], rgb[2]);
  }

  /**
   * Convert HSL values to a RGB Color.
   *
   * @param h     Hue is specified as degrees in the range 0 - 360.
   * @param s     Saturation is specified as a percentage in the range 1 - 100.
   * @param l     Luminance is specified as a percentage in the range 1 - 100.
   * @param alpha the alpha value between 0 - 1
   *              adapted from <a href="https://svn.codehaus.org/griffon/builders/gfxbuilder/tags/GFXBUILDER_0.2/">...</a>
   *              gfxbuilder-core/src/main/com/camick/awt/HSLColor.java
   * @return 4int array [0-255]
   */
  public static int[] hsla2rgbIntArray(float h, float s, float l, float alpha) {
    if (s < 0.0f || s > 100.0f) {
      String message = "Color parameter outside of expected range - Saturation";
      throw new IllegalArgumentException(message);
    }

    if (l < 0.0f || l > 100.0f) {
      String message = "Color parameter outside of expected range - Luminance";
      throw new IllegalArgumentException(message);
    }

    if (alpha < 0.0f || alpha > 1.0f) {
      String message = "Color parameter outside of expected range - Alpha";
      throw new IllegalArgumentException(message);
    }

    //  Formula needs all values between 0 - 1.

    h = h % 360.0f;
    h /= 360f;
    s /= 100f;
    l /= 100f;

    float q;

    if (l < 0.5)
      q = l * (1 + s);
    else
      q = (l + s) - (s * l);

    float p = 2 * l - q;

    int r = Math.round(Math.min(255, Math.max(0, pqh2RgbVal(p, q, h + (1.0f / 3.0f)) * 256)));
    int g = Math.round(Math.min(255, Math.max(0, pqh2RgbVal(p, q, h) * 256)));
    int b = Math.round(Math.min(255, Math.max(0, pqh2RgbVal(p, q, h - (1.0f / 3.0f)) * 256)));

    // noinspection UnnecessaryLocalVariable
    int[] array = {r, g, b, (int) (alpha * 255)};
    return array;
  }

  private static float pqh2RgbVal(float p, float q, float h) {
    if (h < 0)
      h += 1;

    if (h > 1)
      h -= 1;

    if (6 * h < 1) {
      return p + ((q - p) * 6 * h);
    }

    if (2 * h < 1) {
      return q;
    }

    if (3 * h < 2) {
      return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
    }

    return p;
  }
}
