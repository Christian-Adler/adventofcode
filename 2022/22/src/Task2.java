import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {

    Map<Pos, Tile> map = new HashMap<>();
    Tile start = null;


    int TILE = 1;
    int WALL = 2;

    int RIGHT = 0;
    int DOWN = 1;
    int LEFT = 2;
    int UP = 3;

    int maxX = 0;
    int maxY = 0;

    boolean parseMap = true;

    Map<DirPos, DirPos> mapFromTo = new HashMap<>();

    {
        boolean useDemo = false;
        if (useDemo) {
            // DEMO
            int seitenLaenge = 4;
            //   1
            // 234
            //   56

            // 1 > 2
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(i + 8, 1, UP);
                DirPos pTo = new DirPos(5 - i, 5, DOWN);
                mapFromTo.put(pFrom, pTo);
                // 2 > 1
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 1 > 3
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(9, i, LEFT);
                DirPos pTo = new DirPos(4 + i, 5, DOWN);
                mapFromTo.put(pFrom, pTo);
                // 3 > 1
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 1 > 6
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(12, i, RIGHT);
                DirPos pTo = new DirPos(16, 13 - i, LEFT);
                mapFromTo.put(pFrom, pTo);
                // 6 > 1
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 2 > 6
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(1, 4 + i, LEFT);
                DirPos pTo = new DirPos(17 - i, 12, UP);
                mapFromTo.put(pFrom, pTo);
                // 6 > 2
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 2 > 5
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(i + 0, 8, DOWN);
                DirPos pTo = new DirPos(13 - i, 12, UP);
                mapFromTo.put(pFrom, pTo);
                // 5 > 2
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 3 > 5
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(i + 0, 8, DOWN);
                DirPos pTo = new DirPos(9, 13 - i, RIGHT);
                mapFromTo.put(pFrom, pTo);
                // 5 > 3
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 4 > 6
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(12, 4 + i, RIGHT);
                DirPos pTo = new DirPos(17 - i, 9, DOWN);
                mapFromTo.put(pFrom, pTo);
                // 6 > 4
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }
        } else {
            int seitenLaenge = 50;
            //   12
            //   3
            //  45
            //  6

            // 1 > 4
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(51, i, LEFT);
                DirPos pTo = new DirPos(1, 151 - i, RIGHT);
                mapFromTo.put(pFrom, pTo);
                // 4 > 1
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 1 > 6
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(50 + i, 1, UP);
                DirPos pTo = new DirPos(1, 150 + i, RIGHT);
                mapFromTo.put(pFrom, pTo);
                // 6 > 1
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 2 > 3
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(100 + i, 50, DOWN);
                DirPos pTo = new DirPos(100, 50 + i, LEFT);
                mapFromTo.put(pFrom, pTo);
                // 3 > 2
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 2 > 5
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(150, i, RIGHT);
                DirPos pTo = new DirPos(100, 151 - i, LEFT);
                mapFromTo.put(pFrom, pTo);
                // 5 > 2
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 2 > 6
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(100 + i, 1, UP);
                DirPos pTo = new DirPos(i, 200, UP);
                mapFromTo.put(pFrom, pTo);
                // 6 > 2
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 3 > 4
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(51, 50 + i, LEFT);
                DirPos pTo = new DirPos(i, 101, DOWN);
                mapFromTo.put(pFrom, pTo);
                // 4 > 3
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }

            // 5 > 6
            for (int i = 1; i <= seitenLaenge; i++) {
                DirPos pFrom = new DirPos(50 + i, 150, DOWN);
                DirPos pTo = new DirPos(50, 150 + i, LEFT);
                mapFromTo.put(pFrom, pTo);
                // 6 > 5
                DirPos pFromReverse = pTo.switchDir();
                DirPos pToReverse = pFrom.switchDir();
                mapFromTo.put(pFromReverse, pToReverse);
            }
        }
    }

    ArrayList<Object> path = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        if (input.trim().isEmpty()) {
            parseMap = false;
            return;
        }

        if (parseMap) {
            maxY++;
            maxX = Math.max(maxX, input.length());
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                Pos pos = null;
                if (c == '.') {
                    pos = new Pos(i + 1, maxY);
                    map.put(pos, new Tile(pos));
                } else if (c == '#') {
                    pos = new Pos(i + 1, maxY);
                    map.put(pos, new Wall(pos));
                }

                if (start == null && pos != null) {
                    start = map.get(pos);
                    start.visited = true;
                }
            }
        } else {
            Pattern p = Pattern.compile("[A-Za-z]+|\\d+");
            Matcher m = p.matcher(input);

            while (m.find()) {
                String str = m.group();
                if (!str.equals("R") && !str.equals("L"))
                    path.add(Integer.parseInt(str));
                else
                    path.add(str);
            }
//            out(path);
        }
    }

    public void afterParse() {
        Pos actPos = start.pos;
        int direction = RIGHT;

        for (Object o : path) {
            if (o instanceof Integer) {
                int steps = (int) o;
                Pos addPos = null;

                // steps...
                for (int step = 0; step < steps; step++) {

                    if (direction == RIGHT)
                        addPos = new Pos(1, 0);
                    else if (direction == LEFT)
                        addPos = new Pos(-1, 0);
                    else if (direction == DOWN)
                        addPos = new Pos(0, 1);
                    else if (direction == UP)
                        addPos = new Pos(0, -1);

                    if (addPos == null) {
                        out("NEIN! " + direction);
                        return;
                    }

                    Pos check = actPos.add(addPos);
                    Tile tile = map.get(check);
                    if (tile == null) {
                        DirPos fromPos = new DirPos(actPos.x, actPos.y, direction);
                        DirPos toPos = mapFromTo.get(fromPos);
                        //  andere Seite
                        if (toPos == null) {
                            out("FEHLER - no toPos");
                            return;
                        }
                        tile = map.get(toPos.pos);
                        if (tile.isTile) {
                            actPos = toPos.pos;
                            direction = toPos.direction;
                            tile.visited = true;
                        } else if (tile.isWall)
                            break;
                    } else {
                        if (tile.isTile) {
                            actPos = check;
                            tile.visited = true;
                        } else if (tile.isWall)
                            break;
                    }
                }

//                out(toString());
            } else {
                if ("R".equals(o))
                    direction = (direction + 1) % 4;
                else if ("L".equals(o))
                    direction = ((direction - 1) + 4) % 4;
                else
                    out("FEHLER!");
            }
        }

        long password = 1000l * actPos.y + 4 * actPos.x + direction;
        out("password: " + password);
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

        for (int y = 1; y <= maxY; y++) {
            builder.append("\r\n");
            for (int x = 1; x <= maxX; x++) {
                Pos pos = new Pos(x, y);

                Tile tile = map.get(pos);
                if (tile == null)
                    builder.append(" ");
                else
                    builder.append(tile);
            }
        }

        return builder.toString();
    }

    public String toStringSVG() {
        StringBuilder builder = new StringBuilder();

        for (int y = 1; y <= maxY; y++) {
            builder.append("\r\n");
            for (int x = 1; x <= maxX; x++) {
                Pos pos = new Pos(x, y);

                Tile tile = map.get(pos);
                if (tile != null)
                    builder.append(tile.toStringSVG());
            }
        }

        return builder.toString();
    }
}
