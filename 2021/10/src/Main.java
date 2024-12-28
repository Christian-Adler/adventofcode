import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
  public static void main(String[] args) throws IOException {
    System.out.println("Hallo");
    Lines lines = new Lines();

    Files.lines(new File("./input1.txt").toPath()).forEach(lines::add);

    System.out.println(lines);
    System.out.println("ErrScore: " + lines.errScore());
    System.out.println("CompleteScore: " + lines.completeScore());

  }
}
