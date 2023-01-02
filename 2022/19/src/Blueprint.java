import java.util.Objects;

public class Blueprint {
    int id = 0;
    Costs oreRobot = null;
    Costs clayRobot = null;
    Costs obsidianRobot = null;
    Costs geodeRobot = null;

    public Blueprint(int id, Costs oreRobot, Costs clayRobot, Costs obsidianRobot, Costs geodeRobot) {
        this.id = id;
        this.oreRobot = oreRobot;
        this.clayRobot = clayRobot;
        this.obsidianRobot = obsidianRobot;
        this.geodeRobot = geodeRobot;
    }

    @Override
    public String toString() {
        return "Blueprint{" +
                "id=" + id +
                ", oreRobot=" + oreRobot +
                ", clayRobot=" + clayRobot +
                ", obsidianRobot=" + obsidianRobot +
                ", geodeRobot=" + geodeRobot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blueprint blueprint = (Blueprint) o;
        return id == blueprint.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
