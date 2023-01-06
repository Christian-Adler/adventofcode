import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task2 {

    Set<R> reindeers = new HashSet<>();

    public void init() {
    }

    public void addLine(String input) {
        String cleaned = cleanFrom(input, " can fly", " km/s for", " seconds, but then must rest for", " seconds.");
        reindeers.add(new R(cleaned));
    }

    public void afterParse() {
        out(reindeers);
        Map<R, Integer> maxDistance = new HashMap<>();
        Map<R, Integer> points = new HashMap<>();

        int seconds = 2503;
        for (int i = 1; i <= seconds; i++) {
            for (R reindeer : reindeers) {
                int dist = calcDistanceAfterSeconds(reindeer, i);
//            out(reindeer.name, dist);
                maxDistance.put(reindeer, dist);
            }
            // Points
            int max = maxDistance.values().stream().mapToInt(in -> in).max().orElse(0);
            for (Map.Entry<R, Integer> entry : maxDistance.entrySet()) {
                if (entry.getValue() == max) {
                    points.compute(entry.getKey(), (R re, Integer soFar) -> {
                        return soFar == null ? 1 : soFar + 1;
                    });
                }
            }
        }

        int maxPoints = points.values().stream().mapToInt(in -> in).max().orElse(0);
        out(maxPoints);
    }

    int calcDistanceAfterSeconds(R r, int seconds) {
        int fullCycles = (int) Math.floor(seconds / (r.flyDuration + r.restDuration));
        int rest = seconds - fullCycles * (r.flyDuration + r.restDuration);
        int restFlySeconds = Math.min(rest, r.flyDuration);

        return r.speed * (fullCycles * r.flyDuration + restFlySeconds);
    }

    public void out(Object... str) {
        String out = "";
        for (Object o : str) {
            if (out.length() > 0)
                out += " ";
            out += o;
        }
        System.out.println(out);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            result = result.replace(string, "");
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
