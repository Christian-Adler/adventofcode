import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Task {

    long from = Long.MAX_VALUE;
    long to = Long.MIN_VALUE;

    ArrayList<Range> list = new ArrayList<>();

    public void addLine(String input) {
        long[] parts = Arrays.stream(input.split("-")).mapToLong(Long::parseLong).toArray();
        if (from == Long.MAX_VALUE) { // erste Zeile?
            from = parts[0];
            to = parts[1];
            return;
        }
        Range range = new Range(parts[0], parts[1]);
        list.add(range);
    }


    void afterParse() {
        // Sortieren
        Collections.sort(list);

        out(list);

        // Luecke finden

        Range combined = list.get(0);

        long minIp = -1;
        ArrayList<Range> holes = new ArrayList<>();

        // Luecke schon vor dem Ersten?
        if (combined.from > from) {
            minIp = from;
            holes.add(new Range(from, combined.from - 1));
        }


        for (Range r : list) {
            Range tmp = combined;
            combined = combined.combine(r);
            if (combined == null) {
                out("luecke nach " + tmp);
                out("luecke for " + r);
                if (minIp < 0) {
                    minIp = tmp.to + 1;
                }

                holes.add(new Range(tmp.to + 1, r.from - 1));
                combined = r;
            }
        }

        // Luecke nach dem Letzten?
        if (combined.to < to) {
            holes.add(new Range(combined.to + 1, to));
        }

        out("first IP:", minIp);
        out("Holes:", holes);
        long numNotCovered = 0;
        for (Range hole : holes) {
            numNotCovered += hole.size();
        }
        out("numNotCovered", numNotCovered);
    }


    public void out(Object... str) {
        Util.out(str);
    }
}
