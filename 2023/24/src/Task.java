import java.util.ArrayList;
import java.util.List;

public class Task {

  private final List<Hailstone> hailstones = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    hailstones.add(Hailstone.from(input));
  }

  public void afterParse(Range xRange, Range yRange) {
//    out(hailstones);

    long countInside = 0;
    for (int i = 0; i < hailstones.size() - 1; i++) {
      for (int j = i + 1; j < hailstones.size(); j++) {
        Hailstone hailA = hailstones.get(i);
        Hailstone hailB = hailstones.get(j);
        if (checkCrossInside(hailA, hailB, xRange, yRange))
          countInside++;
      }
    }

    out();
    out("Part 1", "count inside test area:", countInside);

    // Part 2
    /*
    For all hailstones:
    Sx + tH * SVx = Hx + tH * HVx  <=>  Sx = Hx + (HVx - SVx) * tH
    Subtract stones velocity from hailstones velocity and for all hailstones the Coordinate of the stone is the same.

    Iterate over int velocities vx, vy - if there is an intersection between to hailstones, check against a third hailstone.
    If it also intersects at the same point do the same with vx and vz to get the third coordinate.
     */
    out();

    Hailstone hA = hailstones.get(0);
    Hailstone hB = hailstones.get(1);
    Hailstone hC = hailstones.get(3); // with idx 2 I had a rounding issue :(

    int testRange = 1000;

    boolean foundIt = false;

    int sVx = 0;
    for (int i = 0; i < testRange; i++) {
      if (foundIt) break;
//      if (sVx % 100 == 0) out("sVx", sVx);
      int sVy = 0;
      for (int j = 0; j < testRange; j++) {
        if (foundIt) break;
//        out();
        IntersectionPoint intersectionPoint = checkCrossAtPointWithVStoneXY(hA, hB, sVx, sVy);

        if (intersectionPoint != null) {
//          out(intersectionPoint);
          IntersectionPoint intersectionPoint2 = checkCrossAtPointWithVStoneXY(hA, hC, sVx, sVy);
          if (intersectionPoint.equals(intersectionPoint2)) {
//            out("FOUND  for x,y", intersectionPoint);

            // check against z
            int sVz = 0;
            for (int k = 0; k < testRange; k++) {
              if (foundIt) break;
              IntersectionPoint intersectionPoint3 = checkCrossAtPointWithVStoneXZ(hA, hB, sVx, sVz);
              if (intersectionPoint3 != null) {
                IntersectionPoint intersectionPoint4 = checkCrossAtPointWithVStoneXZ(hA, hC, sVx, sVz);
                if (intersectionPoint3.equals(intersectionPoint4)) {
                  foundIt = true;
//                  out("FOUND IT fox x,z", intersectionPoint3);
                  out("Found it: x,y,z, vx,vy,vz:", intersectionPoint.p1, intersectionPoint.p2, intersectionPoint3.p2, sVx, sVy, sVz);
                  out("Part 2:", intersectionPoint.p1 + intersectionPoint.p2 + intersectionPoint3.p2);
                }
              }
              sVz = getNextV(sVz);
            }
          }
        }

        sVy = getNextV(sVy);
      }
      sVx = getNextV(sVx);
    }
  }


  private boolean checkCrossInside(Hailstone hailA, Hailstone hailB, Range xRange, Range yRange) {
//    out();
//    out("A", hailA);
//    out("B", hailB);

    // f(x)= y=mx+b
    double mA = (double) hailA.velocity().y / hailA.velocity().x;
    double mB = (double) hailB.velocity().y / hailB.velocity().x;

//    out("mA mB", mA, mB);

    if (mA == mB) {
//      out("found parallel m");
      return false;
    }

    // y-Achsenabschnitt b berechnen
    // b=y-mx
    double bA = hailA.pos().y - mA * hailA.pos().x;
    double bB = hailB.pos().y - mB * hailB.pos().x;

//    out("bA bB", bA, bB);

    // y gleich setzen
    // mA*x+bA = mB*x+bB <=> mA*x - mB*x = bB - bA <=> (mA-mB)*x = bB-bA <=> x= (bB-bA)/(mA-mB)
    double sameX = (bB - bA) / (mA - mB); // mA-mB nie 0, sonst waren sie schon parallel
    double sameY = mA * sameX + bA;

//    out("meet at x y", sameX, sameY);

    // Zeit betrachten von x bis zu sameX
    // xStart+t*xVel = sameX <=> t = (sameX - xStart)/xVel
    double tA = (sameX - hailA.pos().x) / hailA.velocity().x;
    double tB = (sameX - hailB.pos().x) / hailB.velocity().x;
//    out("tA tB", tA, tB);
    if (tA < 0 || tB < 0) {
//      out("path crossed in the past...");
      return false;
    }

    // Check Range
    //      out("cross outside test area");
    return xRange.inRange(sameX) && yRange.inRange(sameY);
  }

  private IntersectionPoint checkCrossAtPointWithVStoneXY(Hailstone hailA, Hailstone hailB, int sVx, int sVy) {
//    out();
//    out("A", hailA);
//    out("B", hailB);

    // f(x)= y=mx+b
    double mA = (double) (hailA.velocity().y - sVy) / (hailA.velocity().x - sVx);
    double mB = (double) (hailB.velocity().y - sVy) / (hailB.velocity().x - sVx);

//    out("mA mB", mA, mB);

    if (mA == mB) {
//      out("found parallel m");
      return null;
    }

    // y-Achsenabschnitt b berechnen
    // b=y-mx
    double bA = hailA.pos().y - mA * hailA.pos().x;
    double bB = hailB.pos().y - mB * hailB.pos().x;

//    out("bA bB", bA, bB);

    // y gleich setzen
    // mA*x+bA = mB*x+bB <=> mA*x - mB*x = bB - bA <=> (mA-mB)*x = bB-bA <=> x= (bB-bA)/(mA-mB)
    double sameX = (bB - bA) / (mA - mB); // mA-mB nie 0, sonst waren sie schon parallel
    double sameY = mA * sameX + bA;

//    out("meet at x y", sameX, sameY);

    // Zeit betrachten von x bis zu sameX
    // xStart+t*xVel = sameX <=> t = (sameX - xStart)/xVel
    double tA = (sameX - hailA.pos().x) / (hailA.velocity().x - sVx);
    double tB = (sameX - hailB.pos().x) / (hailB.velocity().x - sVx);
//    out("tA tB", tA, tB);
    if (tA < 0 || tB < 0) {
//      out("path crossed in the past...");
      return null;
    }

    if (isLong(sameX) && isLong(sameY))
      return new IntersectionPoint(Math.round(sameX), Math.round(sameY));

    return null;
  }

  private IntersectionPoint checkCrossAtPointWithVStoneXZ(Hailstone hailA, Hailstone hailB, int sVx, int sVz) {
//    out();
//    out("A", hailA);
//    out("B", hailB);

    // f(x)= y=mx+b
    double mA = (double) (hailA.velocity().z - sVz) / (hailA.velocity().x - sVx);
    double mB = (double) (hailB.velocity().z - sVz) / (hailB.velocity().x - sVx);

//    out("mA mB", mA, mB);

    if (mA == mB) {
//      out("found parallel m");
      return null;
    }

    // y-Achsenabschnitt b berechnen
    // b=y-mx
    double bA = hailA.pos().z - mA * hailA.pos().x;
    double bB = hailB.pos().z - mB * hailB.pos().x;

//    out("bA bB", bA, bB);

    // y gleich setzen
    // mA*x+bA = mB*x+bB <=> mA*x - mB*x = bB - bA <=> (mA-mB)*x = bB-bA <=> x= (bB-bA)/(mA-mB)
    double sameX = (bB - bA) / (mA - mB); // mA-mB nie 0, sonst waren sie schon parallel
    double sameZ = mA * sameX + bA;

//    out("meet at x y", sameX, sameZ);

    // Zeit betrachten von x bis zu sameX
    // xStart+t*xVel = sameX <=> t = (sameX - xStart)/xVel
    double tA = (sameX - hailA.pos().x) / (hailA.velocity().x - sVx);
    double tB = (sameX - hailB.pos().x) / (hailB.velocity().x - sVx);
//    out("tA tB", tA, tB);
    if (tA < 0 || tB < 0) {
//      out("path crossed in the past...");
      return null;
    }

    if (isLong(sameX) && isLong(sameZ))
      return new IntersectionPoint(Math.round(sameX), Math.round(sameZ));

    return null;
  }

  private int getNextV(int sV) {
    if (sV == 0) sV++;
    else if (sV < 0) sV = sV * -1 + 1;
    else sV *= -1;
//    out(sV);
    return sV;
  }

  private boolean isLong(double value) {
    // if (value > 0) return true;
    if (Double.isInfinite(value)) return false;

    long floor = Math.round(value);
    double abs = Math.abs(value - floor);
    return abs <= 1;
  }

  private record IntersectionPoint(long p1, long p2) {
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
