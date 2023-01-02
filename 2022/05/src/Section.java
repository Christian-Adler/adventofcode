public class Section {
    int start = 0;
    int stop = 0;

    Section(String in) {
        String[] split = in.split("-");
        start = Integer.parseInt(split[0]);
        stop = Integer.parseInt(split[1]);
    }

    boolean contains(Section other) {
        return start <= other.start && stop >= other.stop;
    }

    boolean overlaps(Section other) {
        return start <= other.stop && stop >= other.start;
    }
}
