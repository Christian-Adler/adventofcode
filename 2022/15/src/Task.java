import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task {


    int yMin = 0;
    int yMax = 0;
    int xMin = Integer.MAX_VALUE;
    int xMax = Integer.MIN_VALUE;


    Map<Pos, Item> map = new HashMap<>();

    // y > Liste von x Pos-Ranges
    Map<Integer, Set<Range>> cannotPositions = new HashMap<>();

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


    public void afterParse(int yToCount) {

        // alle Positionen ermitteln, an denen kein Beacon sein kann
        int sensorCount = 0;
        for (Item item : map.values()) {
            Pos pos = item.pos;
//            if (item.isBeacon()) {
//                Set<Range> xCoords = cannotPositions.get(pos.y);
//                if (xCoords == null) {
//                    xCoords = new HashSet<>();
//                    cannotPositions.put(pos.y, xCoords);
//                }
//                // Sich selbst nicht mit aufnehemne   xCoords.add(pos.x);
//            }
            if (item.isSensor()) {
                sensorCount++;
//                out("sensors: " + sensorCount);

                out(item);
                Pos beaconPos = item.closestBeacon;

                int distance = pos.distance(beaconPos);
//                out("distance: " + distance);

                Range yRange = new Range(pos.y - distance, pos.y + distance);
                if (yRange.in(yToCount)) {
                    int xRest = distance - Math.abs(pos.y - yToCount);
                    Range xRange = new Range(pos.x - xRest, pos.x + xRest);
//                    out("xRange: " + xRange);

                    Set<Range> xCoords = cannotPositions.get(yToCount);
                    if (xCoords == null) {
                        xCoords = new HashSet<>();
                        cannotPositions.put(yToCount, xCoords);
                    }

                    xCoords.add(xRange);
                }

//                // Alle moeglichen Positionen durchgehen
//                for (int y = pos.y - distance; y <= pos.y + distance; y++) {
//
//                    Set<Range> xCoords = cannotPositions.get(y);
//                    if (xCoords == null) {
//                        xCoords = new HashSet<>();
//                        cannotPositions.put(y, xCoords);
//                    }
//
//                    for (int x = pos.x - distance; x <= pos.x; x++) {
//                        Pos checkPos = new Pos(x, y);
//                        calcMinMax(checkPos);
//
////                        // Wenn schon belegt ignorieren
////                        if (map.get(checkPos) != null) continue;
//
//                        if (pos.distance(checkPos) <= distance) {
//                            xCoords.add(new Range(x, pos.x + pos.x - x));
//                            break;
//                        }
//
//                    }
//                }
            }
        }

        Set<Range> xCoords = cannotPositions.get(yToCount);
        if (xCoords != null) {
//            out("cannot Ranges: " + xCoords.size() + " " + xCoords);
            Set<Integer> cannot = new HashSet<>();
            for (Range xCoord : xCoords) {
                for (int x = xCoord.from; x <= xCoord.to; x++) {
                    cannot.add(x);
                }
            }
            out(cannot);

            for (Pos pos : map.keySet()) {
                if (pos.y == yToCount)
                    cannot.remove(pos.x);
            }

            out(cannot);

            out(cannot.size());
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

        for (int y = yMin; y <= yMax; y++) {
            builder.append("\r\n ").append(toSize(y)).append(" ");
            for (int x = xMin; x <= xMax; x++) {
                Item item = map.get(new Pos(x, y));
                if (item != null) {
                    builder.append(item.type);
                } else {
                    Set<Range> xCoords = cannotPositions.get(y);
                    if (xCoords != null && xCoords.contains(x))
                        builder.append("#");
                    else
                        builder.append(".");
                }
            }
        }

        return builder.toString();
    }

    public String toStringSVG() {
        StringBuilder builder = new StringBuilder();

        for (int y = yMin; y <= yMax; y++) {
            builder.append("\r\n ");
            for (int x = xMin; x <= xMax; x++) {
                Item item = map.get(new Pos(x, y));
                if (item != null) {
//                    builder.append(item.type);
                    builder.append("<rect style=\"fill:#" + (item.isSensor() ? "00ff00" : "ff0000") + ";\" width=\"1\" height=\"1\" x=\"" + x + "\" y=\"" + y + "\" />");
                } else {
                    Set<Range> xCoords = cannotPositions.get(y);
                    if (xCoords != null && xCoords.contains(x))
//                        builder.append("#");
                        builder.append("<rect style=\"fill:#0000ff;\" width=\"1\" height=\"1\" x=\"" + x + "\" y=\"" + y + "\" />");
//                    else
//                        builder.append(".");
                }
            }
        }

        return builder.toString();
    }

    String toSize(int value) {
        String res = String.valueOf(value);
        while (res.length() < 10)
            res = " " + res;
        return res;
    }
}
