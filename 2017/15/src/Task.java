public class Task {
    Generator genA;
    Generator genB;
    Generator genAMultOf4;
    Generator genBMultOf8;

    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split("\\s");
        int val = Integer.parseInt(split[4]);
        if (genA == null) {
            genA = new Generator(16807, 0, val);
            genAMultOf4 = new Generator(16807, 4, val);
        } else {
            genB = new Generator(48271, 0, val);
            genBMultOf8 = new Generator(48271, 8, val);
        }

    }

    public void afterParse() {
        long counter = 0;
        for (int i = 0; i < 40000000; i++) {

            long a = genA.genNext();
//            out(a);
//            out(Long.toBinaryString(a));
            long b = genB.genNext();
//            out(b);

            // Vergleiche die rechten 16 bits
            long rightA = a & 0B1111111111111111;
            long rightB = b & 0B1111111111111111;

            if (rightA == rightB)
                counter++;
        }

        out("Part1", counter);

        counter = 0;
        for (int i = 0; i < 5000000; i++) {

            long a = genAMultOf4.genNext();
//            out(a);
//            out(Long.toBinaryString(a));
            long b = genBMultOf8.genNext();
//            out(b);

            // Vergleiche die rechten 16 bits
            long rightA = a & 0B1111111111111111;
            long rightB = b & 0B1111111111111111;

            if (rightA == rightB)
                counter++;
        }
        out("Part2", counter);
    }


    public void out(Object... str) {
        Util.out(str);
    }

}
