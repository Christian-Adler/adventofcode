import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        runForInput1(9);
//        runForInput1(5);
//        runForInput1(18);
//        runForInput1(2018);
//        runForInput1(760221);
        runForInput2("01245");
        runForInput2("51589");
        runForInput2("92510");
        runForInput2("59414");
        runForInput2("760221");
    }

    private static void runForInput1(int input) throws IOException {
        System.out.println("\r\nInput: " + input);

        Task task = new Task();
//        Task2 task = new Task2();
        task.init(input);


        long t1 = System.currentTimeMillis();
        task.afterParse();
        long t2 = System.currentTimeMillis();

//        System.out.println(task);
//        System.out.println(task.toStringSVG());		
//        System.out.println(task.toStringConsole());
        System.out.println("Duration: " + (t2 - t1) + "ms");
    }

    private static void runForInput2(String input) throws IOException {
        System.out.println("\r\nInput: " + input);

        Task2 task = new Task2();
        task.init(input);


        long t1 = System.currentTimeMillis();
        task.afterParse();
        long t2 = System.currentTimeMillis();

//        System.out.println(task);
//        System.out.println(task.toStringSVG());
//        System.out.println(task.toStringConsole());
        System.out.println("Duration: " + (t2 - t1) + "ms");
    }


}
