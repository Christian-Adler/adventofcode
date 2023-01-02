import java.util.Objects;

public class Cave {
    public final String name;
    public final boolean isBigCave;
    public final boolean isStart;
    public final boolean isEnd;

    public Cave(String input) {
        this(input, Character.isUpperCase(input.charAt(0)));
    }

    public Cave(String name, boolean bigCave) {
        this.name = name;
        this.isBigCave = bigCave;
        this.isStart = name.equalsIgnoreCase("start");
        this.isEnd = name.equalsIgnoreCase("end");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return Objects.equals(name, cave.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
