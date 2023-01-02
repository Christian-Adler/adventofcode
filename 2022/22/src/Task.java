import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {

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

                // steps...
                for (int step = 0; step < steps; step++) {
                    Pos check = actPos.add(addPos);
                    Tile tile = map.get(check);
                    if (tile == null) {
                        //  andere Seite
                        Pos checkBackwards = check.sub(addPos);
                        Tile checkTile = map.get(checkBackwards);
                        Tile prevTile = checkTile;
                        while (checkTile != null) {
                            actPos = checkBackwards;
                            checkBackwards = checkBackwards.sub(addPos);
                            checkTile = map.get(checkBackwards);
                            if (checkTile != null)
                                prevTile = checkTile;
                        }
                        prevTile.visited = true;
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
