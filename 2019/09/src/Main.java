import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
//        runForInput("./input_0.txt");
//        runForInput("./input_0_1.txt");
//        runForInput("./input_0_2.txt");
//        runForInput("./input_0_3.txt");
    runForInput("./input_1.txt");
    // runForInput("./input_day17.txt");
  }

  private static void runForInput(String inputFileName) throws IOException, InterruptedException {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    task.init();

    Files.lines(new File(inputFileName).toPath()).forEach(task::addLine);

    long t1 = System.currentTimeMillis();
    if (inputFileName.contains("day17"))
      task.afterParse_Day17();
    else
      task.afterParse();
    long t2 = System.currentTimeMillis();

//        System.out.println(task);
//        System.out.println(task.toStringSVG());		
//        System.out.println(task.toStringConsole());
    System.out.println("Duration: " + (t2 - t1) + "ms");
  }


}
