import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(">>");
        Area area = new Area();
        Area2 area2 = new Area2();

        Files.lines(new File(
                new File("."), "/input1.txt").toPath()).forEach((line) -> {
//                    System.out.println(line);
                    area.add(line);
                    area2.add(line);
                }
        );

//        area.increaseY();

        long t1 = System.currentTimeMillis();
        int maxVY = area.shoot();
        System.out.println("maxVY:" + maxVY);
        area2.shoot(maxVY);

//        System.out.println(area);
        long t2 = System.currentTimeMillis();
        System.out.println((t2 - t1) + "ms");
    }
}
