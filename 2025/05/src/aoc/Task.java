package aoc;

import aoc.util.Range;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
    public static void main(String[] args) throws Exception {
//        runForInput("./input_example_1.txt");
        runForInput("./input.txt");
    }

    private final List<Range> ranges = new ArrayList<>();
    private final List<Long> ids = new ArrayList<>();
    private boolean parseRanges = true;

    public void exec(List<String> lines, Object... params) throws Exception {
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                parseRanges = false;
                continue;
            }
            if (parseRanges)
                ranges.add(Range.parse(line));
            else ids.add(Long.parseLong(line));
        }

//      out(ids,ranges);
        long countFresh = 0;
        for (Long id : ids) {
            for (Range range : ranges) {
                if (range.contains(id)) {
                    countFresh++;
                    break;
                }
            }
        }
        out("part 1: ", countFresh);

        ranges.sort(Comparator.comparingLong(Range::from).thenComparingLong(Range::to));

        long sumRanges = 0;

        Range actRange = null;
        for (Range range : ranges) {
            if (actRange == null) actRange = range;
            else {
                Range combined = actRange.combine(range);
                if (combined == null) {
                    sumRanges += actRange.size();
                    actRange = range;
                } else {
                    actRange = combined;
                }
            }
        }
        if (actRange != null) sumRanges += actRange.size();
        out("part 2: ", sumRanges); //  > 6047102612156
        //                                   348548952146313
    }
}
