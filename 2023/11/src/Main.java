import java.io.File;
import java.nio.file.Files;

public class Main {
  public static void main(String[] args) throws Exception {
//    runForInput("./input_0.txt");
    runForInput("./input_1.txt");
  }

  private static void runForInput(String inputFileName) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    task.init();

    Files.lines(new File(inputFileName).toPath()).forEach(task::addLine);

    long t1 = System.currentTimeMillis();
    // Part 1
//    task.afterParse(1);
//    task.afterParse(10-1);
//    task.afterParse(100 - 1);
    task.afterParse(1000000 - 1);

    long t2 = System.currentTimeMillis();

//        System.out.println(task);
//        System.out.println(task.toStringSVG());		
//        System.out.println(task.toStringConsole());
    System.out.println("Duration: " + (t2 - t1) + "ms");
  }


}
