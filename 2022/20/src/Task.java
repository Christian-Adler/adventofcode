import java.util.LinkedList;
import java.util.List;

public class Task {
    LinkedList<Pair> list = new LinkedList<>();
    Pair pair0 = null;

    public void init() {
    }

    public void addLine(String input) {
        Pair pair = new Pair(list.size(), Integer.parseInt(input));
        if (pair.number == 0)
            pair0 = pair;
        list.add(pair);
    }

    public void afterParse() {
        int size = list.size();
        int sizeM1 = size - 1;

//        out(list);

        List<Integer> worklist = list.stream().map(p -> p.idx).toList();

        for (Integer worklistIdx : worklist) {
            Pair pair = list.stream().filter(p -> p.idx == worklistIdx).findFirst().orElseThrow();
            long i = pair.number;
            if (i == 0)
                continue;
            int idx = list.indexOf(pair);
            list.remove(idx);
            long insertIdx = (idx + i);
            while (insertIdx < 0)
                insertIdx += sizeM1;
            insertIdx = insertIdx % sizeM1;

//            out(list);

            list.add((int) insertIdx, pair);

//            out(list);
        }

        int idx0 = list.indexOf(pair0);
        int idx1000 = (idx0 + 1000) % size;
        int idx2000 = (idx0 + 2000) % size;
        int idx3000 = (idx0 + 3000) % size;

        long num1000 = list.get(idx1000).number;
        long num2000 = list.get(idx2000).number;
        long num3000 = list.get(idx3000).number;

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
