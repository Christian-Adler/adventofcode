public class Tile {
    Pos pos;

    boolean visited = false;

    boolean isTile = true;
    boolean isWall = false;

    Tile(Pos pos) {
        this.pos = pos;
    }

    public String toStringFull() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + " {");
        sb.append("pos=").append(pos);
        sb.append('}');
        return sb.toString();
    }

    public String toString() {
        if (visited)
            return "o";
        return ".";
    }

    public String toStringSVG() {
        return "<rect style=\"fill:" + (visited ? "#ff0000" : "#aaaaaa") + ";\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />";
    }
}
