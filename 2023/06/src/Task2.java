import java.util.ArrayList;
import java.util.Arrays;

public class Task2 {
    private ArrayList<Time2Distance> races;

    public void init() {
    }

    public void addLine(String input) {
        if (input.startsWith("Time"))
            races = new ArrayList<>(Arrays.stream(Util.cleanFrom(input, "Time:", " ").trim().split("\\s+")).mapToLong(Long::parseLong).mapToObj(Time2Distance::new).toList());
        else if (input.startsWith("Distance")) {
            ArrayList<Long> distances = new ArrayList<>(Arrays.stream(Util.cleanFrom(input, "Distance:", " ").trim().split("\\s+")).mapToLong(Long::parseLong).boxed().toList());
            for (int i = 0; i < distances.size(); i++) {
                races.get(i).setRecordDistance(distances.get(i));
            }
        }
    }

    public void afterParse() {
        out(races);
        long allTogether = 1;

        for (Time2Distance race : races) {
            int winsPossibleForRace = calcWinPossibilities(race);
            out(winsPossibleForRace);
            allTogether *= winsPossibleForRace;
        }

        out("Part 1", allTogether);
    }

    private static int calcWinPossibilities(Time2Distance race) {
        int wins = 0;

        long soFarRecord = race.getRecordDistance();
        long raceDuration = race.getRaceDuration();
        for (int i = 1; i < raceDuration; i++) {
            if (i * (raceDuration - i) > soFarRecord)
                wins++;
        }

        return wins;
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
