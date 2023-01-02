import java.util.ArrayList;
import java.util.List;

public class Elf implements Comparable<Elf> {
    private final List<Integer> calories = new ArrayList<>();

    public void add(int cal) {
        calories.add(cal);
    }

    public int getSumCalories() {
        return calories.stream().mapToInt(Integer::valueOf).sum();
    }

    @Override
    public int compareTo(Elf e) {
        return Integer.compare(getSumCalories(), e.getSumCalories());
    }

    public String toString() {
        return String.valueOf(getSumCalories());
    }
}
