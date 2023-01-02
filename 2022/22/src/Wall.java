public class Wall extends Tile {
    public Wall(Pos pos) {
        super(pos);
        isTile = false;
        isWall = true;
    }

    public String toString() {
        return "#";
    }

    public String toStringSVG() {
        return "<rect style=\"fill:#202020;\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />";
    }
}
