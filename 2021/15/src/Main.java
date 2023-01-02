import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hallo");
        Cave cave = new Cave();

        Files.lines(new File(
                new File("."), "/input1.txt").toPath()).forEach((line) -> {
//                    System.out.println(line);
                    cave.add(line
                    );
                }
        );

        cave.increaseY();
//        System.out.println(cave);

        long t1 = System.currentTimeMillis();
        cave.calcLowestRiskPath();
        long t2 = System.currentTimeMillis();
        System.out.println((t2 - t1) + "ms");
    }
}
