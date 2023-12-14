import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
    //    runForInput("./input_0_1.txt");
    //    runForInput("./input_0.txt");
    //    runForInput("./input_1.txt");
    runForInput("./input_1_1-300.txt");
  }

  /*
  dauert zu lang:
  12
125
192
141
281
124
243
63
230
  * */

  private static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    List<Future<?>> futures = new ArrayList<>();

    Instant t1 = Instant.now();

    try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {

      lines.forEach(l -> {
        futures.add(executor.submit(() -> {
          Task2 task = new Task2();
          task.addLine(l);
        }));
      });
    }

    for (Future<?> future : futures) {
      while (!future.isDone()) {
        Thread.sleep(100);
      }
    }

    Task2.afterParse();

    executor.shutdownNow();

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}