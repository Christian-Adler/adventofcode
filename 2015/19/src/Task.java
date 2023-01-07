import java.util.*;

public class Task {
    Map<String, List<String>> replacements = new HashMap<>();
    String start = "";

    public void init() {
    }

    public void addLine(String input) {
        if (input.trim().isEmpty()) return;
        if (input.contains("=>")) {
            String[] parts = input.split(" => ");
            List<String> list = replacements.get(parts[0]);
            if (list == null) {
                list = new ArrayList<>();
                replacements.put(parts[0], list);
            }
            list.add(parts[1]);
        } else
            start = input;
    }

    public void afterParse() {
        out(replacements);

        Set<String> distinct = new HashSet<>();

        for (Map.Entry<String, List<String>> entry : replacements.entrySet()) {
            String key = entry.getKey();
            List<String> list = entry.getValue();
            for (String s : list) {

                int idx = start.indexOf(key);
                while (idx >= 0) {
                    String replaced = start.substring(0, idx) + s + start.substring(idx + key.length());
                    distinct.add(replaced);
                    idx = start.indexOf(key, idx + 1);
                }
            }

        }

        out(distinct.size());
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
