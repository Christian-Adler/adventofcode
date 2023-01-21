public class Disc {
    final int no;
    final int positions;
    final int time0Pos;

    public Disc(int no, int positions, int time0Pos) {
        this.no = no;
        this.positions = positions;
        this.time0Pos = time0Pos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Disc{");
        sb.append("no=").append(no);
        sb.append(", positions=").append(positions);
        sb.append(", time0Pos=").append(time0Pos);
        sb.append('}');
        return sb.toString();
    }

    public String atTime(int time) {
        final StringBuilder sb = new StringBuilder("|");
        int holePos = holeAtTime(time);
        for (int i = 0; i < positions; i++) {
            sb.append(holePos == i ? " " : "-");
        }
        sb.append("|");
        return sb.toString();
    }

    public int holeAtTime(int time) {
        return (time0Pos + time) % positions;
    }
}
