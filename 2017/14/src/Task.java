import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task {
    Set<Pos> usedGrid = new HashSet<>();

    public void init() {
    }

    public void addLine(String input) {
        long allUsed = 0;
        for (int i = 0; i < 128; i++) {
            KnotHash hash = new KnotHash(input + "-" + i);
            String hex = hash.calcHexHash();
            String bin = hexToBin(hex);
            long used = bin.chars().filter(ch -> ch == '1').count();
            allUsed += used;
//            out(bin);
            for (int j = 0; j < bin.length(); j++) {
                if (bin.charAt(j) == '1')
                    usedGrid.add(new Pos(j, i));
            }
        }

        out("allUsed", allUsed);
    }

    public void afterParse() {
        // Regions finden
        int regions = 0;

        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 128; x++) {
                Pos p = new Pos(x, y);
                if (usedGrid.contains(p)) {
                    regions++;
                    List<Pos> worklist = new ArrayList<>();
                    worklist.add(p);
                    while (!worklist.isEmpty()) {
                        Pos actPos = worklist.remove(0);
                        if (usedGrid.remove(actPos)) {
                            worklist.add(actPos.addToNew(1, 0));
                            worklist.add(actPos.addToNew(-1, 0));
                            worklist.add(actPos.addToNew(0, 1));
                            worklist.add(actPos.addToNew(0, -1));
                        }
                    }
                }
            }
        }

        out("regions", regions);
    }

    private String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        hex = hex.replaceAll("a", "1010");
        hex = hex.replaceAll("b", "1011");
        hex = hex.replaceAll("c", "1100");
        hex = hex.replaceAll("d", "1101");
        hex = hex.replaceAll("e", "1110");
        hex = hex.replaceAll("f", "1111");
        return hex;
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
