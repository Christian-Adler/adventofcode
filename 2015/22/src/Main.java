import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        Task task = new Task();
        Task2 task = new Task2();
        task.init();

        long t1 = System.currentTimeMillis();
        task.afterParse();
        long t2 = System.currentTimeMillis();

//        System.out.println(task);
        System.out.println("Duration: " + (t2 - t1) + "ms");
    }


}
