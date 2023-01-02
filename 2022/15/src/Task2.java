import java.util.*;
import java.util.stream.Collectors;

public class Task2 {


    int yMin = 0;
    int yMax = 0;
    int xMin = Integer.MAX_VALUE;
    int xMax = Integer.MIN_VALUE;


    Map<Pos, Item> map = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.replace("Sensor at ", "").replace(" closest beacon is at ", "").split(":");
        Item beacon = new Item("B", new Pos(split[1]));
        Item sensor = new Item("S", new Pos(split[0]));
        sensor.closestBeacon = beacon.pos;

        calcMinMax(sensor.pos);
        calcMinMax(beacon.pos);

        map.put(sensor.pos, sensor);
        map.put(beacon.pos, beacon);
    }

    private void calcMinMax(Pos pos) {
        yMin = Math.min(yMin, pos.y);
        yMax = Math.max(yMax, pos.y);
        xMin = Math.min(xMin, pos.x);
        xMax = Math.max(xMax, pos.x);
    }


    public void afterParse(int minLimit, int maxLimit) {

        Pos foundEmptyPos = null;

        for (int yToCount = minLimit; yToCount <= maxLimit; yToCount++) {
            if (foundEmptyPos != null) break;

            if (yToCount % 100 == 0)
                out(yToCount);

            Set<Range> xCoords = new HashSet<>();

            // alle Positionen ermitteln, an denen kein Beacon sein kann
            int sensorCount = 0;
            for (Item item : map.values()) {
                Pos pos = item.pos;

                if (item.isSensor()) {
                    sensorCount++;
//                out("sensors: " + sensorCount);
//                out(item);
                    Pos beaconPos = item.closestBeacon;

                    int distance = pos.distance(beaconPos);
//                out("distance: " + distance);

                    Range yRange = new Range(pos.y - distance, pos.y + distance);
                    if (yRange.in(yToCount)) {
                        int xRest = distance - Math.abs(pos.y - yToCount);
                        Range xRange = new Range(pos.x - xRest, pos.x + xRest);
//                    out("xRange: " + xRange);

                        xCoords.add(xRange);
                    }

                }
            }


            Set<Integer> cannot = new HashSet<>();

            // Ranges sortieren - und dann zusammen fassen - sobald Luecke fertig
            List<Range> sortedXCoords = new ArrayList<>(xCoords.stream().filter(
                    range -> range.to > minLimit && range.from < maxLimit
            ).collect(Collectors.toList()));
            sortedXCoords.sort((r1, r2) -> Integer.compare(r1.from, r2.from));
            Range combined = sortedXCoords.get(0);
            for (Range cor : sortedXCoords) {
                Range tmp = combined;
                combined = combined.combine(cor);
                if (combined == null) {
                    out("luecke nach " + tmp);
                    out("luecke for " + cor);
                    foundEmptyPos = new Pos(cor.from - 1, yToCount);
                    break;
                }
            }


//            for (Range xCoord : xCoords) {
//                for (int x = xCoord.from; x <= xCoord.to; x++) {
//                    if (x >= minLimit && x <= maxLimit)
//                        cannot.add(x);
//                }
//            }

//            out(cannot);

//            for (Pos pos : map.keySet()) {
//                if (pos.y == yToCount)
//                    cannot.remove(pos.x);
//            }

//            out(cannot);

//            out(cannot.size());
//            if (cannot.size() != (maxLimit - minLimit + 1)) {
////                out("FOUND y: " + yToCount);
//                for (int x = minLimit; x < maxLimit; x++) {
//                    if (!cannot.contains(x)) {
////                        out("FOUND x: " + x);
//                        foundEmptyPos = new Pos(x, yToCount);
//                        break;
//                    }
//                }
//            }
        }

        if (foundEmptyPos != null) {
            out(foundEmptyPos);
            long frequency = foundEmptyPos.x * 4000000l + foundEmptyPos.y;
            out(frequency);
        }
    }


    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();


        return builder.toString();
    }

    public String toStringSVG() {
        StringBuilder builder = new StringBuilder();


        return builder.toString();
    }

    String toSize(int value) {
        String res = String.valueOf(value);
        while (res.length() < 10)
            res = " " + res;
        return res;
    }
}
