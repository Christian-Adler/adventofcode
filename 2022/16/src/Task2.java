import java.util.*;

public class Task2 {
    final int MAX_MINUTES = 26;

    Valve start = null;
    Set<Valve> valves = new HashSet<>();

    Map<Set<String>, Integer> openVales2Value = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] split = cleanFrom(input, " tunnels lead to valves ", " tunnel leads to valve ", " has flow rate", "Valve ").split(";");
        String[] valveInfo = split[0].split("=");
        String[] tunnelsTo = split[1].split(", ");

        Valve valve = getOrCreate(valveInfo[0]);
        if (valve.name.equals("AA"))
            start = valve;

        valve.flowRate = Integer.parseInt(valveInfo[1]);

        for (int i = 0; i < tunnelsTo.length; i++) {
            String tunnelsToValve = tunnelsTo[i];
            valve.neighbors.add(getOrCreate(tunnelsToValve));
        }
    }

    Valve getOrCreate(String name) {
        // Schon erstellt (aber bisher ohne Info)
        for (Valve valve : valves) {
            if (valve.name.equals(name)) {
                return valve;
            }
        }

        Valve v = new Valve(name);
        valves.add(v);
        return v;
    }


    public void afterParse() {

        findMax(start, null, MAX_MINUTES, new ArrayList<ValveAction>());
        int maxPressure =
                openVales2Value.values().stream().max(Integer::compare).orElse(0);
        out("maxPressureRelease: " + maxPressure);


        int maxSum = 0;
        for (Set<String> strings : openVales2Value.keySet()) {
            for (Set<String> strings2 : openVales2Value.keySet()) {
                if (Collections.disjoint(strings, strings2)) {
                    maxSum = Math.max(maxSum, openVales2Value.get(strings) + openVales2Value.get(strings2));
                }
            }
        }
        out(maxSum);
    }

    void findMax(Valve actValve, Valve from, int minutesLeft, ArrayList<ValveAction> soFarPath) {
        if (minutesLeft < 0)
            return;

        if (minutesLeft == 0) {
            Set<String> openValves = new HashSet<>();
            for (ValveAction valveAction : soFarPath) {
                if (valveAction.open)
                    openValves.add(valveAction.valve.name);
            }
            int value = calcValue(soFarPath, false);
            Integer soFarValue = openVales2Value.get(openValves);
            if (soFarValue == null || value > soFarValue)
                openVales2Value.put(openValves, value);
            return;
        }


        ArrayList<ValveAction> tmp;

        // actuelles noch nicht geoeffnet?
        if (actValve.flowRate > 0 && !isAlreadyOpened(actValve, soFarPath)) {
            tmp = new ArrayList<>(soFarPath);
            tmp.add(new ValveAction(actValve, true));

            findMax(actValve, actValve, minutesLeft - 1, tmp);
        }

        // Weiter ohne oeffnen
        tmp = new ArrayList<>(soFarPath);
        tmp.add(new ValveAction(actValve, false));

        for (Valve neighbor : actValve.neighbors) {
            // es macht keinen Sinn zum vorgaenger zurueck zu gehen, ohne hier geoeffnet zu haben - dann haette man sich den Weg sparen koennen
            if (neighbor.equals(from)) continue;

            findMax(neighbor, actValve, minutesLeft - 1, tmp);
        }
    }

    boolean isAlreadyOpened(Valve valve, List<ValveAction> soFarPath) {
        for (ValveAction valveAction : soFarPath) {
            if (valve.equals(valveAction.valve) && valveAction.open)
                return true;
        }
        return false;
    }

    int calcValue(List<ValveAction> path, boolean writeToConsole) {
        int pressureReleaseSum = 0;
        int minute = 0;
        Iterator<ValveAction> it = path.iterator();
        while (it.hasNext()) {

            if (minute > MAX_MINUTES) {
                out("SHOULD NEVER HAPPEN!");
                break;
            }

            ValveAction valveAction = it.next();
            Valve valve = valveAction.valve;
            if (writeToConsole) out(minute + " min : At valve " + valve.name);
            int pressureRelease = 0;
            if (valveAction.open) {
                if (writeToConsole) out("Open valve " + valve.name);
                pressureRelease = (MAX_MINUTES - minute - 1) * valve.flowRate;
            }
            pressureReleaseSum += pressureRelease;

            // way to next valve
            if (it.hasNext()) {
                if (writeToConsole) out(minute + " min : Move to next valve");
                minute++;
            }
        }
        return pressureReleaseSum;
    }

    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            input = input.replace(string, "");
        }
        return input;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

//        builder.append(valves);

        return builder.toString();
    }

    public String toStringSVG() {
        StringBuilder builder = new StringBuilder();


        return builder.toString();
    }
}
