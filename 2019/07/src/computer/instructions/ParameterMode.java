package computer.instructions;

public enum ParameterMode {
    POSITION_MODE(0), IMMEDIATE_MODE(1);

    private final int mode;

    ParameterMode(int mode) {
        this.mode = mode;
    }

    public static ParameterMode from(int mode) {
        for (ParameterMode parameterMode : ParameterMode.values()) {
            if (parameterMode.mode == mode) return parameterMode;
        }
        throw new IllegalArgumentException("Found no ParameterMode for " + mode);
    }
}
