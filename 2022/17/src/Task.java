import java.util.*;

public class Task {
    String gasJet = null;
    int gasJetIdx = -1;
    final int LEFT = 1;
    final int RIGHT = 2;


    List<Rock> rocks = new ArrayList<>();
    int rocksIdx = -1;

    Map<Pos, Boolean> map = new HashMap<>();

    long towerHeight = 0;
    Rock fallingRock = null;
    Pos fallingRockPos = null;

    public void init() {
        rocks.add(new Rock(new Pos(0, 0), new Pos(1, 0), new Pos(2, 0), new Pos(3, 0)));
        rocks.add(new Rock(new Pos(1, 0), new Pos(0, 1), new Pos(1, 1), new Pos(2, 1), new Pos(1, 2)));
        rocks.add(new Rock(new Pos(0, 0), new Pos(1, 0), new Pos(2, 0), new Pos(2, 1), new Pos(2, 2)));
        rocks.add(new Rock(new Pos(0, 0), new Pos(0, 1), new Pos(0, 2), new Pos(0, 3)));
        rocks.add(new Rock(new Pos(0, 0), new Pos(1, 0), new Pos(0, 1), new Pos(1, 1)));
    }

    public void addLine(String input) {
        gasJet = input;
        System.out.println(gasJet.length());
        System.exit(1);
    }

    int getNextJet() {
        gasJetIdx++;
        if (gasJetIdx >= gasJet.length())
            gasJetIdx = 0;
        char j = gasJet.charAt(gasJetIdx);
        if (j == '<')
            return LEFT;
        else
            return RIGHT;
    }

    Rock getNextRock() {
        rocksIdx++;
        if (rocksIdx >= rocks.size())
            rocksIdx = 0;
        return rocks.get(rocksIdx);
    }

    public void afterParse() {
//        long maxRockCount = 2022;
        long maxRockCount = 1000000000000l;
        long t1 = System.currentTimeMillis();
        Queue<Pos> last100 = new LinkedList<>();
        int rockCount = 0;
        while (rockCount < maxRockCount) {
            rockCount++;
            if (rockCount % 1000000 == 0) {
                long t2 = System.currentTimeMillis();
                out(rockCount + " : " + ((double) rockCount / maxRockCount * 100) + "% " + (t2 - t1) + "ms");
            }
            boolean falling = true;
            fallingRockPos = new Pos(2, towerHeight + 3);
            fallingRock = getNextRock();

//            out(toString());

            while (falling) {

                // Jet links | rechts
                int jet = getNextJet();
                // Gegen Wand pruefen
                Pos checkPos = null;
                if (jet == LEFT) {
                    if (fallingRockPos.x > 0)
                        checkPos = new Pos(fallingRockPos.x - 1, fallingRockPos.y);
                } else {
                    if (fallingRockPos.x + fallingRock.width < 7)
                        checkPos = new Pos(fallingRockPos.x + 1, fallingRockPos.y);
                }

                // Gegen Map pruefen (falls nicht gegen Wand gestoessen
                if (checkPos != null && !hitTestMap(checkPos))
                    fallingRockPos = checkPos;

//                out(toString());

                // 1 nach unten fallen
                if (hitTestMapFalling()) {
                    falling = false;

                    for (Pos position : fallingRock.positions) {
                        Pos pos = new Pos(fallingRockPos.x + position.x, fallingRockPos.y + position.y);
                        map.put(pos, true);
                        last100.add(pos);
                        towerHeight = Math.max(towerHeight, pos.y + 1);
//                        out("towerheight: " + towerHeight);
                    }
//                    out(toString());
                    fallingRock = null;
                } else
                    fallingRockPos.y = fallingRockPos.y - 1;

                while (last100.size() > 50) {
                    Pos p = last100.poll();
                    map.remove(p);
                }

//                out(toString());
            }
        }
    }

    boolean hitTestMapFalling() {
        Pos posCheck = new Pos(fallingRockPos.x, fallingRockPos.y - 1);

        if (posCheck.y >= towerHeight)
            return false;

        return hitTestMap(posCheck);
    }

    boolean hitTestMap(Pos posCheck) {
        if (posCheck.y < 0) // Ground
            return true;
        for (Pos position : fallingRock.positions) {
            Pos check = new Pos(posCheck.x + position.x, posCheck.y + position.y);
            if (map.get(check) != null)
                return true;
        }
        return false;
    }

    boolean hitTestFallingRock(Pos p) {
        if (fallingRock == null) return false;

        for (Pos position : fallingRock.positions) {
            Pos check = new Pos(fallingRockPos.x + position.x, fallingRockPos.y + position.y);
            if (check.equals(p))
                return true;
        }
        return false;
    }

    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            input = input.replace(string, "");
        }
        return input;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        long yMax = (towerHeight + 6);
//        builder.append("\r\nyMax : " + yMax);
//        for (int y = yMax; y >= 0; y--) {
//            builder.append("\r\n");
//
//            for (int x = -1; x <= 7; x++) {
//                if (x < 0 || x > 6) builder.append("|");
//                else {
//                    Pos p = new Pos(x, y);
//
//                    // FallingRock?
//                    if (hitTestFallingRock(p))
//                        builder.append("@");
//                    else
//                        builder.append(map.get(p) != null ? "#" : ".");
//                }
//            }
//
//        }
//        builder.append("\r\n+-------+");
        builder.append("\r\ntowerheight:").append(towerHeight);

        return builder.toString();
    }

    public String toStringSVG() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
