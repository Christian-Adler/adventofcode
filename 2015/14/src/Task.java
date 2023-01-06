import java.util.HashSet;
import java.util.Set;

public class Task {

    Set<R> reindeers = new HashSet<>();

    public void init() {
    }

    public void addLine(String input) {
        String cleaned = cleanFrom(input, " can fly", " km/s for", " seconds, but then must rest for", " seconds.");
        reindeers.add(new R(cleaned));
    }

    public void afterParse() {
        out(reindeers);
        int maxDistance = 0;
        int seconds = 2503;
        for (R reindeer : reindeers) {
            int dist = calcDistanceAfterSeconds(reindeer, seconds);
            out(reindeer.name, dist);
            maxDistance = Math.max(maxDistance, dist);
        }
        out(maxDistance);
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
