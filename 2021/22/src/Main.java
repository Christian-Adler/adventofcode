import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(">>");
        long t1 = System.currentTimeMillis();
//        Cuboid initRegion = Cuboid.parse("x=-50..50,y=-50..50,z=-50..50");
//        Cubes cubes = new Cubes(initRegion);
//
//        Files.lines(new File(
//                new File("."), "/input2.txt").toPath()).forEach((line) -> {
////                    System.out.println(line);
//                    cubes.add(line);
//                    System.out.println(cubes);
//                }
//        );

        boolean[][][] bool = new boolean[300000][300000][300000];
        bool[2][1][2000] = true;
        boolean l = bool[123456][0][24];
        System.out.println(l);
//        cubes.filter(initRegion);

                long t2 = System.currentTimeMillis();
        System.out.println((t2 - t1) + "ms");
    }
}
