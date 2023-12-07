import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RangeMap {
    private final List<RangeMapEl> ranges = new ArrayList<>();

    public void addMapEl(List<Long> rangeMapElData) {
        ranges.add(new RangeMapEl(rangeMapElData));
    }

    public long getDestinationValue(long input) {
        for (RangeMapEl range : ranges) {
            Optional<Long> opt = range.getDestinationValue(input);
            if (opt.isPresent())
                return opt.get();
        }
        // Fallback
        return input;
    }

    public List<Range> getDestinationRanges(List<Range> seedRanges) {
        // Sort by source
        ranges.sort((o1, o2) -> Long.compare(o1.sourceRangeStart, o2.sourceRangeStart));

        List<Range> result = new ArrayList<>();

        for (Range seedRange : seedRanges) {
            Range actSeedRange = seedRange;

            for (RangeMapEl mapEl : ranges) {
                if (actSeedRange == null)
                    break;

                // act
                //     el
                if (actSeedRange.to() < mapEl.sourceRangeStart)
                    break;
                //     act
                // el
                if (actSeedRange.from() > mapEl.getSourceRangeEnd())
                    continue;

                // a???
                //  el
                if (actSeedRange.from() < mapEl.sourceRangeStart) {
                    result.add(new Range(actSeedRange.from(), mapEl.sourceRangeStart - 1));
                    actSeedRange = new Range(mapEl.sourceRangeStart, actSeedRange.to());
                }
                // act
                // eeeeel
                if (actSeedRange.to() <= mapEl.getSourceRangeEnd()) {
                    Range destinationSeedRange = new Range(getDestinationValue(actSeedRange.from()), getDestinationValue(actSeedRange.to()));
                    result.add(destinationSeedRange);
                    actSeedRange = null;
                    break;
                }
                //  aact
                //  el
                Range destinationSeedRange = new Range(getDestinationValue(actSeedRange.from()), getDestinationValue(mapEl.getSourceRangeEnd()));
                result.add(destinationSeedRange);
                actSeedRange = new Range(mapEl.getSourceRangeEnd() + 1, actSeedRange.to());
            }

            if (actSeedRange != null)
                result.add(actSeedRange);
        }
        return result;
    }

    @Override
    public String toString() {
        return "RangeMap{" + "\r\n" + ranges +
                '}';
    }

    private static class RangeMapEl {
        public final long destinationRangeStart;
        public final long sourceRangeStart;
        public final long rangeLength;

        public RangeMapEl(List<Long> list) {
            this.destinationRangeStart = list.get(0);
            this.sourceRangeStart = list.get(1);
            this.rangeLength = list.get(2);
        }

        public long getSourceRangeEnd() {
            return sourceRangeStart + rangeLength - 1;
        }

        public Optional<Long> getDestinationValue(long input) {
            if (input >= sourceRangeStart && input <= getSourceRangeEnd()) {
                long diff = input - sourceRangeStart;
                return Optional.of(destinationRangeStart + diff);
            }
            return Optional.empty();
        }

        @Override
        public String toString() {
            return destinationRangeStart +
                    " " + sourceRangeStart +
                    " " + rangeLength + " source:" + new Range(sourceRangeStart, getSourceRangeEnd()) + " dest:" + new Range(destinationRangeStart, destinationRangeStart + rangeLength - 1);
        }
    }

    public static void main(String[] args) {
        RangeMap rangeMap = new RangeMap();
        rangeMap.addMapEl(Arrays.asList(10L, 20L, 5L));
        rangeMap.addMapEl(Arrays.asList(120L, 100L, 5L));

        printCheck(1, 1, rangeMap);
        printCheck(19, 19, rangeMap);
        printCheck(20, 10, rangeMap);
        printCheck(22, 12, rangeMap);
        printCheck(25, 15, rangeMap);
        printCheck(26, 26, rangeMap);
        printCheck(99, 99, rangeMap);
        printCheck(100, 120, rangeMap);
        printCheck(104, 124, rangeMap);
        printCheck(106, 106, rangeMap);
        printCheck(123, 123, rangeMap);
    }

    private static void printCheck(long input, long expected, RangeMap rangeMap) {
        System.out.println("in: " + input + " out: " + rangeMap.getDestinationValue(input) + " expected: " + expected);
    }

}
