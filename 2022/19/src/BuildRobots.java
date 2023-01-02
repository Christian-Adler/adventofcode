import java.util.Objects;

public class BuildRobots {
    long oreRobots = 0;
    long clayRobots = 0;
    long obsidianRobots = 0;
    long geodeRobots = 0;

    Storage storage = null;

    public BuildRobots(long oreRobots, long clayRobots, long obsidianRobots, long geodeRobots, Storage storage) {
        this.oreRobots = oreRobots;
        this.clayRobots = clayRobots;
        this.obsidianRobots = obsidianRobots;
        this.geodeRobots = geodeRobots;
        this.storage = storage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BuildRobots{");
        sb.append("oreRobots=").append(oreRobots);
        sb.append(", clayRobots=").append(clayRobots);
        sb.append(", obsidianRobots=").append(obsidianRobots);
        sb.append(", geodeRobots=").append(geodeRobots);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildRobots that = (BuildRobots) o;
        return oreRobots == that.oreRobots && clayRobots == that.clayRobots && obsidianRobots == that.obsidianRobots && geodeRobots == that.geodeRobots;
    }

    @Override
    public int hashCode() {
        return Objects.hash(oreRobots, clayRobots, obsidianRobots, geodeRobots);
    }
}
