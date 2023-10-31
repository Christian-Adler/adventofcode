public class Pos {
    int x = 0;
    int y = 0;
    int z = 0;
    int t = 0;

    public Pos(int x, int y, int z, int t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }


    void add(Pos add) {
        this.x += add.x;
        this.y += add.y;
        this.z += add.z;
        this.t += add.t;
    }

    Pos addToNew(int x, int y, int z, int t) {
        return new Pos(this.x + x, this.y + y, this.z + z, this.t + t);
    }

    Pos addToNew(Pos other) {
        return addToNew(other.x, other.y, other.z, other.t);
    }


    Pos copy() {
        return new Pos(x, y, z, t);
    }


    /**
     * https://en.wikipedia.org/wiki/Taxicab_geometry
     *
     * @return
     */
    int manhattanDistance() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z) + Math.abs(t);
    }

    int manhattanDistance(Pos other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z) + Math.abs(t - other.t);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pos{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", z=").append(z);
        sb.append(", t=").append(t);
        sb.append('}');
        return sb.toString();
    }
}
