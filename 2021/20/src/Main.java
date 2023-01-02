import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(">>");
        Algo alog = new Algo();

        Files.lines(new File(
                new File("."), "/input.txt").toPath()).forEach((line) -> {
//                    System.out.println(line);
                    alog.add(line);
                }
        );


        long t1 = System.currentTimeMillis();
        alog.printImage();

        alog.enhance1();

        alog.printImageEH1();

        alog.enhance2();
        alog.printImageEH2();

        long t2 = System.currentTimeMillis();
        System.out.println((t2 - t1) + "ms");
    }
}
