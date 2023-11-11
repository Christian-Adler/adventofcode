import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task2 {
    List<Moon> moons = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        int[] ints = Arrays.stream(Util.cleanFrom(input, "<", ">", " ", "x=", "y=", "z=").trim().split(",")).mapToInt(Integer::parseInt).toArray();
        Pos p = new Pos(ints[0], ints[1], ints[2]);
        moons.add(new Moon(p));
    }

    public void afterParse() {
        outMoons();

        // Betrachte jede Achse einzeln. Die Monde pendeln auf jeder Achse.
        // Wann ist pro Achse das naechste Mal die Beschleunigung 0?
        // Dann hat man die Schrittanzahl fuer einen halben Pendel. -> *2
        // Alle Beschleunigungen wieder gleichzeitig auf 0 beim KGV aller Achsen.

        long allXVelZeroAfterSteps = -1;
        long allYVelZeroAfterSteps = -1;
        long allZVelZeroAfterSteps = -1;

        long stepCount = 0;
        while (allXVelZeroAfterSteps < 0 || allYVelZeroAfterSteps < 0 || allZVelZeroAfterSteps < 0) {
            stepCount++;
            moveMoons();

            boolean xZero = true;
            boolean yZero = true;
            boolean zZero = true;
            for (Moon moon : moons) {
                xZero &= moon.vel.x == 0;
                yZero &= moon.vel.y == 0;
                zZero &= moon.vel.z == 0;
            }

            if (xZero && allXVelZeroAfterSteps < 0)
                allXVelZeroAfterSteps = stepCount;
            if (yZero && allYVelZeroAfterSteps < 0)
                allYVelZeroAfterSteps = stepCount;
            if (zZero && allZVelZeroAfterSteps < 0)
                allZVelZeroAfterSteps = stepCount;
        }

        out(allXVelZeroAfterSteps, allYVelZeroAfterSteps, allZVelZeroAfterSteps);

        long lcm = leastCommonMultiple(leastCommonMultiple(allXVelZeroAfterSteps * 2, allYVelZeroAfterSteps * 2), allZVelZeroAfterSteps * 2);
        out("Par 2", "leastCommonMultiple", lcm);
//        outMoons();
    }

    // Calculates the least common multiple / kleinstes gemeinsames Vielfaches
    public static long leastCommonMultiple(long x, long y) {
        long result = 0;
        for (int i = 1; i <= y; i++) {
            if (i * x % y == 0) {
                result = (i * x);
                break;
            }
        }
        return result;
    }

    private void moveMoons() {
        for (int i = 0; i < moons.size() - 1; i++) {
            for (int j = i + 1; j < moons.size(); j++) {
                Moon moon1 = moons.get(i);
                Moon moon2 = moons.get(j);

                int compareValue = Integer.compare(moon1.pos.x, moon2.pos.x);
                moon1.vel.x += compareValue * -1;
                moon2.vel.x += compareValue;
                compareValue = Integer.compare(moon1.pos.y, moon2.pos.y);
                moon1.vel.y += compareValue * -1;
                moon2.vel.y += compareValue;
                compareValue = Integer.compare(moon1.pos.z, moon2.pos.z);
                moon1.vel.z += compareValue * -1;
                moon2.vel.z += compareValue;
            }
        }

        for (Moon moon : moons) {
            moon.pos.add(moon.vel);
        }
    }

    void outMoons() {
        for (Moon moon : moons) {
            out(moon);
            int energy = sumPos(moon.pos) * sumPos(moon.vel);
        }
    }

    private static int sumPos(Pos pos) {
        return Math.abs(pos.x) + Math.abs(pos.y) + Math.abs(pos.z);
    }

    public void out(Object... str) {
        Util.out(str);
    }


}
