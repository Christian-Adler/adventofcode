import java.util.Objects;

public class Vector {
    public static final Vector vec0 = new Vector(0, 0, 0);

    long x;
    long y;
    long z;

    public Vector(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(String str) {
        String[] split = str.replace("<", "").replace(">", "").trim().split(",");
        this.x = Long.parseLong(split[0]);
        this.y = Long.parseLong(split[1]);
        this.z = Long.parseLong(split[2]);
    }

    public void add(Vector vector) {
        x += vector.x;
        y += vector.y;
        z += vector.z;
    }

    public long dist(Vector vector) {
        long dist = 0;
        dist += Math.abs(x - vector.x);
        dist += Math.abs(y - vector.y);
        dist += Math.abs(y - vector.z);
        return dist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return x == vector.x && y == vector.y && z == vector.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Vector{" + "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
