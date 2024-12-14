import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class Img {
  int xMin = Integer.MAX_VALUE;
  int yMin = Integer.MAX_VALUE;
  int xMax = Integer.MIN_VALUE;
  int yMax = Integer.MIN_VALUE;
  Set<Vec> positions = new LinkedHashSet<>();

  void add(Vec vec) {
    add(vec, null);
  }

  @SuppressWarnings("SameParameterValue")
  void add(Vec vec, Object color) {
    int x = vec.x;
    int y = vec.y;
    this.xMax = Math.max(this.xMax, x);
    this.xMin = Math.min(this.xMin, x);
    this.yMax = Math.max(this.yMax, y);
    this.yMin = Math.min(this.yMin, y);

    Vec p = new Vec(x, y, color != null ? color : vec.color);

    positions.add(p);
  }

  public void writeBitmap() throws IOException {
    writeBitmap(null);
  }

  public void writeBitmap(String path) throws IOException {
    int width = xMax - xMin;
    int height = yMax - yMin;
    BufferedImage img = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);
    for (Vec vec : positions) {
      int yIdx = vec.y - this.yMin;
      int xIdx = vec.x - this.xMin;

      // bitmap needs int
      int rgb = Color.RED.getRGB();
      if (vec.color instanceof Color col)
        rgb = col.getRGB();
      if (vec.color instanceof Integer i)
        rgb = i;

      img.setRGB(xIdx, yIdx, rgb);
    }
    ImageIO.write(img, "bmp", new File(path == null ? "./img.bmp" : path));
  }

  String toConsoleString() {
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
    int startL = 40;
    int endL = 50;
    int startS = 40;
    int endS = 100;
    int startH = 260;
    int endH = 70;

    float steps = positions.size(); // Anzahl schritte aus Task...

    float stepL = (endL - startL) / steps;
    float stepS = (endS - startS) / steps;
    float stepH = (endH - startH) / steps;


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
        float h = startH + count * stepH;
        float s = startS + count * stepS;
        float l = startL + count * stepL;
        rgb = HSLtoRGB(h, s, l);
      }
      count++;
      res.append("<rect style=\"fill:").append(rgb).append(";\" width=\"1\" height=\"1\" x=\"").append(vec.x - this.xMin).append("\" y=\"").append(vec.y - this.yMin).append("\" />\r\n");
    }
    res.append("</g>\r\n");
    res.append("</svg>\r\n");

    return res.toString();
  }

  String toSVGString() {
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
  public static String HSLtoRGB(float h, float s, float l) {
    int[] rgb = HSLtoRGB(h, s, l, 1);
    String res = "#";
    String hex = Integer.toHexString(rgb[0]);
    if (hex.length() < 2)
      res += "0";
    res += hex;
    hex = Integer.toHexString(rgb[1]);
    if (hex.length() < 2)
      res += "0";
    res += hex;
    hex = Integer.toHexString(rgb[2]);
    if (hex.length() < 2)
      res += "0";
    res += hex;

    return res;
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
   */
  public static int[] HSLtoRGB(float h, float s, float l, float alpha) {
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

    int r = Math.round(Math.min(255, Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f)) * 256)));
    int g = Math.round(Math.min(255, Math.max(0, HueToRGB(p, q, h) * 256)));
    int b = Math.round(Math.min(255, Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f)) * 256)));

    // noinspection UnnecessaryLocalVariable
    int[] array = {r, g, b};
    return array;
  }

  private static float HueToRGB(float p, float q, float h) {
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
