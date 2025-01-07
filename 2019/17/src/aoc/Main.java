package aoc;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt");
    runForInput("./input_example_2.txt");
    runForInput(null);
  }

  private static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    task.init();

    Instant t1 = Instant.now();

    if (inputFileName != null) {
      try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {
        lines.forEach(task::addLine);
      }
    }

    String intCode = Files.readString(new File("./input.txt").toPath(), StandardCharsets.UTF_8);
    task.afterParse(intCode);

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}