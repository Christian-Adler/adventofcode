import java.util.HashMap;
import java.util.Map;

public class Task2 {
    DoublyLinkedList circle = new DoublyLinkedList();
    int players = 0;
    int lastMarble = 0;

    // TODO double linked circular list mit pointer auf act https://www.softwaretestinghelp.com/doubly-linked-list-in-java/

    public void init() {
    }

    public void addLine(String input) {
        String[] split = Util.cleanFrom(input, " players", " last marble is worth ", " points").trim().split(";");
        players = Integer.parseInt(split[0]);
        lastMarble = Integer.parseInt(split[1]);
        circle = new DoublyLinkedList();
        marbleRun();
    }

    public void afterParse() {
    }

    private void marbleRun() {
        Map<Integer, Long> player2Score = new HashMap<>();
        for (int i = 0; i < players; i++) {
            player2Score.put(i, 0L);
        }

        circle.addRelativeAfter(0, 0);

        for (int marble = 1; marble <= lastMarble; marble++) {
//            if (marble % 10000 == 0) out(marble + " of " + lastMarble);

            if (marble % 23 == 0) {
                long val = circle.removeRelative(-7);
//                out(val);

                int actPlayer = (marble - 1) % players;
                player2Score.put(actPlayer, player2Score.get(actPlayer) + marble + val);
//                out(player2Score);
            } else {
                circle.addRelativeAfter(marble, 1);
            }

//            out(circle);
        }

//        out(player2Score);
        long maxScore = player2Score.values().stream().max(Long::compareTo).orElse(-1L);
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
