public class ValveAction {
    Valve valve = null;
    boolean open = false;

    public ValveAction(Valve valve) {
        this.valve = valve;
    }

    public ValveAction(Valve valve, boolean open) {
        this(valve);
        this.open = open;
    }

    @Override
    public String toString() {
        return "ValveAction{" +
                "valve=" + valve +
                ", open=" + open +
                '}';
    }
}
