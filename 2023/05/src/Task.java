import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Task {
    List<Long> seeds;
    RangeMap seed2soil = new RangeMap();
    RangeMap soil2fertilizer = new RangeMap();
    RangeMap fertilizer2water = new RangeMap();
    RangeMap water2light = new RangeMap();
    RangeMap light2temperature = new RangeMap();
    RangeMap temperature2humidity = new RangeMap();
    RangeMap humidity2location = new RangeMap();

    List<RangeMap> allMaps = Arrays.asList(seed2soil, soil2fertilizer, fertilizer2water, water2light, light2temperature, temperature2humidity, humidity2location);
    Iterator<RangeMap> allMapsIt = allMaps.iterator();
    RangeMap actMap;

    public void init() {
    }

    public void addLine(String input) {
        if (input.trim().isEmpty()) return;

        if (input.startsWith("seeds:")) {
            seeds = Arrays.stream(Util.cleanFrom(input, "seeds: ").split("\\s+")).mapToLong(Long::parseLong).boxed().toList();
        } else if (input.endsWith("map:")) {
            actMap = allMapsIt.next();
        } else { // numbers of act map
            List<Long> mapEntryValues = Arrays.stream(input.split("\\s+")).mapToLong(Long::parseLong).boxed().toList();
            actMap.addMapEl(mapEntryValues);
        }
    }

    public void afterParse() {
//        out(seeds);
//        for (RangeMap rangeMap : allMaps) {
//            out(rangeMap);
//        }
        long lowestLocation = Long.MAX_VALUE;

        for (Long seed : seeds) {
//            out("seed", seed);
            Long actInput = seed;
            for (RangeMap rangeMap : allMaps) {
                actInput = rangeMap.getDestinationValue(actInput);
//                out(actInput);
            }

            if (actInput < lowestLocation)
                lowestLocation = actInput;
        }

        out("Part1", "lowestLocation", lowestLocation);

        // Part 2


//        // seeds as Ranges - way to slow with real input
//        lowestLocation = Long.MAX_VALUE;
//        for (int i = 0; i < seeds.size(); i = i + 2) {
//            long seedRangeStart = seeds.get(i);
//            long seedRange = seeds.get(i + 1);
//            out(seedRangeStart + " " + (i + 1) + " of " + seeds.size());
//            for (long seed = seedRangeStart; seed < seedRangeStart + seedRange - 1; seed++) {
//                long actInput = seed;
//                for (RangeMap rangeMap : allMaps) {
//                    actInput = rangeMap.getDestinationValue(actInput);
////                out(actInput);
//                }
//
//                if (actInput < lowestLocation)
//                    lowestLocation = actInput;
//            }
//        }

        // Seeds as ranges and handle always as ranges
        List<Range> seedRanges = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i = i + 2) {
            long seedRangeStart = seeds.get(i);
            long seedRange = seeds.get(i + 1);
            seedRanges.add(new Range(seedRangeStart, seedRangeStart + seedRange - 1));
        }

        for (RangeMap rangeMap : allMaps) {
            seedRanges = rangeMap.getDestinationRanges(seedRanges);
        }

        lowestLocation = seedRanges.stream().mapToLong(Range::from).min().orElseThrow();

        out("Part2", "lowestLocation", lowestLocation);
    }

    public void out(Object... str) {
        Util.out(str);
    }
}
