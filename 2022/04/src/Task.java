public class Task {

    int counterContains = 0;
    int counterOverlaps = 0;

    public void addLine(String input) {
        String in = input.trim();
        String[] split = in.split(",");

        Section s1 = new Section(split[0]);
        Section s2 = new Section(split[1]);

        if (s1.contains(s2) || s2.contains(s1))
            counterContains++;

        if (s1.overlaps(s2))
            counterOverlaps++;
    }

    @Override
    public String toString() {
        return "Contains: " + counterContains + ", Overlaps: " + counterOverlaps;
    }
}
