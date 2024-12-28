import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
  public static void main(String[] args) throws IOException {
    System.out.println("Hallo");
    Transparent transparent = new Transparent();

    Files.lines(new File("./input1.txt").toPath()).forEach((line) -> {
          if (line.contains(","))
            transparent.add(new Pos(line));
          else if (line.startsWith("fold along")) {
            System.out.println("\r\nBefore fold");
//                        System.out.println(transparent);
            System.out.println("Size: " + transparent.size());
            transparent.fold(line);
            System.out.println("\r\nAfter fold");
//                        System.out.println(transparent);
            System.out.println("Size: " + transparent.size());
            System.out.println("\r\n");
          }
        }

    );

    System.out.println(transparent);
    System.out.println("Size: " + transparent.size());
// CEJKLUGJ
  }
}
