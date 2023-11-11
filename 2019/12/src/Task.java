import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task {
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
        for (int step = 1; step <= 1000; step++) {
            moveMoons();
//            out("\r\n step", step);
//            outMoons();
        }
        outMoons();
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
        int sumEnergy = 0;
        for (Moon moon : moons) {
            out(moon);
            int energy = sumPos(moon.pos) * sumPos(moon.vel);
            sumEnergy += energy;
        }
        out("Sum total energy", sumEnergy);
    }

    private static int sumPos(Pos pos) {
        return Math.abs(pos.x) + Math.abs(pos.y) + Math.abs(pos.z);
    }

    public void out(Object... str) {
        Util.out(str);
    }


}
