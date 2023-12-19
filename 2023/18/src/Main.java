import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
    runForInput("./input_0.txt");
//    runForInput("./input_0_1.txt");
//    runForInput("./input_0_2.txt");
//    runForInput("./input_0_3.txt");
//    runForInput("./input_0_4.txt");
//    runForInput("./input_0_5.txt");
    runForInput("./input_1.txt");
  }

  private static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    task.init();
    Task2 task2 = new Task2();
    task2.init();

    Instant t1 = Instant.now();

    try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {
      lines.forEach(s -> {
        task.addLine(s);
//        task2.addLine(s, true);
        task2.addLine(s, false);
      });
    }

    task.afterParse();
    task2.afterParse();

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}