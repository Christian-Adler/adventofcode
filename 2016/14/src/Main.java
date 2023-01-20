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

        long t1 = System.currentTimeMillis();

        Task task = new Task();

        Files.lines(new File(inputFileName).toPath()).forEach(l -> task.addLine(l, true));

        long t2 = System.currentTimeMillis();

        System.out.println("Duration: " + (t2 - t1) + "ms");
    }


}
