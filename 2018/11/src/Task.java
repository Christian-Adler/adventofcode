public class Task {

    public void init() {
    }

    public void addLine(String input) {
    }

    public void afterParse() {
        out(new Grid(8).calcPowerLevel(new Pos(3, 5))); // 4
        out(new Grid(57).calcPowerLevel(new Pos(122, 79))); //-5
        out(new Grid(39).calcPowerLevel(new Pos(217, 196))); // 0
        out(new Grid(71).calcPowerLevel(new Pos(101, 153))); // 4

        Grid grid = new Grid(18);
        grid.calcGrid();
        Pos topLeftPosMaxPowerSquare = grid.findMaxPower3x3Square();
        out("topLeftPosMaxPowerSquare", topLeftPosMaxPowerSquare);

        grid = new Grid(42);
        grid.calcGrid();
        topLeftPosMaxPowerSquare = grid.findMaxPower3x3Square();
        out("topLeftPosMaxPowerSquare", topLeftPosMaxPowerSquare);

        grid = new Grid(5535);
        grid.calcGrid();
        topLeftPosMaxPowerSquare = grid.findMaxPower3x3Square();
        out("Part1", "topLeftPosMaxPowerSquare", topLeftPosMaxPowerSquare, ":", topLeftPosMaxPowerSquare.x + "," + topLeftPosMaxPowerSquare.y);

        // Part 2
        GridByPointers grid2 = new GridByPointers(18);
        grid2.calcGrid();
        topLeftPosMaxPowerSquare = grid2.findMaxPower3x3Square();
        out("topLeftPosMaxPowerSquare", topLeftPosMaxPowerSquare);

        grid2 = new GridByPointers(42);
        grid2.calcGrid();
        topLeftPosMaxPowerSquare = grid2.findMaxPower3x3Square();
        out("topLeftPosMaxPowerSquare", topLeftPosMaxPowerSquare);

        grid2 = new GridByPointers(5535);
        grid2.calcGrid();
        topLeftPosMaxPowerSquare = grid2.findMaxPower3x3Square();
        out("Part1 Grid2", "topLeftPosMaxPowerSquare", topLeftPosMaxPowerSquare, ":", topLeftPosMaxPowerSquare.x + "," + topLeftPosMaxPowerSquare.y);

//        grid2 = new GridByPointers(18);
//        grid2.calcGrid();
//        GridByPointers.MaxPowerSquare maxPowerS2 = grid2.findMaxPowerAnySquare();
//        topLeftPosMaxPowerSquare = maxPowerS2.pos();
//        int squareSize = maxPowerS2.squareSize();
//        out("topLeftPosMaxPowerSquare", topLeftPosMaxPowerSquare, "squareSize", squareSize, ":", topLeftPosMaxPowerSquare.x + "," + topLeftPosMaxPowerSquare.y + "," + squareSize);

        grid2 = new GridByPointers(5535);
        grid2.calcGrid();
        GridByPointers.MaxPowerSquare maxPowerS2 = grid2.findMaxPowerAnySquare();
        topLeftPosMaxPowerSquare = maxPowerS2.pos();
        int squareSize = maxPowerS2.squareSize();
        out("Part2 ", "topLeftPosMaxPowerSquare", topLeftPosMaxPowerSquare, "squareSize", squareSize, ":", topLeftPosMaxPowerSquare.x + "," + topLeftPosMaxPowerSquare.y + "," + squareSize);
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
