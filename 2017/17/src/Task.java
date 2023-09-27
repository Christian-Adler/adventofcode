import java.util.ArrayList;

public class Task {
    int step;
    ArrayList<Integer> buffer = new ArrayList<>();

    public void init() {
        buffer.add(0);
    }

    public void addLine(String input) {
        step = Integer.parseInt(input);
    }

    public void afterParse() {
        int currentIdx = 0;
        for (int i = 0; i < 2017; i++) {
            currentIdx = (currentIdx + step) % buffer.size();
            currentIdx++;
            buffer.add(currentIdx, i + 1);
//        out(buffer);
        }

        int targetIdx = (currentIdx + 1) % buffer.size();
        out("Part1:", buffer.get(targetIdx));

        // Viel zu langsam:
//        currentIdx = 0;
//        buffer.clear();
//        buffer.add(0);
////        out(buffer);
////        for (int i = 0; i < 50000000; i++) {
//        for (int i = 0; i < 500; i++) {
//            if (i % 100000 == 0)
//                out(i);
//            currentIdx = (currentIdx + step) % buffer.size();
//            currentIdx++;
//            buffer.add(currentIdx, i + 1);
//            out(buffer);
//        }
//
//        int idx0 = buffer.indexOf(0);
//        targetIdx = (idx0 + 1) % buffer.size();
//        out("Part2:", buffer.get(targetIdx));

        // Immer nur 0-Index und die zahl hinter 0 merken
        // Die "Liste" waechst immer um 1 - Laenge ist daher immer klar i+1
        currentIdx = 0;
        int idxOf0 = 0;
        int numberAfterIdxOf0 = 0;
        for (int i = 0; i < 50000000; i++) {
            currentIdx = (currentIdx + step) % (i + 1);
            currentIdx++;
            if (currentIdx == idxOf0 + 1)
                numberAfterIdxOf0 = i + 1;
            else if (currentIdx == idxOf0)
                idxOf0++;

//            out(i, numberAfterIdxOf0);
        }
        out("Part2", numberAfterIdxOf0);

    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
