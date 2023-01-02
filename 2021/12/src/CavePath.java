import java.util.LinkedList;
import java.util.List;

public class CavePath {


    private final List<Cave> path;
    public boolean hasOnceDuplicateSmallCave = false;

    public CavePath(List<Cave> path) {
        this.path = path;
    }

    public CavePath(List<Cave> path, boolean hasOnceDuplicateSmallCave) {
        this.path = path;
        this.hasOnceDuplicateSmallCave = hasOnceDuplicateSmallCave;
    }

    public CavePath add(Cave cave) {
        LinkedList<Cave> clone = new LinkedList<>(path);
        clone.add(cave);
        return new CavePath(clone, hasOnceDuplicateSmallCave);
    }

    public boolean contains(Cave cave) {
        return path.contains(cave);
    }

    @Override
    public String toString() {
        return "CavePath{" +
                "path=" + path +
                ", hasOnceDuplicateSmallCave=" + hasOnceDuplicateSmallCave +
                '}';
    }
}
