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

        Task task = new Task();
//        task.init(0);
        task.init(1); // 120 & 226 ist zu klein 313 ist zu hoch

        Files.lines(new File(inputFileName).toPath()).forEach(task::addLine);

        long t1 = System.currentTimeMillis();
        task.afterParse();
        long t2 = System.currentTimeMillis();

//        System.out.println(task);
        System.out.println("Duration: " + (t2 - t1) + "ms");
    }


}
