// https://github.com/pauldraper/advent-of-code-2022/blob/main/problems/day-19/part_2.py

public class Task2 {

    long product = 1;
    long ore_ore, cla_ore, obs_ore, obs_cla, geo_ore, geo_obs;

    public void init() {
    }

    public void addLine(String input) {
        String in = cleanFrom(input, "Blueprint ", "Each ore robot costs ", "Each clay robot costs ", "Each obsidian robot costs ", "Each geode robot costs ");
        String[] split = in.split(":");
        int bluePrintId = Integer.parseInt(split[0]);
        String[] splitCosts = split[1].split("\\.");
//        out(splitCosts);

        Costs costs = new Costs(splitCosts[0]);
        ore_ore = costs.ore;
        costs = new Costs(splitCosts[1]);
        cla_ore = costs.ore;
        costs = new Costs(splitCosts[2]);
        obs_ore = costs.ore;
        obs_cla = costs.clay;
        costs = new Costs(splitCosts[3]);
        geo_ore = costs.ore;
        geo_obs = costs.obsidian;

        product *= bluePrintId * best(0, 0, 0, 1, 0, 0, 24);
    }

    long best(long ore, long cla, long obs, long ore_rob, long cla_rob, long obs_rob, int time) {
        if (time == 0)
            return 0;

        if (geo_ore <= ore && geo_obs <= obs)
            return (time - 1) + best(
                    ore + ore_rob - geo_ore,
                    cla + cla_rob,
                    obs + obs_rob - geo_obs,
                    ore_rob,
                    cla_rob,
                    obs_rob,
                    time - 1
            );
        long result = 0;

        if (ore_ore <= ore)
            result = Math.max(
                    result,
                    best(
                            ore + ore_rob - ore_ore,
                            cla + cla_rob,
                            obs + obs_rob,
                            ore_rob + 1,
                            cla_rob,
                            obs_rob,
                            time - 1
                    )
            );
        if (cla_ore <= ore)
            result = Math.max(
                    result,
                    best(
                            ore + ore_rob - cla_ore,
                            cla + cla_rob,
                            obs + obs_rob,
                            ore_rob,
                            cla_rob + 1,
                            obs_rob,
                            time - 1
                    )
            );
        if (obs_ore <= ore && obs_cla <= cla)
            result = Math.max(
                    result,
                    best(
                            ore + ore_rob - obs_ore,
                            cla + cla_rob - obs_cla,
                            obs + obs_rob,
                            ore_rob,
                            cla_rob,
                            obs_rob + 1,
                            time - 1
                    )
            );
        if (ore + ore_rob - ore_ore < Math.max(cla_ore, Math.max(obs_ore, geo_ore)) && ore < ore_ore)
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
        out(product);
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
