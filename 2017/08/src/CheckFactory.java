public class CheckFactory {
    public static Check createCheck(String checkRegister, String condition) {
        if (condition.equals("=="))
            return (int val) -> Registers.get(checkRegister) == val;
        if (condition.equals("!="))
            return (int val) -> Registers.get(checkRegister) != val;
        if (condition.equals(">"))
            return (int val) -> Registers.get(checkRegister) > val;
        if (condition.equals(">="))
            return (int val) -> Registers.get(checkRegister) >= val;
        if (condition.equals("<"))
            return (int val) -> Registers.get(checkRegister) < val;
        if (condition.equals("<="))
            return (int val) -> Registers.get(checkRegister) <= val;
        throw new IllegalArgumentException("Invalid condition " + condition);
    }
}

