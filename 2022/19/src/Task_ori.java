import java.util.*;

// https://github.com/pauldraper/advent-of-code-2022/blob/main/problems/day-19/part_1.py

public class Task_ori {

    Map<Blueprint, Long> blueprints2Level = new HashMap<>();

    long maxGeode = 0;

    public void init() {
    }

    public void addLine(String input) {
        String in = cleanFrom(input, "Blueprint ", "Each ore robot costs ", "Each clay robot costs ", "Each obsidian robot costs ", "Each geode robot costs ");
        String[] split = in.split(":");
        int bluePrintId = Integer.parseInt(split[0]);
        String[] splitCosts = split[1].split("\\.");
//        out(splitCosts);
        Blueprint blueprint = new Blueprint(bluePrintId, new Costs(splitCosts[0]), new Costs(splitCosts[1]), new Costs(splitCosts[2]), new Costs(splitCosts[3]));
        blueprints2Level.put(blueprint, 0L);
    }

    public void afterParse() {
        ArrayList<Blueprint> blueprints = new ArrayList<>(blueprints2Level.keySet());
        for (Blueprint blueprint : blueprints) {
            out(blueprint.id);
            long level = calcBlueprintLevel(blueprint);
            blueprints2Level.put(blueprint, level);
            out(blueprint.id + " done: " + level);
        }


    }

    long calcBlueprintLevel(Blueprint blueprint) {
        int timelimit = 24;
        Storage storage = new Storage();
        storage.oreRobots = 1; // initial

        return calcBlueprintLevel(blueprint, timelimit, storage);
    }

    long calcBlueprintLevel(Blueprint blueprint, int timeleft, Storage storage) {

        maxGeode = Math.max(maxGeode, storage.geode);


        if (maxGeode > 0) {
            if (storage.geode < maxGeode / 2 && timeleft < 6)
                return storage.geode;
            long checkRobots = storage.geodeRobots + timeleft / 2;
            if (maxGeode > checkRobots * timeleft)
                return storage.geode;
        }

        if (timeleft <= 0) {
            return storage.geode;
        }

        // Bauen innerhalb der Minute mit vorhandenen Robottern
        long plus_ore = storage.oreRobots;
        long plus_clay = storage.clayRobots;
        long plus_obsidian = storage.obsidianRobots;
        long plus_geode = storage.geodeRobots;


        // Alle permutationen von moeglichen Robotern bauen und damit weiter testen
        Set<BuildRobots> possibleBuildSteps = new HashSet<>();

        Map<String, Integer> maxRobots = storage.howManyRobotsEachCouldBeBuild(blueprint);

        // In den letzten Schritten keine unwichtigen Roboter mehr
        if (timeleft <= 5 || maxRobots.get("geode") > 0 || maxRobots.get("obsidian") > 0) {
            maxRobots.put("ore", 0);
            maxRobots.put("clay", 0);
        }

//        for (int geodeRobots = maxRobots.get("geode"); geodeRobots >= 0; geodeRobots--) {
//            Storage s4 = storage.buildRobots(geodeRobots, blueprint.geodeRobot);
        int geodeRobots = maxRobots.get("geode");
        Storage s4 = storage.buildRobots(geodeRobots, blueprint.geodeRobot); // GeodenRoboter immer! bauen wenn moeglich
        s4.geodeRobots += geodeRobots;
        for (int obsidianRobots = maxRobots.get("obsidian"); obsidianRobots >= 0; obsidianRobots--) {
            Storage s3 = s4.buildRobots(obsidianRobots, blueprint.obsidianRobot);
            s3.obsidianRobots += obsidianRobots;
            for (int clayRobots = maxRobots.get("clay"); clayRobots >= 0; clayRobots--) {
                Storage s2 = s3.buildRobots(clayRobots, blueprint.clayRobot);
                s2.clayRobots += clayRobots;
                for (int oreRobots = maxRobots.get("ore"); oreRobots >= 0; oreRobots--) {
                    Storage s1 = s2.buildRobots(oreRobots, blueprint.oreRobot);
                    s1.oreRobots += oreRobots;

                    possibleBuildSteps.add(new BuildRobots(oreRobots, clayRobots, obsidianRobots, geodeRobots, s1));
                }
            }
        }
//        }


        long maxResult = 0;

        for (BuildRobots possibleBuildStep : possibleBuildSteps) {

            // in der 1 Minute wird von den bereits vorhandenen Robotern ja auch Zeug erzeugt
            Storage storageAfter1Minute = new Storage(possibleBuildStep.storage);
            storageAfter1Minute.ore += plus_ore;
            storageAfter1Minute.clay += plus_clay;
            storageAfter1Minute.obsidian += plus_obsidian;
            storageAfter1Minute.geode += plus_geode;

            long tmpResult = calcBlueprintLevel(blueprint, timeleft - 1, storageAfter1Minute);
            if (tmpResult > maxResult)
                maxResult = tmpResult;
        }

        return maxResult;
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

//        builder.append(blueprints);

        return builder.toString();
    }

    public String toStringSVG() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
