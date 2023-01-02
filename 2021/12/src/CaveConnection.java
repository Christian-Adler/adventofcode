public class CaveConnection {
    public Cave cave1;
    public Cave cave2;

    public CaveConnection(Cave cave1, Cave cave2) {
        this.cave1 = cave1;
        this.cave2 = cave2;
    }

    @Override
    public String toString() {
        return "CaveConnection{" +
                "cave1=" + cave1 +
                ", cave2=" + cave2 +
                '}';
    }
}
