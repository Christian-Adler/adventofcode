import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task2 {
    Map<String, List<String>> replacements = new HashMap<>();
    String start = "";
    int foundMin = Integer.MAX_VALUE;
    int sameResultCounter = 0;
    boolean abortSearch = false;

    public void init() {
    }

    public void addLine(String input) {
        if (input.trim().isEmpty()) return;
        if (input.contains("=>")) {
            String[] parts = input.split(" => ");
            List<String> list = replacements.get(parts[1]);
            if (list == null) {
                list = new ArrayList<>();
                replacements.put(parts[1], list);
            }
            list.add(parts[0]);
        } else
            start = input;
    }

    public void afterParse() {
        out(replacements);

        out(minSteps(start, 0));
        //518 ist zu hoch
    }

    int minSteps(String input, int soFarSteps) {
        if (abortSearch)
            return foundMin;

//        out(soFarSteps);
        if (input.equals("e")) {
            if (foundMin == soFarSteps)
                sameResultCounter++;
            else {
                foundMin = Math.min(foundMin, soFarSteps);
                sameResultCounter = 1;
            }

            if (sameResultCounter == 200)
                abortSearch = true;

            return soFarSteps;
        }

        if (soFarSteps >= foundMin)
            return Integer.MAX_VALUE;

        int min = Integer.MAX_VALUE;

        for (Map.Entry<String, List<String>> entry : replacements.entrySet()) {
            String key = entry.getKey();
            List<String> list = entry.getValue();
            for (String s : list) {
                int idx = input.indexOf(key);
                while (idx >= 0) {
                    String replaced = input.substring(0, idx) + s;
                    if (input.length() > idx + key.length())
                        replaced += input.substring(idx + key.length());
                    min = Math.min(min, minSteps(replaced, soFarSteps + 1));
                    idx = input.indexOf(key, idx + 1);
                }
            }

        }

        return min;
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
