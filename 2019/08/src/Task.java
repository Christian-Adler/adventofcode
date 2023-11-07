import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Task {

    int width = -1;
    int height = -1;
    int[] imgData = new int[0];

    public void init() {
    }

    public void addLine(String input) {
        if (width < 0) {
            int[] split = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
            width = split[0];
            height = split[1];
        } else {
            imgData = Arrays.stream(input.split("")).mapToInt(Integer::parseInt).toArray();
        }
    }

    public void afterParse() throws IOException {
        int layerLength = width * height;
        int numLayers = imgData.length / layerLength;
        ArrayList<int[]> layers = new ArrayList<>();
        for (int i = 0; i < numLayers; i++) {
            int[] layer = Arrays.copyOfRange(imgData, i * layerLength, (i + 1) * layerLength);
            layers.add(layer);
        }

        long minZeros = Long.MAX_VALUE;
        int[] minZerosLayer = null;
        for (int[] layer : layers) {
            long numZeros = Arrays.stream(layer).filter(i -> i == 0).count();
            if (numZeros < minZeros) {
                minZeros = numZeros;
                minZerosLayer = layer;
            }
        }

        if (minZerosLayer == null) {
            out("Found no min 0 layer");
            return;
        }

        long num1 = Arrays.stream(minZerosLayer).filter(i -> i == 1).count();
        long num2 = Arrays.stream(minZerosLayer).filter(i -> i == 2).count();
        out("Part 1", "min Zero layer: num1*num2", num1 * num2);

        // Part 2

        int[] mergedImg = new int[layerLength];
        for (int i = 0; i < layerLength; i++) {
            for (int[] layer : layers) {
                int pixelValue = layer[i];
                if (pixelValue < 2) {
                    mergedImg[i] = pixelValue;
                    break;
                }
            }
        }

        SVG svg = new SVG();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int idx = x + y * width;
                int pixelValue = mergedImg[idx];
                if (pixelValue == 1)
                    svg.add(new Pos(x, y));
            }
        }
        out(svg.toConsoleString());
        Util.writeToAOCSvg(svg.toSVGStringAged());

        /*
#..#..##..###...##..####
#..#.#..#.#..#.#..#.#...
####.#....###..#....###.
#..#.#.##.#..#.#....#...
#..#.#..#.#..#.#..#.#...
#..#..###.###...##..#...
         */
    }


    public void out(Object... str) {
        Util.out(str);
    }


}
