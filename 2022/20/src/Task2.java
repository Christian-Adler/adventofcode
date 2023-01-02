import java.util.*;

public class Task2 {
    LinkedList<Integer> list = new LinkedList<>();
    Map<Integer, Long> map = new HashMap<>();
    int pair0 = -1;

    long mult = 811589153l;


    public void init() {
    }

    public void addLine(String input) {
        int idx = list.size();
        long number =/* 811589153l */ Integer.parseInt(input);
        map.put(idx, number);
        if (number == 0)
            pair0 = idx;
        list.add(idx);
    }

    public void afterParse() {
        int size = list.size();
        int sizeM1 = size - 1;
        long mul = mult % sizeM1;
//        out(list);

        long t1 = System.currentTimeMillis();

        List<Integer> worklist = new ArrayList<>(list);

        for (int round = 0; round < 10; round++) {

            for (int workIdx : worklist) {
//                out(round + " - " + workIdx);
                if (workIdx % 10 == 0) {
                    long t2 = System.currentTimeMillis();
                    out("round: " + round + " - " + workIdx + "/" + size + " = " + ((double) workIdx / size * 100.0 / 10) + "% " + (t2 - t1) + "ms");
                }
                long i = map.get(workIdx);
                if (i == 0)
                    continue;
                int idx = list.indexOf(workIdx);
                list.remove(idx);
                long insertIdx = (idx + i * mul);
                while (insertIdx < 0) {
                    insertIdx += sizeM1;
                }
                insertIdx = insertIdx % sizeM1;

//            out(list);

                list.add((int) insertIdx, workIdx);

//            out(list);
            }
//            out(list);
        }

        int idx0 = list.indexOf(pair0);
        int idx1000 = (idx0 + 1000) % size;
        int idx2000 = (idx0 + 2000) % size;
        int idx3000 = (idx0 + 3000) % size;

        long num1000 = map.get(list.get(idx1000)) * mult;
        long num2000 = map.get(list.get(idx2000)) * mult;
        long num3000 = map.get(list.get(idx3000)) * mult;

        long sum = num1000 + num2000 + num3000;
        out(sum);
    }

    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            input = input.replace(string, "");
        }
        return input;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
