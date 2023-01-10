import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException {
        runForInput();
    }

    private static void runForInput() throws IOException {

        Task task = new Task();
//        Task2 task = new Task2();
        task.init();


        long t1 = System.currentTimeMillis();
        task.afterParse();
        long t2 = System.currentTimeMillis();

//        System.out.println(task);
        System.out.println("Duration: " + (t2 - t1) + "ms");
    }


}
