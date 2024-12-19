package aoc;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
    runForInput("./input_example_1.txt", 6, 6, 12);
    runForInput("./input.txt", 70, 70, 1024);
  }

  @SuppressWarnings("SameParameterValue")
  private static void runForInput(String inputFileName, int width, int height, int fallingBytes) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    // Task2 task = new Task2();
    task.init(fallingBytes, width, height);

    Instant t1 = Instant.now();

    try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {
      lines.forEach(task::addLine);
    }

    task.afterParse();

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}