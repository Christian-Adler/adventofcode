package aoc;

import aoc.util.Util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class TaskBase {
  protected static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName, Object... params) throws Exception {
    out("\r\nInput: " + inputFileName);
    out();

    List<String> lines = readFile(inputFileName);

    Task task = new Task();

    Instant start = Instant.now();
    task.part1(lines, params);
    Instant end = Instant.now();
    out("Duration:", Duration.between(start, end).toMillis(), "ms");
  }


  private static List<String> readFile(String inputFileName) throws IOException {
    try (Stream<String> stream = Files.lines(Paths.get(inputFileName), StandardCharsets.UTF_8)) {
      return new ArrayList<>(stream.toList());
    }
  }

  protected static void out(Object... str) {
    Util.out(str);
  }
}
