import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt", 11, 7);
    // runForInput("./input_example_2.txt", 11, 7);
    runForInput("./input.txt", 101, 103);
  }

  @SuppressWarnings("SameParameterValue")
  private static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName, int width, int height) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    // Task2 task = new Task2();
    task.init();

    Instant t1 = Instant.now();

    try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {
      lines.forEach(task::addLine);
    }

    task.afterParse(width, height);

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}