public class State {
    final String state;
    final Instruction instruction0 = new Instruction();
    final Instruction instruction1 = new Instruction();

    public State(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "\r\nState{" + "state='" + state + '\'' +
                ",\r\n instruction0=" + instruction0 +
                ",\r\n instruction1=" + instruction1 +
                '}';
    }
}
