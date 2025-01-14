package aoc;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt", 10);
    // runForInput("./input_example_2.txt", 10);
    // runForInput("./input_example_3.txt", 10);
    // runForInput("./input_example_4.txt", 10);
    // runForInput("./input_example_5.txt", 10);
    // runForInput("./input_example_6.txt", 10);
    // runForInput("./input_example_7.txt", 10);
    runForInput("./input.txt", 10007);
  }

  private static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName, int cards) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    // Task2 task = new Task2();
    task.init();

    Instant t1 = Instant.now();

    try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {
      lines.forEach(task::addLine);
    }

    task.afterParse(cards);

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}