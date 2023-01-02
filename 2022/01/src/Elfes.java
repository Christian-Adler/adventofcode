import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Elfes {
    Elf actElf;
    List<Elf> elfes = new ArrayList<>();

    @Override
    public String toString() {
        return "Elfes{" +
                "elfes=" + elfes +
                '}';
    }

    public void addLine(String input) {
        String in = input.trim();
        if (actElf == null || in.isEmpty()) {
            actElf = new Elf();
            elfes.add(actElf);
        } else {
            int calories = Integer.parseInt(in);
            actElf.add(calories);
        }
    }

    public long getTheMostCalories() {
        return elfes.stream().mapToInt(Elf::getSumCalories).max().orElse(0);
    }

    public long getTheThreeTopMostCalories() {
        Collections.sort(elfes);
        System.out.println(toString());
        if (elfes.size() < 3)
            return elfes.stream().mapToInt(Elf::getSumCalories).sum();
        return elfes.subList(elfes.size() - 3, elfes.size()).stream().mapToInt(Elf::getSumCalories).sum();
    }
}
