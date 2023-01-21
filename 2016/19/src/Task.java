public class Task {

    public void init() {
    }

    public void addLine(String input) {
        RingItem start = null;
        RingItem act = null;
        int num = Integer.parseInt(input);
        for (int i = 1; i <= num; i++) {
            if (start == null) {
                start = new RingItem(i);
                act = start;
            } else {
                RingItem ringItem = new RingItem(i);
                ringItem.prev = act;
                act.next = ringItem;
                act = ringItem;
            }
        }

        act.next = start;
        start.prev = act;

        act = start;
        out(act);

        while (act.numPresents != num) {
            act.numPresents += act.next.numPresents;
            act.next.removeFromRing();
            act = act.next;
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
