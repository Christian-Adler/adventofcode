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
        task.init();

        Files.lines(new File(inputFileName).toPath()).forEach(task::addLine);

        long t1 = System.currentTimeMillis();
//        task.afterParse(7);
        task.afterParse(12); // Teil 2 Dauert ~ 1300s - besser + Erklärung: https://github.com/tmrd993/advent-of-code-solutions/blob/master/src/main/java/2k16/aoc16/Day23.java
        long t2 = System.currentTimeMillis();

//        System.out.println(task);
        System.out.println("Duration: " + (t2 - t1) + "ms");
    }


}
