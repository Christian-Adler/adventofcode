import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task {

    List<Pattern> ruleSet = new ArrayList<>();

    Set<Pos> grid = new HashSet<>();

    // Start
    int gridSize = 3;

    public void init() {
    }

    public void addLine(String input) {
        ruleSet.add(new Pattern(input));
    }

    public void afterParse() {
//        out(ruleSet);
        grid = new HashSet<>();

        // Start
        gridSize = 3;
        grid.add(new Pos(1, 0));
        grid.add(new Pos(2, 1));
        grid.add(new Pos(0, 2));
        grid.add(new Pos(1, 2));
        grid.add(new Pos(2, 2));

        outGrid(gridSize, grid);

        for (int i = 0; i < 18; i++) { // Part 1: 5 , Part 2: 18
            List<String> squaresAsStr = new ArrayList<>();

            int smallSquareWidth;
            if (gridSize % 2 == 0) {
                smallSquareWidth = 2;
            } else {
                smallSquareWidth = 3;
            }

            int squaresPerDim = gridSize / smallSquareWidth;

            for (int yS = 0; yS < squaresPerDim; yS++) {
                for (int xS = 0; xS < squaresPerDim; xS++) {

                    Pos smallSquareTopLeft = new Pos(xS * smallSquareWidth, yS * smallSquareWidth);

                    StringBuilder smallSquare = new StringBuilder();
                    for (int y = 0; y < smallSquareWidth; y++) {
                        for (int x = 0; x < smallSquareWidth; x++) {
                            smallSquare.append(grid.contains(smallSquareTopLeft.addToNew(x, y)) ? "#" : ".");
                        }
                    }
                    squaresAsStr.add(smallSquare.toString());
                }
            }

//            out("squaresAsStr", squaresAsStr);

            // Grid zurueck setzen
            grid.clear();

            // SmallSquares -  Pattern matching
            int smallSquareWidthAfterPatternMatch = smallSquareWidth + 1;
            Pos smallSquareTopLeft = new Pos(0, 0);
            for (int j = 0; j < squaresAsStr.size(); j++) {
                if (j % squaresPerDim == 0 && j > 0) {
                    smallSquareTopLeft.x = 0;
                    smallSquareTopLeft.y += smallSquareWidthAfterPatternMatch;
                }

                String squareStr = squaresAsStr.get(j);

                for (Pattern pattern : ruleSet) {
                    if (pattern.matches(squareStr)) {
                        for (Pos outputPos : pattern.output) {
                            grid.add(outputPos.addToNew(smallSquareTopLeft));
                        }
                        break;
                    }
                }

                smallSquareTopLeft.x += smallSquareWidthAfterPatternMatch;
            }

            gridSize += squaresPerDim;
//            outGrid(gridSize, grid);
        }

        out("Part 1 Pixels on:", grid.size());
    }

    private void outGrid(int gridSize, Set<Pos> grid) {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                builder.append(grid.contains(new Pos(x, y)) ? "#" : ".");
            }
            builder.append("\r\n");
        }

        out(builder);
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {
        SVG svg = new SVG();
        for (Pos p : grid) {
            svg.add(p);
        }
        return svg.toSVGStringAged();
    }
}
