import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
    List<Aunt> aunts = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        aunts.add(new Aunt(input));
    }

    public void afterParse() {
        out(aunts);

        String data = """
                children: 3
                cats: 7
                samoyeds: 2
                pomeranians: 3
                akitas: 0
                vizslas: 0
                goldfish: 5
                trees: 3
                cars: 2
                perfumes: 1
                """;

        Map<String, Integer> dataMap = new HashMap<>();
        String[] tes = data.split("\n");
        for (String te : tes) {
            String[] parts = te.split(":");
            dataMap.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }
        out(dataMap);

        for (Aunt aunt : aunts) {
            boolean matches = true;
            for (Map.Entry<String, Integer> prop : aunt.properties.entrySet()) {
                if (dataMap.getOrDefault(prop.getKey(), -1) != prop.getValue()) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                out("matching aunt", aunt.no);
                break;
            }
        }
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
