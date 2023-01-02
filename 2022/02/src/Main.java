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

        Game2 game = new Game2();

        Files.lines(new File(inputFileName).toPath()).forEach(game::addLine);

        game.printScore();
    }
}
