import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
//    public static void main(String[] args) throws IOException {
//        runForInput("./input_example.txt", 10);
////        runForInput("./input1.txt", 2000000);
//    }
//
//    private static void runForInput(String inputFileName, int yToCount) throws IOException {
//        System.out.println("\r\nInput: " + inputFileName);
//
//        Task task = new Task();
//        task.init();
//
//        Files.lines(new File(inputFileName).toPath()).forEach(task::addLine);
//
//        task.afterParse(yToCount);
//
////        System.out.println(task);
////        System.out.println(task.toStringSVG());
//    }


    public static void main(String[] args) throws IOException {
//        runForInput("./input_example.txt", 0, 20);
        runForInput("./input1.txt", 0, 4000000);
    }

    private static void runForInput(String inputFileName, int limitMin, int limitMax) throws IOException {
        System.out.println("\r\nInput: " + inputFileName);

        Task2 task = new Task2();
        task.init();

        Files.lines(new File(inputFileName).toPath()).forEach(task::addLine);

        task.afterParse(limitMin, limitMax);

//        System.out.println(task);
//        System.out.println(task.toStringSVG());
    }
}
