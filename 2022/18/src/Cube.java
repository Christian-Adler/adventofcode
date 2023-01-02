import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Cube {
    Pos pos = null;
    Set<Cube> neighbors = new HashSet<>();
    Set<Pos> noNeigbor = new HashSet<>();

    boolean visited = false;

    Cube(Pos pos) {
        this.pos = pos;
    }

    void findNeighbors(Map<Pos, Cube> others) {
        Set<Pos> possibleNeighbors = new HashSet<>();
        possibleNeighbors.add(new Pos(pos.x + 1, pos.y, pos.z));
        possibleNeighbors.add(new Pos(pos.x - 1, pos.y, pos.z));
        possibleNeighbors.add(new Pos(pos.x, pos.y + 1, pos.z));
        possibleNeighbors.add(new Pos(pos.x, pos.y - 1, pos.z));
        possibleNeighbors.add(new Pos(pos.x, pos.y, pos.z + 1));
        possibleNeighbors.add(new Pos(pos.x, pos.y, pos.z - 1));

        for (Pos possibleNeighbor : possibleNeighbors) {
            Cube neighbor = others.get(possibleNeighbor);
            if (neighbor != null)
                neighbors.add(neighbor);
            else
                noNeigbor.add(possibleNeighbor);
        }
    }

    @Override
    public String toString() {
        return "Cube{" +
                "pos=" + pos +
                ", neighbors=" + neighbors.size() +
                ", visited=" + visited +
                '}';
    }
}
