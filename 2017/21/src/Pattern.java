import java.util.*;

public class Pattern {
    final int size;
    final List<Pos> output = new ArrayList<>();
    final Set<String> matches = new HashSet<>();

    public Pattern(String input) {
        String[] split = input.split(" => ");
        String matchStr = split[0];
        size = matchStr.length() == 5 ? 2 : 3;

        // output
        String[] outputData = split[1].split("/");
        for (int y = 0; y < outputData.length; y++) {
            String[] outputLine = outputData[y].split("");
            for (int x = 0; x < outputLine.length; x++) {
                String s = outputLine[x];
                if (s.equals("#"))
                    output.add(new Pos(x, y));
            }
        }

        // matches
        List<Pos> checkPositions;
        List<Pos> positions = new ArrayList<>();
        if (size == 2) {
            positions.add(new Pos(-1, -1, matchStr.charAt(0) == '#'));
            positions.add(new Pos(1, -1, matchStr.charAt(1) == '#'));
            positions.add(new Pos(-1, 1, matchStr.charAt(3) == '#'));
            positions.add(new Pos(1, 1, matchStr.charAt(4) == '#'));

            checkPositions = Arrays.asList(new Pos(-1, -1), new Pos(1, -1), new Pos(-1, 1), new Pos(1, 1));
        } else {
            positions.add(new Pos(-1, -1, matchStr.charAt(0) == '#'));
            positions.add(new Pos(0, -1, matchStr.charAt(1) == '#'));
            positions.add(new Pos(1, -1, matchStr.charAt(2) == '#'));

            positions.add(new Pos(-1, 0, matchStr.charAt(4) == '#'));
            positions.add(new Pos(0, 0, matchStr.charAt(5) == '#'));
            positions.add(new Pos(1, 0, matchStr.charAt(6) == '#'));

            positions.add(new Pos(-1, 1, matchStr.charAt(8) == '#'));
            positions.add(new Pos(0, 1, matchStr.charAt(9) == '#'));
            positions.add(new Pos(1, 1, matchStr.charAt(10) == '#'));

            checkPositions = Arrays.asList( //
                    new Pos(-1, -1), new Pos(0, -1), new Pos(1, -1), //
                    new Pos(-1, 0), new Pos(0, 0), new Pos(1, 0), //
                    new Pos(-1, 1), new Pos(0, 1), new Pos(1, 1)
            );
        }

        // Max 8 patterns 4x gedreht und jedes Mal auch Flip

        addMatch(checkPositions, positions);

        positions.forEach(Pos::flipV);
        addMatch(checkPositions, positions);

        positions.forEach(p -> p.rotate90Deg(false));
        addMatch(checkPositions, positions);

        positions.forEach(Pos::flipH);
        addMatch(checkPositions, positions);

        positions.forEach(p -> p.rotate90Deg(false));
        addMatch(checkPositions, positions);

        positions.forEach(Pos::flipV);
        addMatch(checkPositions, positions);

        positions.forEach(p -> p.rotate90Deg(false));
        addMatch(checkPositions, positions);

        positions.forEach(Pos::flipH);
        addMatch(checkPositions, positions);
    }

    private void addMatch(List<Pos> checkPositions, List<Pos> positions) {
        StringBuilder match = new StringBuilder();
        for (Pos checkPosition : checkPositions) {
            int idx = positions.indexOf(checkPosition);
            match.append(positions.get(idx).active ? "#" : ".");
        }
        matches.add(match.toString());
    }

    public boolean matches(String test) {
        for (String match : matches) {
            if (match.equals(test))
                return true;
        }
        return false;
    }

    public String toString() {
        return "Matches (" + matches.size() + "):\r\n" + toStringMatches() + "\r\nOutput:\r\n" + toStringOutput();
    }

    public String toStringOutput() {
        StringBuilder result = new StringBuilder();

        for (int y = 0; y <= size; y++) {
            for (int x = 0; x <= size; x++) {
                result.append(output.contains(new Pos(x, y)) ? "#" : ".");
            }
            result.append("\r\n");
        }

        return result.toString();
    }

    public String toStringMatches() {
        StringBuilder result = new StringBuilder();

        for (String match : matches) {
            for (int i = 0; i < match.length(); i++) {
                if (i % size == 0 && !result.isEmpty()) result.append("\r\n");
                result.append(match.charAt(i));
            }
            result.append("\r\n");
        }

        return result.toString();
    }
}
