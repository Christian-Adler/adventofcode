import java.util.HashMap;
import java.util.Map;

public class Task {
    Map<String, SpaceObject> spaceObjects = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split("\\)");
        String center = split[0];
        String orbiter = split[1];

        SpaceObject soCenter = spaceObjects.get(center);
        if (soCenter == null) {
            soCenter = new SpaceObject(center);
            spaceObjects.put(center, soCenter);
        }

        SpaceObject soOribiter = spaceObjects.get(orbiter);
        if (soOribiter == null) {
            soOribiter = new SpaceObject(orbiter);
            spaceObjects.put(orbiter, soOribiter);
        }

        soOribiter.orbits = soCenter;
    }

    public void afterParse() {
        // Part 1
        int countOrbits = 0;
        for (SpaceObject spaceObject : spaceObjects.values()) {
            SpaceObject s = spaceObject.orbits;
            while (s != null) {
                countOrbits++;
                s = s.orbits;
            }
        }

        out("Part 1", "countOrbits", countOrbits);

        // Part 2
        // Path from SAN to COM
        // Path from YOU to COM
        // where do the meet each other

        SpaceObject san = spaceObjects.get("SAN");
        SpaceObject you = spaceObjects.get("YOU");

        Map<String, Integer> stepsSan = new HashMap<>();
        int step = 0;
        SpaceObject s = san.orbits;
        while (s != null) {
            step++;
            s = s.orbits;
            if (s != null)
                stepsSan.put(s.name, step);
        }
//        out(stepsSan);

        step = 0;
        s = you.orbits;
        while (s != null) {
            step++;
            s = s.orbits;

            if (stepsSan.containsKey(s.name)) {
                out("Part 2", "minimum orbital transfers", (stepsSan.get(s.name) + step));
                break;
            }
        }
    }

    public void out(Object... str) {
        Util.out(str);
    }
}
