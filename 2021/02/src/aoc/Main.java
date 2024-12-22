package aoc;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
    runForInput("./input_example_1.txt");
    runForInput("./input.txt");
  }

  private static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    Task2 task2 = new Task2();

    Instant t1 = Instant.now();

    try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {
      lines.forEach(l -> {
        task.addLine(l);
        task2.addLine(l);
      });
    }

    task.afterParse();
    task2.afterParse();

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}