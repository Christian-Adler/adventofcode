import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Cart {
    static List<String> dirChange = Arrays.asList("/", "\\", "+");

    public Pos pos = new Pos(0, 0);
    Pos direction = new Pos(1, 0);
    int intersectionCounter = 0;

    public Cart(Pos pos, Pos direction) {
        this.pos = pos;
        this.direction = direction;
    }

    public void tick(Map<Pos, String> trackMap) {
        pos.add(direction);

        String track = trackMap.get(pos);
        if (dirChange.contains(track)) {
            switch (track) {
                case "+" -> {
                    if (intersectionCounter == 0)
                        direction = direction.rotate90DegToNew(true);
                    else if (intersectionCounter == 2)
                        direction = direction.rotate90DegToNew(false);
                    intersectionCounter = (intersectionCounter + 1) % 3;
                }
                case "\\" -> {
                    if (direction.y == 0)
                        direction = direction.rotate90DegToNew(false);
                    else
                        direction = direction.rotate90DegToNew(true);
                }
                case "/" -> {
                    if (direction.y == 0)
                        direction = direction.rotate90DegToNew(true);
                    else
                        direction = direction.rotate90DegToNew(false);
                }
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("C{");
        sb.append(pos);
//        sb.append(", direction=").append(direction);
        sb.append('}');
        return sb.toString();
    }
}
