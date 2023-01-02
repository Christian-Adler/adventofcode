import java.util.LinkedList;
import java.util.List;

public class Area2 extends Area {
    private int shotCount = 0;

    public int shoot(int maxVY) {
        shotCount = 0;
        // xV ermitteln
        int xVMin = -1;
        int xVMax = tXMax;
        int xV = 1;
        int reachableWidth = 0;
        while (true) {
            if (reachableWidth >= tXMin) {
                xVMin = xV;
                break;
            }
            xV++;
            reachableWidth += xV;
        }

        System.out.println("xVMin:" + xVMin);
        System.out.println("xVMax:" + xVMax);

        int yVMin = tYMin;

        for (int vXIt = xVMin; vXIt <= xVMax; vXIt++) {

//            System.out.println("vXStart: " + vX);

            for (int vYIt = maxVY; vYIt >= yVMin; vYIt--) {
                int vX = vXIt;
                int vY = vYIt;

//                System.out.println("vYStart: " + vYStart);
                System.out.println("v: " + vX + "," + vY);


                List<Pos> shot = new LinkedList<>();


                int yPos = 0;
                int xPos = 0;
                int step = 0;


                while (yPos >= tYMin) {
                    step++;
                    yPos += vY;
                    xPos += vX;

                    Pos pos = new Pos(xPos, yPos, step);
                    shot.add(pos);

                    // Pruefen ob in TargetArea
                    if (hitTargetArea(xPos, yPos)) {
                        shotCount++;
//                            System.out.println("Found shot: vX:" + vX + " vY:" + vY + " - " + shot);
                        System.out.println("Found shot: vX:" + vX + " vY:" + vY);

                        break;
                    }

                    vY--;
                    if (vX > 0)
                        vX--;
                }


            }
        }

        System.out.println("shotCount: " + shotCount);
        return xVMin;
    }
}
