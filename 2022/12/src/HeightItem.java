import java.util.Objects;

public class HeightItem {

    final Pos pos;
    final String elevation;

    final int height;

    Pos nextPos2End = null;

    int steps2End = -1;

    boolean isOfShortestPath = false;

    public HeightItem(Pos pos, char elevation) {
        this.pos = pos;
        this.elevation = String.valueOf(elevation);
        if (this.elevation.startsWith("S"))
            height = 0;
        else if (this.elevation.startsWith("E"))
            height = 25;
        else
            height = ((int) elevation) - 97;
    }

    public String toString() {
        return pos.toString();
    }

    public String toStringElevation() {
        if (isOfShortestPath)
            return " ";
        return elevation;
    }

    public String toStringHeight() {
        return " " + toLength(height, 2) + " ";
    }

    public String toStringPathLength() {
        return "[" + toLength(steps2End, 4) + "]";
    }

    public String toStringSVG() {
        String color = Integer.toHexString((int) (((height + 1) / 26.0) * 255.0));
        if (color.length() == 1)
            color = "0" + color;
//        if (isOfShortestPath)
//            color = "ff";
//        if (!isOfShortestPath) color = "00";
        return "<rect style=\"fill:#" + color + color + color + ";\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />";
    }

    String toLength(int input, int len) {
        String res = "" + input;
        while (res.length() < len)
            res = " " + res;
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeightItem that = (HeightItem) o;
        return Objects.equals(pos, that.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }
}
