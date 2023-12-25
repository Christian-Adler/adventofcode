import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
//    runForInput("./input_0.txt", new Range(7, 27), new Range(7, 27));
    runForInput("./input_1.txt", new Range(200000000000000L, 400000000000000L), new Range(200000000000000L, 400000000000000L));
  }

  private static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName, Range xRange, Range yRange) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    // Task2 task = new Task2();
    task.init();

    Instant t1 = Instant.now();

    try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {
      lines.forEach(task::addLine);
    }

    task.afterParse(xRange, yRange);

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}