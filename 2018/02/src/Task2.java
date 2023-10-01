import java.util.ArrayList;
import java.util.List;

public class Task2 {

    List<String> inputs = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        inputs.add(input);
    }

    public void afterParse() {
        for (int i = 0; i < inputs.size() - 1; i++) {
            String s1 = inputs.get(i);
            for (int j = i + 1; j < inputs.size(); j++) {
                String s2 = inputs.get(j);

                ArrayList<String> l1 = Util.str2List(s1);
                ArrayList<String> l2 = Util.str2List(s2);

                int countDiff = 0;
                for (int k = 0; k < l1.size(); k++) {
                    if (!l1.get(k).equals(l2.get(k)))
                        countDiff++;

                    if (countDiff > 1) break;
                }

                if (countDiff == 1) {

                    StringBuilder sameLetters = new StringBuilder();
                    for (int k = 0; k < l1.size(); k++) {
                        if (l1.get(k).equals(l2.get(k)))
                            sameLetters.append(l1.get(k));

                    }

                    out("commonLetters", sameLetters.toString());
                    return;
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
