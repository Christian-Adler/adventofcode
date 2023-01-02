import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Lines {
    public List<Line> lines = new LinkedList<>();

    public void add(String line) {
        lines.add(new Line(line));
    }

    public long errScore() {
        return lines.stream().mapToLong(line -> line.isCorrupt ? line.syntaxErrScore : 0).sum();
    }

    public long completeScore() {
        long[] closingScores = lines.stream().filter(line -> line.isIncomplete).mapToLong(line -> line.completeScore).sorted().toArray();
        // for (long l :                closingScores)            System.out.println(l);
        return closingScores[closingScores.length / 2];
    }

    @Override
    public String toString() {
        return lines.stream().map(Line::toString).collect(Collectors.joining("\r\n"));
    }
}
