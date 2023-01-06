import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task {
    Set<Person> persons = new HashSet<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] parts = cleanFrom(input, " would", " happiness units by sitting next to", ".").split(" ");
        String p = parts[0];
        boolean gain = parts[1].equals("gain");
        int happiness = Integer.parseInt(parts[2]) * (gain ? 1 : -1);
        String n = parts[3];
//        out(happiness);

        Person person = persons.stream().filter(per -> per.name.equals(p)).findFirst().orElse(new Person(p));
        Person neighbor = persons.stream().filter(per -> per.name.equals(n)).findFirst().orElse(new Person(n));
        persons.add(person);
        persons.add(neighbor);
        person.neighbor2Happiness.put(neighbor, happiness);
    }

    public void afterParse() {
        out(persons);


        int happiness = findMax(persons.iterator().next(), new ArrayList<>(), 0, 0);
        out("maxHappiness: ", happiness);
    }


    int findMax(Person visitNode, ArrayList<Person> visited, int soFarHappiness, int happinessGain) {
        if (visited.size() > persons.size() * 2)
            return Integer.MIN_VALUE;

        // keine Doppelten
        if (visited.contains(visitNode))
            return Integer.MIN_VALUE;

        int sumHappiness = soFarHappiness + happinessGain;
        ArrayList<Person> sumVisited = new ArrayList<>(visited);
        sumVisited.add(visitNode);

        // Alle Nodes erreicht?
        if (sumVisited.containsAll(persons)) {
            // Kante zum Anfangsknoten noch dazu rechnen
            int happinessFirstLast = visitNode.neighbor2Happiness.get(visited.get(0)) + visited.get(0).neighbor2Happiness.get(visitNode);
            return sumHappiness + happinessFirstLast;
        }

        int maxHappiness = Integer.MIN_VALUE;

        for (Map.Entry<Person, Integer> neighborEntry : visitNode.neighbor2Happiness.entrySet()) {
            Person neighbor = neighborEntry.getKey();
            Integer happinessToNeighbor = neighborEntry.getValue();

            Integer happinessOfNeighborWouldBe = neighbor.neighbor2Happiness.get(visitNode);

            int sumHappinessGain = happinessToNeighbor + happinessOfNeighborWouldBe;

            int happiness = findMax(neighbor, new ArrayList<>(sumVisited), sumHappiness, sumHappinessGain);
            maxHappiness = Math.max(maxHappiness, happiness);
        }

        return maxHappiness;
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
        builder.append(persons);
        return builder.toString();
    }
}
