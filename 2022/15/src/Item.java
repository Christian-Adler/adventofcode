public class Item {
    final String type;// S|B
    final Pos pos;
    Pos closestBeacon = null; // falls Sensor

    public Item(String type, Pos pos) {
        this.type = type; // S | B
        this.pos = pos;
    }

    boolean isSensor() {
        return type.equals("S");
    }

    boolean isBeacon() {
        return type.equals("B");
    }

    @Override
    public String toString() {
        return "Item{" +
                "type=" + type +
                ", pos=" + pos +
                (isSensor() ? ", closestBeacon=" + closestBeacon : "") +
                '}';
    }
}
