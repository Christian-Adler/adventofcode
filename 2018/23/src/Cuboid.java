import java.util.*;

public class Cuboid {
    Pos minCorner;
    Pos maxCorner;
    int nanobotsInRange = 0;

    public Cuboid(Pos pos1, Pos pos2) {
        this.minCorner = new Pos(Math.min(pos1.x, pos2.x), Math.min(pos1.y, pos2.y), Math.min(pos1.z, pos2.z));
        this.maxCorner = new Pos(Math.max(pos1.x, pos2.x), Math.max(pos1.y, pos2.y), Math.max(pos1.z, pos2.z));
    }

    public Cuboid intersect(Cuboid other) {
        if (other.minCorner.x > maxCorner.x || other.maxCorner.x < minCorner.x
                || other.minCorner.y > maxCorner.y || other.maxCorner.y < minCorner.y
                || other.minCorner.z > maxCorner.z || other.maxCorner.z < minCorner.z)
            return null;
        return new Cuboid(
                new Pos(Math.max(minCorner.x, other.minCorner.x),
                        Math.max(minCorner.y, other.minCorner.y),
                        Math.max(minCorner.z, other.minCorner.z)),
                new Pos(Math.min(maxCorner.x, other.maxCorner.x),
                        Math.min(maxCorner.y, other.maxCorner.y),
                        Math.min(maxCorner.z, other.maxCorner.z))
        );
    }

    public Cuboid union(Cuboid other) {
        return new Cuboid(
                new Pos(Math.min(minCorner.x, other.minCorner.x),
                        Math.min(minCorner.y, other.minCorner.y),
                        Math.min(minCorner.z, other.minCorner.z)),
                new Pos(Math.max(maxCorner.x, other.maxCorner.x),
                        Math.max(maxCorner.y, other.maxCorner.y),
                        Math.max(maxCorner.z, other.maxCorner.z))
        );
    }

    public List<Cuboid> split2() {
        List<Cuboid> result = new ArrayList<>();
        int xLength = Math.abs(maxCorner.x - minCorner.x);
        int yLength = Math.abs(maxCorner.y - minCorner.y);
        int zLength = Math.abs(maxCorner.z - minCorner.z);
        int maxLength = Math.max(Math.max(xLength, yLength), zLength);

        if (maxLength == 1)
            return result; // kein Split

        if (maxLength == xLength) {
            int split = minCorner.x + maxLength / 2;
            result.add(new Cuboid(minCorner.copy(), new Pos(split, maxCorner.y, maxCorner.z)));
            result.add(new Cuboid(new Pos(split, minCorner.y, minCorner.z), maxCorner.copy()));
        } else if (maxLength == yLength) {
            int split = minCorner.y + maxLength / 2;
            result.add(new Cuboid(minCorner.copy(), new Pos(maxCorner.x, split, maxCorner.z)));
            result.add(new Cuboid(new Pos(minCorner.x, split, minCorner.z), maxCorner.copy()));
        } else {
            int split = minCorner.z + maxLength / 2;
            result.add(new Cuboid(minCorner.copy(), new Pos(maxCorner.x, maxCorner.y, split)));
            result.add(new Cuboid(new Pos(minCorner.x, minCorner.y, split), maxCorner.copy()));
        }

        return result;
    }

    public boolean isMiniCube() {
        int xLength = Math.abs(maxCorner.x - minCorner.x);
        if (xLength > 1) return false;
        int yLength = Math.abs(maxCorner.y - minCorner.y);
        if (yLength > 1) return false;
        int zLength = Math.abs(maxCorner.z - minCorner.z);
        if (zLength > 1) return false;
        return true;
    }

    public Set<Pos> getAllCorners() {
        Set<Pos> corners = new HashSet<>();
        corners.add(new Pos(minCorner.x, minCorner.y, minCorner.z));
        corners.add(new Pos(maxCorner.x, minCorner.y, minCorner.z));
        corners.add(new Pos(maxCorner.x, maxCorner.y, minCorner.z));
        corners.add(new Pos(maxCorner.x, maxCorner.y, maxCorner.z));
        corners.add(new Pos(maxCorner.x, minCorner.y, maxCorner.z));
        corners.add(new Pos(minCorner.x, minCorner.y, maxCorner.z));
        corners.add(new Pos(minCorner.x, maxCorner.y, maxCorner.z));
        corners.add(new Pos(minCorner.x, maxCorner.y, minCorner.z));
        return corners;
    }

    @Override
    public String toString() {
        int xLength = Math.abs(maxCorner.x - minCorner.x);
        int yLength = Math.abs(maxCorner.y - minCorner.y);
        int zLength = Math.abs(maxCorner.z - minCorner.z);
        int maxLength = Math.max(Math.max(xLength, yLength), zLength);
        int minLength = Math.min(Math.min(xLength, yLength), zLength);

        final StringBuilder sb = new StringBuilder("Cuboid{");
        sb.append("minCorner=").append(minCorner);
        sb.append(", maxCorner=").append(maxCorner);
        sb.append(", minLength=").append(minLength);
        sb.append(", maxLength=").append(maxLength);
        sb.append(", nanobotsInRange=").append(nanobotsInRange);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuboid cuboid = (Cuboid) o;
        return Objects.equals(minCorner, cuboid.minCorner) && Objects.equals(maxCorner, cuboid.maxCorner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minCorner, maxCorner);
    }
}
