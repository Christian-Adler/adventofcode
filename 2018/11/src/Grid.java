import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    final int serial;
    final Map<Pos, Long> map = new HashMap<>();
    final List<Pos> square3x3 = Arrays.asList(new Pos(0, 0), new Pos(1, 0), new Pos(2, 0),
            new Pos(0, 1), new Pos(1, 1), new Pos(2, 1),
            new Pos(0, 2), new Pos(1, 2), new Pos(2, 2));
    private final int gridSize = 300;

    public Grid(int serial) {
        this.serial = serial;
    }

    long calcPowerLevel(Pos p) {
        long rackId = p.x + 10;
        long powerLevel = (rackId * p.y + serial) * rackId;
        long hundredsDigit = (powerLevel / 100) % 10;
        long pwl = hundredsDigit - 5;
        return pwl;
    }

    void calcGrid() {
        for (int y = 1; y <= gridSize; y++) {
            for (int x = 1; x <= gridSize; x++) {
                Pos p = new Pos(x, y);
                map.put(p, calcPowerLevel(p));
            }
        }
    }

    Pos findMaxPower3x3Square() {
        Pos result = null;
        long maxPower = Long.MIN_VALUE;
        for (int y = 1; y <= gridSize - 2; y++) {
            for (int x = 1; x <= 300 - 2; x++) {
                long sumPower = 0;
                Pos p = new Pos(x, y);
                for (Pos pos : square3x3) {
                    sumPower +=
                            map.get(p.addToNew(pos));
                }
                if (sumPower > maxPower) {
                    maxPower = sumPower;
                    result = p;
                }
            }
        }
//        System.out.println(maxPower);
        return result;
    }

    MaxPowerSquare findMaxPowerAnySquare() {
        Pos result = null;
        int maxSquareSize = 1;
        long maxPower = Long.MIN_VALUE;
        for (int squareSize = 1; squareSize <= gridSize; squareSize++) {
            System.out.println("SquareSize:" + squareSize);
            for (int y = 1; y <= gridSize - squareSize - 1; y++) {
                for (int x = 1; x <= gridSize - squareSize - 1; x++) {
                    long sumPower = 0;
                    Pos p = new Pos(x, y);

                    for (int yS = 0; yS < squareSize; yS++) {
                        for (int xS = 0; xS < squareSize; xS++) {
                            Pos pos = new Pos(xS, yS);
                            sumPower += map.get(p.addToNew(pos));
                        }
                    }

                    if (sumPower > maxPower) {
                        maxPower = sumPower;
                        result = p;
                    }
                }
            }
        }

        // TODO Grid aus verlinkten Nodes - so dass per Pointer navigiert werden kann

//        System.out.println(maxPower);
        return new MaxPowerSquare(result, maxSquareSize);
    }

    public record MaxPowerSquare(Pos pos, int squareSize) {
    }
}
