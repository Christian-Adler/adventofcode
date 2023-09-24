public class Instruction {
    final String register;
    final int add;
    final String conditionRegister;
    final String condition;
    final int conditionValue;
    final Check conditionCheck;

    public Instruction(String input) {
        String[] split = input.split("\\s");
        if (split.length != 7) throw new IllegalArgumentException("Failed to parse input " + input);
        register = split[0];
        int factor = 1;
        if (split[1].equals("dec")) factor = -1;
        add = factor * Integer.parseInt(split[2]);
        conditionRegister = split[4];
        condition = split[5];
        conditionValue = Integer.parseInt(split[6]);
        conditionCheck = CheckFactory.createCheck(conditionRegister, condition);
    }

    public void exec() {
        if (conditionCheck.check(conditionValue)) {
            Registers.add(register, add);
        }
    }

    @Override
    public String toString() {
        return "Instruction{" + "register='" + register + '\'' +
                ", add=" + add +
                ", conditionRegister='" + conditionRegister + '\'' +
                ", condition='" + condition + '\'' +
                ", conditionValue=" + conditionValue +
                '}';
    }
}
