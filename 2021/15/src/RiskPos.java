import java.util.ArrayList;
import java.util.Objects;

public class RiskPos {
    public final int x;
    public final int y;
    public final int risk;
    public boolean highlight = false;

    public ArrayList<RiskPos> shortestWayToStart = new ArrayList<>();

    public RiskPos(int x, int y, int risk) {
        this.x = x;
        this.y = y;
        this.risk = risk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiskPos riskPos = (RiskPos) o;
        return x == riskPos.x && y == riskPos.y && risk == riskPos.risk;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, risk);
    }

    @Override
    public String toString() {
        if (highlight)
            return "<" + risk + ">";
        return " " + risk + " ";
    }
}
