import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class Template {
    private final Map<String, String> mapping = new HashMap<>();
    public String polymer = "";

    long counter = 0;
    long numIterations = 0;

    public void addMapping(String line) {
        String[] split = line.split("->");
        if (split.length != 2) return;
        mapping.put(split[0].trim(), split[1].trim());
    }

    @Override
    public String toString() {
        return polymer;
    }

    public void calc(int steps) throws InterruptedException {
        Map<String, Long> map = new HashMap<>();
        counter = 0;

        numIterations = (long) polymer.length() * (long) Math.pow(2, steps);

        calc(polymer, steps, map);

        LinkedList<Long> sorted = map.values().stream().sorted().collect(Collectors.toCollection(LinkedList::new));

        long sub = sorted.getLast() - sorted.getFirst();
        System.out.println(sub);
    }


    /*
    ABC
    AXB BXC -> -B => AXBXC
    A-X X-B B-X X-C -> -X -B -X => A-X-B-X-C

    ABC
    AB
    AXB
    AX
    A-X
     XB
     X-B
     */

    private void calc(String actPolymer, int stepsLeft, Map<String, Long> map) throws InterruptedException {
        if (stepsLeft == 0) { // Rekursionsabbruch
            String[] spli = actPolymer.split("");
            for (String s : spli) {
                Long val = map.getOrDefault(s, 0L);
                map.put(s, val + 1);
                counter++;
            }
            System.out.println(counter + " / " + numIterations);
            return;
        }

        // Thread.sleep(1);

        String[] split = actPolymer.split("");
        String prev = null;
        for (int i = 1; i < split.length; i++) {
            String a = split[i - 1];
            String b = split[i];

            String aAndB = mapping.get(a + b);

            calc(a + aAndB + b, stepsLeft - 1, map);


            // Vorheriger 2ter Buchstabe vorhanden (also immer ausser beim 1sten)? Dann Korrektur: 1 abziehen
            if (prev != null) {
                Long val = map.getOrDefault(prev, 0L);
                map.put(prev, val - 1);
            }

            prev = b;
        }
    }
}
