import java.util.*;

public class Task {
    Map<Pos, String> trackMap = new HashMap<>();
    List<Cart> carts = new ArrayList<>();
    Set<Pos> crash = new HashSet<>();

    private int yMax = -1;
    private int xMax = -1;

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split("");
        xMax = Math.max(xMax, split.length);
        yMax++;

        for (int x = 0; x < split.length; x++) {
            Pos p = new Pos(x, yMax);
            String s = split[x];
            if (s.trim().isEmpty()) continue;
            if (s.equals(">")) {
                carts.add(new Cart(p.copy(), new Pos(1, 0)));
                p.color = "-";
                trackMap.put(p, "-");
            } else if (s.equals("<")) {
                carts.add(new Cart(p.copy(), new Pos(-1, 0)));
                p.color = "-";
                trackMap.put(p, "-");
            } else if (s.equals("^")) {
                carts.add(new Cart(p.copy(), new Pos(0, -1)));
                p.color = "|";
                trackMap.put(p, "|");
            } else if (s.equals("v")) {
                carts.add(new Cart(p.copy(), new Pos(0, 1)));
                p.color = "|";
                trackMap.put(p, "|");
            } else {
                p.color = s;
                trackMap.put(p, s);
            }
        }
    }


    public void afterParse() {
//        out(toStringConsole());
        out("Carts", carts.size(), carts);

        long stepCounter = 0;

        boolean part1 = true;

        while (carts.size() > 1) {
            stepCounter++;

            carts.sort(Comparator.comparing(cart -> ((Cart) cart).pos.y).thenComparing(cart -> ((Cart) cart).pos.x));

            Set<Cart> tmpCarts = new HashSet<>();

            crash.clear();
            for (Cart cart : carts) {
                if (tmpCarts.contains(cart)) continue;
                cart.tick(trackMap);

                Set<Pos> crashTest = new HashSet<>();
                for (Cart c : carts) {
                    if (!crashTest.add(c.pos)) {
                        crash.add(c.pos);
                    }
                }

                for (Cart c : carts) {
                    if (tmpCarts.contains(c)) continue;
                    if (crash.contains(c.pos)) {
                        tmpCarts.add(c);
                        out("Crash at ", c, "stepCounter", stepCounter);

                        if (part1) {
                            part1 = false;
                            out("Part 1", c.pos);
                        }
                    }
                }
            }

            carts.removeAll(tmpCarts);

//            if (!crash.isEmpty())
//                out("after clear ", carts.size(), carts);
//            out(toStringConsole());
        }
//        out(toStringConsole());
        out("Part 2", carts);
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {
        SVG svg = new SVG();
        return svg.toSVGStringAged();
    }


    public String toStringConsole() {
        SVG svg = new SVG();

        for (Pos pos : trackMap.keySet()) {
            svg.add(pos);
        }

        for (Cart cart : carts) {
            String dir = "";
            if (cart.direction.x == 0) {
                if (cart.direction.y < 0)
                    dir = "^";
                else
                    dir = "v";
            } else {
                if (cart.direction.x < 0)
                    dir = "<";
                else
                    dir = ">";
            }
            svg.add(cart.pos, dir);
        }

        for (Pos pos : crash) {
            svg.add(pos, "X");
        }

        return svg.toConsoleString();
    }
}
