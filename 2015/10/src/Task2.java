import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Task2 {

    public void init() {
    }

    public void addLine(String input) {
        ArrayList<Sequence> sequences = new ArrayList<>();

        ArrayList<String> list = Util.str2List(input);
//        out(list);
        Sequence actSequence = null;
        for (int i = 0; i < list.size(); i++) {
            String c = list.get(i);
            if (i == 0) {
                actSequence = new Sequence(1, Integer.parseInt(c));
                sequences.add(actSequence);
                continue;
            }
            if (String.valueOf(actSequence.of).equals(c)) {
                actSequence.num++;
                continue;
            }
            actSequence = new Sequence(1, Integer.parseInt(c));
            sequences.add(actSequence);
        }

        out(sequences);


        for (int i = 0; i < 50; i++) {
            out(i);
            List<Sequence> workList = new ArrayList<>(sequences);

            sequences.clear();
            for (Sequence sequence : workList) {
                sequences.add(new Sequence(1, sequence.num));
                sequences.add(new Sequence(1, sequence.of));
            }

            // squeeze - gleiche zusammen fassen
            Sequence prev = null;
            Iterator<Sequence> it = sequences.iterator();
            while (it.hasNext()) {
                if (prev == null)
                    prev = it.next();
                else {
                    Sequence act = it.next();
                    if (prev.of == act.of) {
                        prev.num += act.num;
                        it.remove();
                    } else
                        prev = act;
                }
            }

//            out(sequences);
        }

        int length = 0;
        for (Sequence sequence : sequences) {
            length += sequence.num;
        }
        out(length);
    }

    public void afterParse() {
    }

    public void out(Object... str) {
        String out = "";
        for (Object o : str) {
            if (out.length() > 0)
                out += " ";
            out += o;
        }
        System.out.println(out);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            result = result.replace(string, "");
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
