import java.util.LinkedList;
import java.util.List;

public class Area {
    protected int tXMin = -1;
    protected int tXMax = -1;
    protected int tYMin = -1;
    protected int tYMax = -1;

    protected int maxYPosOfAllShots = 0;
    protected int maxVYAllShots = 0;
    protected List<Pos> maxShot = null;

    public void add(String line) {
        String[] split = line.replace("target area: x=", "").replace(" y=", "").replace("..", "t").split(",");
        if (split.length != 2) return;
        String[] splitX = split[0].split("t");
        String[] splitY = split[1].split("t");
        tXMin = Integer.parseInt(splitX[0]);
        tXMax = Integer.parseInt(splitX[1]);
        tYMin = Integer.parseInt(splitY[0]); // low
        tYMax = Integer.parseInt(splitY[1]); // high
    }

    public int shoot() {
        // xV ermitteln
        int xVMin = -1;
        int xVMax;
        int xVMinSteps = -1;
        int xVMaxSteps;
        int xV = 1;
        int reachableWidth = 0;
        int steps = 0;
        while (true) {
            if (reachableWidth >= tXMin && xVMin < 0) {
                xVMin = xV;
                xVMinSteps = steps;
            }
            if (reachableWidth > tXMax) {
                xVMax = xV - 1;
                xVMaxSteps = steps - 1;
                break;
            }
            steps++;
            xV++;
            reachableWidth += xV;
        }

        System.out.println("xVMin:" + xVMin);
        System.out.println("xVMinSteps:" + xVMinSteps);
        System.out.println("xVMax:" + xVMax);
        System.out.println("xVMaxSteps:" + xVMaxSteps);

        maxYPosOfAllShots = 0;
        maxVYAllShots = 0;
        maxShot = null;

        int vYStart = 1;
        while (true) {
            // TEST
//            if (maxShot != null)
//                break;

            System.out.println("vYStart: " + vYStart);
            if (vYStart > Math.abs(tYMin)) // nicht mehr moeglich innerhalb 1 Schritts nach unten (<0) die Targetarea zu treffen
                break;

            List<Pos> shot = new LinkedList<>();

            int vY = vYStart;
            int vX = xVMin;
            int yPos = 0;
            int xPos = 0;
            int step = 0;

            int maxYPos = 0;

            while (yPos >= tYMin) {
                step++;
                yPos += vY;
                xPos += vX;

                shot.add(new Pos(xPos, yPos, step));

                if (yPos > maxYPos)
                    maxYPos = yPos;
                // Pruefen ob in TargetArea
                if (step >= xVMinSteps) {
                    if (hitTargetAreaY(yPos)) {
                        System.out.println("Found shot: vX:" + xVMin + " vY:" + vYStart + " - " + shot);
                        if (maxYPos > maxYPosOfAllShots) {
                            maxYPosOfAllShots = maxYPos;
                            maxShot = shot;
                            maxVYAllShots = vYStart;
                        }
                        break;
                    }
                }

                vY--;
                if (vX > 0)
                    vX--;
            }


            vYStart++;
        }

        System.out.println("maxYPosOfAllShots: " + maxYPosOfAllShots);

        return maxVYAllShots;
    }

    protected boolean hitTargetArea(int x, int y) {
        return (x >= tXMin && x <= tXMax && y >= tYMin && y <= tYMax);
    }

    protected boolean hitTargetAreaY(int y) {
        return (y >= tYMin && y <= tYMax);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int y = maxYPosOfAllShots; y > tYMin - 1; y--) {
            for (int x = 0; x < tXMax + 1; x++) {
                if (x == 0 && y == 0)
                    builder.append("S");
                else if (maxShot != null && maxShot.contains(new Pos(x, y, 0)))
                    builder.append("#");
                else if (hitTargetArea(x, y))
                    builder.append("T");
                else
                    builder.append(".");
            }
            builder.append("\r\n");
        }

        return builder.toString();
//        return "Area{" +
//                "x1=" + tXMin +
//                ", x2=" + tXMax +
//                ", y1=" + tYMin +
//                ", y2=" + tYMax +
//                '}';
    }
}
