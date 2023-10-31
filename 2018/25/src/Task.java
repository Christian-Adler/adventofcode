import java.util.*;

public class Task {
    List<Set<Pos>> constellations = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        int[] split = Arrays.stream(input.trim().split(",")).mapToInt(Integer::parseInt).toArray();
        Pos p = new Pos(split[0], split[1], split[2], split[3]);

        // Alle Konstellationen durchgehen - es koennten mehrere treffen - dann muessen die vereint werden.
        LinkedList<Integer> matchingConstellationsIdxs = new LinkedList<>();
        for (int i = 0; i < constellations.size(); i++) {
            Set<Pos> constellation = constellations.get(i);

            for (Pos pos : constellation) {
                if (p.manhattanDistance(pos) <= 3) {
                    matchingConstellationsIdxs.add(i);
                    break;
                }
            }
        }

        if (matchingConstellationsIdxs.isEmpty()) {
            Set<Pos> newConstellation = new HashSet<>();
            newConstellation.add(p);
            constellations.add(newConstellation);
        } else if (matchingConstellationsIdxs.size() == 1) {
            constellations.get(matchingConstellationsIdxs.getFirst()).add(p);
        } else { // Mehr als eine treffenden -> Merge
            Set<Pos> merge = new HashSet<>();
            merge.add(p);
            Collections.reverse(matchingConstellationsIdxs); // rueckwaerts druchgehen!
            for (int matchingConstellationIdx : matchingConstellationsIdxs) {
                Set<Pos> removed = constellations.remove(matchingConstellationIdx);
                merge.addAll(removed);
            }
            constellations.add(merge);
        }
    }

    public void afterParse() {
//        out(constellations.stream().mapToInt(s -> s.size()).sum());
        out(constellations.size()); // < 348
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
