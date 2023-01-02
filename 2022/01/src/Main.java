import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException {
        runForInput("./input_example.txt");
        runForInput("./input1.txt");
    }

    private static void runForInput(String inputFileName) throws IOException {
        System.out.println("\r\nInput: " + inputFileName);

        Elfes elfes = new Elfes();

        Files.lines(new File(inputFileName).toPath()).forEach(elfes::addLine);

        System.out.println(elfes.getTheMostCalories());
        System.out.println(elfes.getTheThreeTopMostCalories());
    }
}
