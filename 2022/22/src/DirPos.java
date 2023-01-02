import java.util.Objects;

public class DirPos {
    Pos pos = null;
    int direction = -1;

    public DirPos(Pos pos, int direction) {
        this.pos = pos;
        this.direction = direction;
    }

    public DirPos(int x, int y, int direction) {
        this.pos = new Pos(x, y);
        this.direction = direction;
    }

    public DirPos switchDir() {
        int directionSwitch = (direction + 2) % 4;
        DirPos result = new DirPos(pos.x, pos.y, directionSwitch);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirPos dirPos = (DirPos) o;
        return direction == dirPos.direction && Objects.equals(pos, dirPos.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, direction);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DirPos{");
        sb.append("pos=").append(pos);
        sb.append(", direction=").append(direction);
        sb.append('}');
        return sb.toString();
    }
}
