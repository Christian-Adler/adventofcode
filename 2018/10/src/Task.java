import java.util.ArrayList;
import java.util.List;

public class Task {
    List<Light> lights = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] splitV = Util.cleanFrom(input, "position=<", ">", "elocity=<").trim().split("v");
        Pos pos = parsePos(splitV[0]);
        Pos velocity = parsePos(splitV[1]);
        lights.add(new Light(pos, velocity));
    }

    private static Pos parsePos(String input) {
        String[] split = input.split(",");
        int x = Integer.parseInt(split[0].trim());
        int y = Integer.parseInt(split[1].trim());
        return new Pos(x, y);
    }

    public void afterParse() {
        int minHeight = Integer.MAX_VALUE;
        int minWidth = Integer.MAX_VALUE;
        long count = 0;
        while (true) {
            int xMin = Integer.MAX_VALUE;
            int yMin = Integer.MAX_VALUE;
            int xMax = Integer.MIN_VALUE;
            int yMax = Integer.MIN_VALUE;

            for (Light light : lights) {
                xMin = Math.min(xMin, light.pos.x);
                yMin = Math.min(yMin, light.pos.y);
                xMax = Math.max(xMax, light.pos.x);
                yMax = Math.max(yMax, light.pos.y);
            }

            int height = yMax - yMin;
            int width = xMax - xMin;

            if (height > minHeight && width > minWidth) {
                lights.forEach(Light::stepBack);
                count--;
                break;
            }

            minHeight = height;
            minWidth = width;
            count++;

            lights.forEach(Light::step);
        }
//        for (int i = 0; i < 3; i++) {
//            lights.forEach(Light::step);
//        }

        out("seconds", count);
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

        for (Light light : lights) {
            svg.add(light.pos);
        }

        return svg.toSVGStringAged();
    }

    public String toStringConsole() {
        SVG svg = new SVG();

        for (Light light : lights) {
            svg.add(light.pos);
        }

        return svg.toConsoleString();
    }

    private static class Light {
        Pos pos;
        Pos velocity;

        public Light(Pos pos, Pos velocity) {
            this.pos = pos;
            this.velocity = velocity;
        }

        public void step() {
            pos.add(velocity);
        }

        public void stepBack() {
            pos.add(velocity.multToNew(-1));
        }
    }
}
