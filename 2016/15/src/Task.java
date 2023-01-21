import java.util.ArrayList;
import java.util.Arrays;

public class Task {

    ArrayList<Disc> discs = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        // Disc #2 has 2 positions; at time=0, it is at position 1.
        int[] parts = Arrays.stream(Util.cleanFrom(input, "Disc #", " has", " positions; at time=0, it is at position", ".").split(" ")).mapToInt(Integer::parseInt).toArray();
        discs.add(new Disc(parts[0], parts[1], parts[2]));
    }

    public void addDisc() {
        discs.add(new Disc(discs.size() + 1, 11, 0));
    }

    public void afterParse() {
        out(discs);
        int time = -1;
        boolean fallThrough = false;
        while (!fallThrough) {
            time++;
//            out("\r\nime:", time);
//            for (Disc disc : discs) {
//                out(disc.atTime(time), fallsThroughAtTime(disc, time));
//            }
            fallThrough = checkFallThroughAtTime(time);
//            out("fall through:", fallThrough);
        }
        out("fall through time:", time);
    }

    boolean checkFallThroughAtTime(int time) {
        for (int i = 0; i < discs.size(); i++) {
            Disc disc = discs.get(i);
            if (!fallsThroughAtTime(disc, time + i + 1))
                return false;
        }
        return true;
    }

    boolean fallsThroughAtTime(Disc disc, int time) {
        int holeAtTime = disc.holeAtTime(time);
        return holeAtTime == 0;
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
