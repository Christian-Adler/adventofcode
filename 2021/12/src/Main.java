import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
  public static void main(String[] args) throws IOException {
    System.out.println("Hallo");
    CaveSystem caveSystem = new CaveSystem();

    Files.lines(new File("./input.txt").toPath()).forEach((line) -> caveSystem.addCaves(line.split("-")));

    System.out.println(caveSystem);

    caveSystem.findAllPaths();
  }
}
