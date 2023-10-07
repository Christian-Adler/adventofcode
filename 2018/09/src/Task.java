import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Task {
    List<Integer> circle = new LinkedList<>();
    int players = 0;
    int lastMarble = 0;

    public void init() {
    }

    public void addLine(String input) {
        String[] split = Util.cleanFrom(input, " players", " last marble is worth ", " points").trim().split(";");
        players = Integer.parseInt(split[0]);
        lastMarble = Integer.parseInt(split[1]);
        circle = new LinkedList<>();
        marbleRun();
    }

    public void afterParse() {
    }

    private void marbleRun() {
        Map<Integer, Integer> player2Score = new HashMap<>();
        for (int i = 0; i < players; i++) {
            player2Score.put(i, 0);
        }

        circle.add(0);
        int actPlayer = 0;
        int currentIdx = 0;

        for (int marble = 1; marble <= lastMarble; marble++) {
            if (marble % 10000 == 0) out(marble + " of " + lastMarble);
            actPlayer = (marble - 1) % players;

            if (marble % 23 == 0) {
                int removeIdx = (currentIdx + circle.size() - 7) % circle.size();
                Integer val;
//                if (removeIdx == 0)
//                    val = circle.remove(circle.size() - 1);
//                else
                val = circle.remove(removeIdx);
//                out(val);

                player2Score.put(actPlayer, player2Score.get(actPlayer) + marble + val);

                currentIdx = removeIdx;

//                out(player2Score);
            } else {

                int insertIdx = (currentIdx + 2) % circle.size();
                if (insertIdx == 0) {
                    circle.add(marble);
                    currentIdx = circle.size() - 1;
                } else {
                    circle.add(insertIdx, marble);
                    currentIdx = insertIdx;
                }
//                currentIdx = circle.indexOf(marble);
            }

//            out("currentIdx", currentIdx);
//            out(circle);
        }

//        out(player2Score);
        Integer maxScore = player2Score.values().stream().max(Integer::compareTo).orElse(-1);
        out("maxScore", maxScore);
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
