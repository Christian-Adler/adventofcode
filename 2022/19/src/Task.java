// https://github.com/pauldraper/advent-of-code-2022/blob/main/problems/day-19/part_1.py

public class Task {

    long total = 0;
    long oreRobCosts_ore, clayRobCosts_ore, obsRobCosts_ore, obsRobCosts_clay, geoRobCosts_ore, geoRobCosts_obs;

    public void init() {
    }

    public void addLine(String input) {
        String in = cleanFrom(input, "Blueprint ", "Each ore robot costs ", "Each clay robot costs ", "Each obsidian robot costs ", "Each geode robot costs ");
        String[] split = in.split(":");
        int bluePrintId = Integer.parseInt(split[0]);
        String[] splitCosts = split[1].split("\\.");
//        out(splitCosts);

        Costs costs = new Costs(splitCosts[0]);
        oreRobCosts_ore = costs.ore;
        costs = new Costs(splitCosts[1]);
        clayRobCosts_ore = costs.ore;
        costs = new Costs(splitCosts[2]);
        obsRobCosts_ore = costs.ore;
        obsRobCosts_clay = costs.clay;
        costs = new Costs(splitCosts[3]);
        geoRobCosts_ore = costs.ore;
        geoRobCosts_obs = costs.obsidian;

        total += bluePrintId * best(0, 0, 0, 1, 0, 0, 24);
    }

    long best(long ore, long cla, long obs, long ore_rob, long cla_rob, long obs_rob, int time) {
        if (time == 0)
            return 0;

        if (geoRobCosts_ore <= ore && geoRobCosts_obs <= obs)
            return (time - 1) + best(
                    ore + ore_rob - geoRobCosts_ore,
                    cla + cla_rob,
                    obs + obs_rob - geoRobCosts_obs,
                    ore_rob,
                    cla_rob,
                    obs_rob,
                    time - 1
            );
        long result = 0;

        if (oreRobCosts_ore <= ore)
            result = Math.max(
                    result,
                    best(
                            ore + ore_rob - oreRobCosts_ore,
                            cla + cla_rob,
                            obs + obs_rob,
                            ore_rob + 1,
                            cla_rob,
                            obs_rob,
                            time - 1
                    )
            );
        if (clayRobCosts_ore <= ore)
            result = Math.max(
                    result,
                    best(
                            ore + ore_rob - clayRobCosts_ore,
                            cla + cla_rob,
                            obs + obs_rob,
                            ore_rob,
                            cla_rob + 1,
                            obs_rob,
                            time - 1
                    )
            );
        if (obsRobCosts_ore <= ore && obsRobCosts_clay <= cla)
            result = Math.max(
                    result,
                    best(
                            ore + ore_rob - obsRobCosts_ore,
                            cla + cla_rob - obsRobCosts_clay,
                            obs + obs_rob,
                            ore_rob,
                            cla_rob,
                            obs_rob + 1,
                            time - 1
                    )
            );
        if (ore + ore_rob - oreRobCosts_ore < Math.max(clayRobCosts_ore, Math.max(obsRobCosts_ore, geoRobCosts_ore)) && ore < oreRobCosts_ore)
            result = Math.max(
                    result,
                    best(
                            ore + ore_rob,
                            cla + cla_rob,
                            obs + obs_rob,
                            ore_rob,
                            cla_rob,
                            obs_rob,
                            time - 1
                    )
            );
        return result;

    }

    public void afterParse() {
        out(total);
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
