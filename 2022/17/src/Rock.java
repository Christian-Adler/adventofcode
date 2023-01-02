import java.util.ArrayList;
import java.util.List;

public class Rock {
    // Relativ von links unten (0,0)
    List<Pos> positions = new ArrayList<>();
    long width = 1;

    public Rock(Pos... poss) {
        for (Pos pos : poss) {
            if (pos.x + 1 > width)
                width = pos.x + 1;
            positions.add(pos);
        }
    }
}
