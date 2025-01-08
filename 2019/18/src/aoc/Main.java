package aoc;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
    // runForInput("./input_example_1.txt",new Task());
    // runForInput("./input_example_2.txt",new Task());
    // runForInput("./input_example_3.txt",new Task());
    // runForInput("./input_example_4.txt",new Task());
    // runForInput("./input_example_5.txt",new Task());
    // runForInput("./input.txt", new Task());

    // runForInput("./input_example_6.txt", new Task2());
    // runForInput("./input_example_7.txt", new Task2());
    // runForInput("./input_example_8.txt", new Task2());
    // runForInput("./input_example_9.txt", new Task2());
    runForInput("./input2.txt", new Task2());
  }

  private static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName, Task task) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    task.init();

    Instant t1 = Instant.now();

    try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {
      lines.forEach(task::addLine);
    }

    task.afterParse();

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}