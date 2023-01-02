import java.util.HashMap;
import java.util.Map;

public class Storage {
    // Storage
    long ore = 0;
    long clay = 0;
    long obsidian = 0;
    long geode = 0;

    long oreRobots = 0;
    long clayRobots = 0;
    long obsidianRobots = 0;
    long geodeRobots = 0;

    public Storage() {
    }

    public Storage(Storage other) {
        this.ore = other.ore;
        this.clay = other.clay;
        this.obsidian = other.obsidian;
        this.geode = other.geode;

        this.oreRobots = other.oreRobots;
        this.clayRobots = other.clayRobots;
        this.obsidianRobots = other.obsidianRobots;
        this.geodeRobots = other.geodeRobots;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Storage{");
        sb.append(" ore=").append(ore);
        sb.append(" clay=").append(clay);
        sb.append(" obsidian=").append(obsidian);
        sb.append(" geode=").append(geode);
        sb.append(" oreRobots=").append(oreRobots);
        sb.append(" clayRobots=").append(clayRobots);
        sb.append(" obsidianRobots=").append(obsidianRobots);
        sb.append(" geodeRobots=").append(geodeRobots);
        sb.append(" }");
//        sb.append("\r\n ore=").append(ore);
//        sb.append("\r\n clay=").append(clay);
//        sb.append("\r\n obsidian=").append(obsidian);
//        sb.append("\r\n geode=").append(geode);
//        sb.append("\r\n oreRobots=").append(oreRobots);
//        sb.append("\r\n clayRobots=").append(clayRobots);
//        sb.append("\r\n obsidianRobots=").append(obsidianRobots);
//        sb.append("\r\n geodeRobots=").append(geodeRobots);
        sb.append("\r\n }");
        return sb.toString();
    }

    Map<String, Integer> howManyRobotsEachCouldBeBuild(Blueprint blueprint) {
        Map<String, Integer> map = new HashMap<>();

        Costs costs;
        costs = blueprint.oreRobot;
        map.put("ore", howManyRobotsCouldBeBuild(costs));
        costs = blueprint.clayRobot;
        map.put("clay", howManyRobotsCouldBeBuild(costs));
        costs = blueprint.obsidianRobot;
        map.put("obsidian", howManyRobotsCouldBeBuild(costs));
        costs = blueprint.geodeRobot;
        map.put("geode", howManyRobotsCouldBeBuild(costs));

        return map;
    }

    private int howManyRobotsCouldBeBuild(Costs costs) {
        int result = 0;
        long test = 1;
        while (true) {
            if (ore >= costs.ore * test && clay >= costs.clay * test && obsidian >= costs.obsidian * test) {
                result++;
                test++;
            } else
                break;
        }
        return result;
    }

    Storage buildRobots(int numRobots, Costs costs) {
        Storage result = new Storage(this);

        result.ore -= numRobots * costs.ore;
        result.clay -= numRobots * costs.clay;
        result.obsidian -= numRobots * costs.obsidian;

        return result;
    }
}
