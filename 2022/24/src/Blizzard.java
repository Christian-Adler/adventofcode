public class Blizzard {
    static Pos RIGHT = new Pos(1, 0);
    static Pos LEFT = new Pos(-1, 0);
    static Pos UP = new Pos(0, -1);
    static Pos DOWN = new Pos(0, 1);

    Pos pos = null;
    Pos step = null;

    char dir = '_';

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Blizzard{");
        sb.append("dir='").append(dir).append('\'');
        sb.append('}');
        return sb.toString();
    }

    String toStringSimple() {
        return "" + dir;
    }
}
