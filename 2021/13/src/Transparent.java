import java.util.*;
import java.util.stream.Collectors;

public class Transparent {
    private final Set<Pos> positions = new HashSet<>();

    public int size() {
        return positions.size();
    }

    public void add(Pos pos) {
        positions.add(pos);
    }

    @Override
    public String toString() {
        int maxX = positions.stream().mapToInt(pos -> pos.x).max().orElse(0);
        int maxY = positions.stream().mapToInt(pos -> pos.y).max().orElse(0);

        if (maxY == 0 && maxX == 0)
            return "empty";

        List<Pos> sortedPositions = positions.stream().sorted(Comparator.comparingInt((Pos p) -> p.y).thenComparingInt(p -> p.x)).collect(Collectors.toList());

        Iterator<Pos> posIterator = sortedPositions.iterator();

        Pos actPos = posIterator.next();

        StringBuilder builder = new StringBuilder();

        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {

                Pos checkPos = new Pos(x, y);
                if (checkPos.equals(actPos)) {
                    if (posIterator.hasNext())
                        actPos = posIterator.next();
                    else
                        actPos = null;
                    builder.append("#");
                } else
                    builder.append(" ");
            }
            builder.append("\r\n");
        }

        return builder.toString();
    }

    public void fold(String line) {
        if (line.contains("y"))
            foldY(Integer.parseInt(line.replaceAll("\\D+", "")));
        else if (line.contains("x"))
            foldX(Integer.parseInt(line.replaceAll("\\D+", "")));
        else
            System.out.println("Invalid fold line");
    }

    private void foldX(int foldLine) {
        System.out.println(" fold x " + foldLine);
        Set<Pos> changed = new HashSet<>();
        Iterator<Pos> it = positions.iterator();
        while (it.hasNext()) {
            Pos pos = it.next();
            if (pos.x == foldLine)
                it.remove();
            else if (pos.x > foldLine) {
                int newX = foldLine - (pos.x - foldLine);
                if (newX >= 0) {
                    changed.add(new Pos(newX, pos.y));

                    it.remove();
                }
            }
        }
        positions.addAll(changed);
    }


    private void foldY(int foldLine) {
        System.out.println(" fold y " + foldLine);
        Set<Pos> changed = new HashSet<>();
        Iterator<Pos> it = positions.iterator();
        while (it.hasNext()) {
            Pos pos = it.next();
            if (pos.y == foldLine)
                it.remove();
            else if (pos.y > foldLine) {
                int newY = foldLine - (pos.y - foldLine);
                if (newY >= 0) {
                    changed.add(new Pos(pos.x, newY));

                    it.remove();
                }
            }
        }
        positions.addAll(changed);
    }
}
