public class Vector extends Pos {
    public Vector(int x, int y) {
        super(x, y);
    }

    public Vector(int x, int y, String color) {
        super(x, y, color);
    }

    public Vector(Pos p) {
        super(p.x, p.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector pos = (Vector) o;

        return normalized().equals(pos.normalized());
    }

    @Override
    public int hashCode() {
        return normalized().hashCode();
    }

    public static Vector of(Pos p) {
        return new Vector(p);
    }

    @Override
    public String toString() {
        return super.toString() + " hash:" + String.valueOf(hashCode()) + " normiert: " + normalized();
    }

    public String normalized() {
        if (x == 0 && y == 0)
            return "0,0";
        if (x == 0)
            return "0," + (y < 0 ? "-1" : "1");
        if (y == 0)
            return (x < 0 ? "-1" : "1") + ",0";
        int absX = Math.abs(x);
        String yValNormalized = String.valueOf((double) y / absX);
        int idxDot = yValNormalized.indexOf(".");
        if (idxDot > 0) {
            int lenDecimalPlaces = yValNormalized.length() - idxDot;
            if (lenDecimalPlaces > 8) // auf 8 stellen genauigkeit begrenzen, damit keine Rundungsfehler zum Problem werden.
                yValNormalized = yValNormalized.substring(0, idxDot + 8);
            if (yValNormalized.endsWith(".0"))
                yValNormalized = yValNormalized.replace(".0", "");
        }
        return (x / absX) + "," + yValNormalized;
    }
}
