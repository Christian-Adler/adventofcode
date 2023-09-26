public class Generator {
    final long factor;
    final long multOf;
    long previousValue = 0;

    public Generator(long factor, long multOf, long initValue) {
        this.factor = factor;
        this.multOf = multOf;
        previousValue = initValue;
    }

    public long genNext() {
        if (multOf > 0) {
            previousValue = (previousValue * factor) % 2147483647L;
            while (previousValue % multOf != 0)
                previousValue = (previousValue * factor) % 2147483647L;
        } else
            previousValue = (previousValue * factor) % 2147483647L;
        return previousValue;
    }
}
