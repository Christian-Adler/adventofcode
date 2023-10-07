import java.util.HashMap;
import java.util.Map;

public class GridByPointers {
    final int serial;
    private final int gridSize = 300;
    private final Map<Pos, GridNode> map = new HashMap<>();

    public GridByPointers(int serial) {
        this.serial = serial;
    }

    long calcPowerLevel(Pos p) {
        return calcPowerLevel(p.x, p.y);
    }

    long calcPowerLevel(int px, int py) {
        long rackId = px + 10;
        long powerLevel = (rackId * py + serial) * rackId;
        long hundredsDigit = (powerLevel / 100) % 10;
        long pwl = hundredsDigit - 5;
        return pwl;
    }

    void calcGrid() {
        for (int y = 1; y <= gridSize; y++) {
            for (int x = 1; x <= gridSize; x++) {
                GridNode gridNode = new GridNode(new Pos(x, y));
                gridNode.powerLevel = calcPowerLevel(gridNode.pos);
                map.put(gridNode.pos, gridNode);
            }
        }

        // Pointer setzen
        for (int y = 1; y <= gridSize; y++) {
            for (int x = 1; x <= gridSize; x++) {
                Pos p = new Pos(x, y);
                GridNode gridNode = map.get(p);
                if (x > 1)
                    gridNode.prevX = map.get(p.addToNew(-1, 0));
                if (y > 1)
                    gridNode.prevY = map.get(p.addToNew(0, -1));
                if (x < gridSize)
                    gridNode.nextX = map.get(p.addToNew(1, 0));
                if (y < gridSize)
                    gridNode.nextY = map.get(p.addToNew(0, 1));
            }
        }
    }

    Pos findMaxPower3x3Square() {
        Pos result = null;
        long maxPower = Long.MIN_VALUE;
        for (int y = 1; y <= gridSize - 2; y++) {
            for (int x = 1; x <= 300 - 2; x++) {
                Pos p = new Pos(x, y);
                long sumPower = calcSquarePower(p, 3);

                if (sumPower > maxPower) {
                    maxPower = sumPower;
                    result = p;
                }
            }
        }
//        System.out.println(maxPower);
        return result;
    }

    long calcSquarePower(Pos p, int squareSize) {
        GridNode topLeft = map.get(p);
        return calcSquarePower(topLeft, squareSize);
    }

    private static long calcSquarePower(GridNode topLeft, int squareSize) {
        long sumPower = 0;
        GridNode actY = topLeft;
        for (int y = 0; y < squareSize; y++) {
            GridNode actX = actY;
            for (int x = 0; x < squareSize; x++) {
                sumPower += actX.powerLevel;
                actX = actX.nextX;
            }
            actY = actY.nextY;
        }
        return sumPower;
    }


    MaxPowerSquare findMaxPowerAnySquare() {
        Pos result = null;
        int maxSquareSize = 1;
        long maxPower = Long.MIN_VALUE;
        for (int squareSize = 1; squareSize <= gridSize; squareSize++) {
            if (squareSize % 10 == 0) System.out.println("SquareSize:" + squareSize);
            for (int y = 1; y <= gridSize - squareSize - 1; y++) {
                for (int x = 1; x <= gridSize - squareSize - 1; x++) {
                    Pos p = new Pos(x, y);

                    long sumPower = calcSquarePower(p, squareSize);

                    if (sumPower > maxPower) {
                        maxPower = sumPower;
                        result = p;
                        maxSquareSize = squareSize;
//                        System.out.println("So far MaxPower " + maxPower);
                    }
                }
            }
        }

//        System.out.println(maxPower);
        return new MaxPowerSquare(result, maxSquareSize);
    }

    public record MaxPowerSquare(Pos pos, int squareSize) {
    }

    private static class GridNode {
        final Pos pos;
        GridNode nextX = null;
        GridNode nextY = null;
        GridNode prevX = null;
        GridNode prevY = null;
        long powerLevel = 0;

        public GridNode(Pos pos) {
            this.pos = pos;
        }
    }
}
