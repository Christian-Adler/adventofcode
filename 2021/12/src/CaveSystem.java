import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CaveSystem {
    public final Set<Cave> caves = new HashSet<>();
    public final Set<CaveConnection> caveConnections = new HashSet<>();
    public Cave start = null;
    public Cave end = null;

    public void addCaves(String[] split) {
        Cave c1 = new Cave(split[0]);
        Cave c2 = new Cave(split[1]);
        caves.add(c1);
        caves.add(c2);
        caveConnections.add(new CaveConnection(c1, c2));
        if (c1.isStart)
            start = c1;
        else if (c1.isEnd)
            end = c1;
        else if (c2.isStart)
            start = c2;
        else if (c2.isEnd)
            end = c2;
    }

    public void findAllPaths() {
        List<CavePath> allPaths = new LinkedList<>();

        List<Cave> startPath = new LinkedList<>();
        startPath.add(start);
        CavePath actPath = new CavePath(startPath);

        Cave actCave = start;

        findSubPaths(allPaths, actPath, actCave);

        allPaths.forEach(System.out::println);

        System.out.println("Num Paths " + allPaths.size());
    }

    private void findSubPaths(List<CavePath> allPaths, CavePath actPath, Cave actCave) {
        Set<Cave> possibleConnections = findPossibleConnections(actCave);

        for (Cave c : possibleConnections) {
            // Rekursionsabbruch?
            if (c.isEnd) {
                CavePath path = actPath.add(c);
                allPaths.add(path);
            } else if (c.isStart) {
                // nichts tun - wieder am Start angelangt.
            } else if (!c.isBigCave) { // kleine Hoehle?
                // Schon im Pfad vorhanden? Dann nichts tun - sonst anhaengen
                // if (!actPath.contains(c)) { // Fall 1

                if (actPath.hasOnceDuplicateSmallCave) {
                    if (!actPath.contains(c)) {
                        CavePath path = actPath.add(c);
                        findSubPaths(allPaths, path, c);
                    }
                } else {
                    boolean setDuplicateFlage = (actPath.contains(c));

                    CavePath path = actPath.add(c);
                    path.hasOnceDuplicateSmallCave = setDuplicateFlage;

                    findSubPaths(allPaths, path, c);
                }

            } else { // Grosse Hoehle
                CavePath path = actPath.add(c);
                findSubPaths(allPaths, path, c);
            }
        }
    }

    private Set<Cave> findPossibleConnections(Cave cave) {
        Set<Cave> result = new HashSet<>();
        caveConnections.forEach(caveConnection -> {
            if (caveConnection.cave1.equals(cave))
                result.add(caveConnection.cave2);
            else if (caveConnection.cave2.equals(cave))
                result.add(caveConnection.cave1);
        });
        return result;
    }

    @Override
    public String toString() {
        return "CaveSystem{" +
                "caves=" + caves +
                ", caveConnections=" + caveConnections +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
