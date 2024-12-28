import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
  public static void main(String[] args) throws IOException {
    System.out.println("Hallo");
    Cave cave = new Cave();

    AtomicInteger lineNo = new AtomicInteger();
    Files.lines(new File("./input.txt").toPath()).forEach((line) ->
    {
      String[] split = line.split("");
      for (int i = 0; i < split.length; i++) {
        int e = Integer.parseInt(split[i], 10);
        Position pos = new Position(i, lineNo.get());
        Octo octo = new Octo(e, pos);
        cave.setOcto(octo);
      }

      lineNo.getAndIncrement();
    });

    System.out.println(cave);

//        for (int i = 1; i < 200; i++) {
//            if (cave.step())
//                System.out.println("All flashed at step: " + i);
//        }

    int step = 1;
    while (!cave.step()) {
      step++;
    }
    System.out.println("All flashed at step: " + step);


    System.out.println(cave);
    System.out.println("Flashes: " + Octo.flashes);


  }
}
