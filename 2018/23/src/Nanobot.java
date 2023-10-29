public class Nanobot {
    final Pos pos;
    final int range;

    public Nanobot(Pos pos, int range) {
        this.pos = pos;
        this.range = range;
    }

    public boolean isInRange(Pos p) {
        return pos.manhattanDistance(p) <= range;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Nanobot{");
        sb.append("pos=").append(pos);
        sb.append(", range=").append(range);
        sb.append('}');
        return sb.toString();
    }

    public Cuboid getCuboid() {
        return new Cuboid(pos.addToNew(-range, -range, -range), pos.addToNew(range, range, range));
    }

    public boolean isInRange(Cuboid cuboid) {

        int xDistance = 0;
        if (pos.x < cuboid.minCorner.x)
            xDistance = Math.abs(pos.x - cuboid.minCorner.x);
        else if (pos.x > cuboid.maxCorner.x)
            xDistance = Math.abs(pos.x - cuboid.maxCorner.x);

        int yDistance = 0;
        if (pos.y < cuboid.minCorner.y)
            yDistance = Math.abs(pos.y - cuboid.minCorner.y);
        else if (pos.y > cuboid.maxCorner.y)
            yDistance = Math.abs(pos.y - cuboid.maxCorner.y);

        int zDistance = 0;
        if (pos.z < cuboid.minCorner.z)
            zDistance = Math.abs(pos.z - cuboid.minCorner.z);
        else if (pos.z > cuboid.maxCorner.z)
            zDistance = Math.abs(pos.z - cuboid.maxCorner.z);

        int manhattenDist = xDistance + yDistance + zDistance;

        return range >= manhattenDist;
    }
}
