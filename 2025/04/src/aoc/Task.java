package aoc;

import aoc.util.Img;
import aoc.util.Util;
import aoc.util.Vec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
    public static void main(String[] args) throws Exception {
//        runForInput("./input_example_1.txt");
        runForInput("./input.txt");
    }

    private final Set<Vec> rolls = new HashSet<>();
    private int maxY = -1;


    public void exec(List<String> lines, Object... params) throws Exception {
        for (String line : lines) {
            maxY++;
            ArrayList<String> chars = Util.str2List(line);
            int x = -1;
            for (String aChar : chars) {
                x++;
                if (aChar.equals("@")) rolls.add(new Vec(x, maxY));
            }
        }
//        out(toString());

        long countLT4 = getCountLT4();
        out("part 1: ", countLT4);

        long countMultipleTimes = countLT4;
        while (countLT4 > 0) {
            countLT4 = getCountLT4();
            countMultipleTimes += countLT4;
        }
        out("part 2: ", countMultipleTimes);
    }

    private long getCountLT4() {
        Set<Vec> toRemove = new HashSet<>();
        // count <4
        long countLT4 = 0;
        for (Vec roll : rolls) {
            int countNeighbors = 0;
            for (Vec adj : Vec.adjacent8) {
                if (rolls.contains(roll.add(adj))) countNeighbors++;
            }
            if (countNeighbors < 4) {
                countLT4++;
                toRemove.add(roll);
            }
        }

        rolls.removeAll(toRemove);

        return countLT4;
    }

    @Override
    public String toString() {
        return toStringConsole();
    }

    public void toBmp() throws Exception {
        Img img = new Img();
        img.writeBitmapAged();
    }

    public String toStringConsole() {
        Img img = new Img();
        for (Vec roll : rolls) {
            img.add(roll);
        }
        return img.toConsoleString();
    }
}
