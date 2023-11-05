package computer;

public class Variable {
    public int value = 0;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Variable{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
