import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException {
//        runForInput("./input_0.txt");
        runForInput("./input_1.txt");
    }

    private static void runForInput(String inputFileName) throws IOException {
        System.out.println("\r\nInput: " + inputFileName);

//        Task task = new Task();
        Task2 task = new Task2();
        task.init();

        long t1 = System.currentTimeMillis();
        Files.lines(new File(inputFileName).toPath()).forEach(task::addLine);

        task.afterParse();
        long t2 = System.currentTimeMillis();

        System.out.println("Duration: " + (t2 - t1) + "ms");
    }


}
