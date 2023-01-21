import java.util.ArrayList;

public class Task2 {

    public void init() {
    }

    public void addLine(String input) {
        RingItem start = null;
        RingItem act = null;
        ArrayList<RingItem> list = new ArrayList<>();
        int num = Integer.parseInt(input);
        for (int i = 1; i <= num; i++) {
            if (start == null) {
                start = new RingItem(i);
                act = start;
                list.add(start);
            } else {
                RingItem ringItem = new RingItem(i);
                ringItem.prev = act;
                act.next = ringItem;
                act = ringItem;
                list.add(act);
            }
        }

        if (act == null) throw new IllegalStateException("act = null");

        act.next = start;
        start.prev = act;

        act = start;
        out(act);
        int actRingSize = num;
        boolean odd = actRingSize % 2 != 0;

        while (act.numPresents != num) {
            if (actRingSize % 1000 == 0) out(actRingSize);
            int stealFromSteps;
            if (odd)
                stealFromSteps = (actRingSize - 1) / 2;
            else
                stealFromSteps = (actRingSize) / 2;

            int idx = list.indexOf(act);
            int stealFromIdx = (idx + stealFromSteps) % actRingSize;

            RingItem stealFrom = list.remove(stealFromIdx);

            act.numPresents += stealFrom.numPresents;
            stealFrom.removeFromRing();
            actRingSize--;
            act = act.next;
            odd = !odd;
        }

        out("fin:", act);
    }

    public void afterParse() {
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
