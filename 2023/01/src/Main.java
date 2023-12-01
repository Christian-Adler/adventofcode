import java.io.File;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws Exception {
//        runForInput("./input_0.txt");
//        runForInput("./input_0_2.txt");
        runForInput("./input_1.txt");
    }

    private static void runForInput(String inputFileName) throws Exception {
        System.out.println("\r\nInput: " + inputFileName);

        Task task = new Task();
//        Task2 task = new Task2();
        task.init();

        Files.lines(new File(inputFileName).toPath()).forEach(task::addLine);

        long t1 = System.currentTimeMillis();
        task.afterParse();
        long t2 = System.currentTimeMillis();

//        System.out.println(task);
//        System.out.println(task.toStringSVG());		
//        System.out.println(task.toStringConsole());
        System.out.println("Duration: " + (t2 - t1) + "ms");
    }


}
