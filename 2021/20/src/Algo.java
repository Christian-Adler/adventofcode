import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Algo {
    private final StringBuilder algoBuilder = new StringBuilder();
    private final Set<Integer> lightIndexs = new HashSet<>();
    private final Set<Pos> imageLights = new HashSet<>();
    private final Set<Pos> imageLightsEH1 = new HashSet<>();
    private final Set<Pos> imageLightsEH2 = new HashSet<>();
    private boolean addToAlgo = true;
    private int yImgLine = 0;

    public void add(String line) {
        if (line.isEmpty()) {
            addToAlgo = false;
            String[] split = algoBuilder.toString().split("");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                if (s.equals("#"))
                    lightIndexs.add(i);
            }

            System.out.println(lightIndexs);

            return;
        }

        if (addToAlgo)
            algoBuilder.append(line);
        else {
            String[] split = line.split("");
            for (int x = 0; x < split.length; x++) {
                String s = split[x];
                if (s.equals("#"))
                    imageLights.add(new Pos(x, yImgLine));
            }
            yImgLine++;
        }
    }

    public void enhance1() {
        enhance(imageLights, imageLightsEH1);
    }

    public void enhance2() {
        enhance(imageLightsEH1, imageLightsEH2);

        System.out.println("lit pixels : " + imageLightsEH2.size());
    }

    private void enhance(Set<Pos> source, Set<Pos> target) {
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = 0;
        int yMax = 0;

        for (Pos pos : source) {
            if (pos.x < xMin) xMin = pos.x;
            if (pos.x > xMax) xMax = pos.x;
            if (pos.y > yMin) yMin = pos.y;
            if (pos.y < yMax) yMax = pos.y;
        }

        // Rand
        xMin -= 1;
        xMax += 1;
        yMin += 1; // Koordinaten + nach unten!
        yMax -= 1;

        for (int y = yMax; y <= yMin; y++) {
            for (int x = xMin; x <= xMax; x++) {
                Pos checkPos = new Pos(x, y);
                boolean lights = enhance(checkPos, source);
                if (lights)
                    target.add(checkPos);
            }
        }

    }

    private boolean enhance(Pos pos, Set<Pos> source) {
        List<Pos> checkPos = new ArrayList<>();
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                checkPos.add(new Pos(pos.x + x, pos.y + y));
            }
        }

        StringBuilder builder = new StringBuilder();
        for (Pos checkP :
                checkPos) {
            builder.append(source.contains(checkP) ? "1" : "0");
        }

        String binary = builder.toString();

        Integer decimal = Integer.parseInt(binary, 2);
//        if (decimal > 0)
//            System.out.println(binary);
        boolean isLight = lightIndexs.contains(decimal);
        return isLight;
    }

    public void printImage() {
        printImage(imageLights);
    }

    public void printImageEH1() {
        printImage(imageLightsEH1);
    }

    public void printImageEH2() {
        printImage(imageLightsEH2);
    }

    private void printImage(Set<Pos> image) {
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = 0;
        int yMax = 0;

        for (Pos pos : image) {
            if (pos.x < xMin) xMin = pos.x;
            if (pos.x > xMax) xMax = pos.x;
            if (pos.y > yMin) yMin = pos.y;
            if (pos.y < yMax) yMax = pos.y;
        }

        // Rand
        xMin -= 4;
        xMax += 4;
        yMin += 4; // Koordinaten + nach unten!
        yMax -= 4;

        StringBuilder builder = new StringBuilder();

        for (int y = yMax; y <= yMin; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (image.contains(new Pos(x, y)))
                    builder.append("#");
                else
                    builder.append(".");
            }
            builder.append("\r\n");
        }

        System.out.println(builder);
    }
}
