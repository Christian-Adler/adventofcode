public class Instruction {
    boolean writeValue = false;
    int move = 0;
    String nextState = "A";

    @Override
    public String toString() {
        return "Instruction{" + "writeValue=" + writeValue +
                ", move=" + move +
                ", nextState='" + nextState + '\'' +
                '}';
    }
}
