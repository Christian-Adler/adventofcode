import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task2 {
    List<Integer> list = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        list.add(Integer.parseInt(input));
    }

    public void afterParse() {
        Set<Integer> soFarFrequencies = new HashSet<>();
        int f = 0;
        soFarFrequencies.add(f);
        boolean foundDuplicate = false;
        while (!foundDuplicate) {
            for (Integer change : list) {
                f += change;
                if (!soFarFrequencies.add(f)) {
                    foundDuplicate = true;
                    out("reached twice", f);
                    break;
                }
            }
        }
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
