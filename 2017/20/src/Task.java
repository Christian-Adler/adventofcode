import java.util.Arrays;

public class Task {

    long minA = Long.MAX_VALUE;
    int minParticle = -1;
    int particleCount = 0;

    public void init() {
    }

    public void addLine(String input) {
        String[] aSplit = input.split("a=")[1].replace("<", "").replace(">", "").split(",");

        int a = Arrays.stream(aSplit).mapToInt(i -> Math.abs(Integer.parseInt(i))).sum();
        if (a < minA) {
            minA = a;
            minParticle = particleCount;
        }
        particleCount++;
    }

    public void afterParse() {
        out("minA", minA);
        out("minParticle", minParticle);
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
