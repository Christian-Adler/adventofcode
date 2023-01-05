public class Util {

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
     * @param h Hue is specified as degrees in the range 0 - 360.
     * @param s Saturation is specified as a percentage in the range 1 - 100.
     * @param l Luminance is specified as a percentage in the range 1 - 100.
     * @paran alpha  the alpha value between 0 - 1
     * adapted from https://svn.codehaus.org/griffon/builders/gfxbuilder/tags/GFXBUILDER_0.2/
     * gfxbuilder-core/src/main/com/camick/awt/HSLColor.java
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

        float q = 0;

        if (l < 0.5)
            q = l * (1 + s);
        else
            q = (l + s) - (s * l);

        float p = 2 * l - q;

        int r = Math.round(Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f)) * 256));
        int g = Math.round(Math.max(0, HueToRGB(p, q, h) * 256));
        int b = Math.round(Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f)) * 256));

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
