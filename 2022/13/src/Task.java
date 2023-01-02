import java.util.Iterator;
import java.util.StringTokenizer;

public class Task {

    Item packet1 = null;
    Item packet2 = null;

    int rightOrderIdxSum = 0;
    int packetCounter = 0;

    public void init() {
    }

    public void addLine(String input) {
        if (input.trim().isEmpty()) {
            out("");
            packet1 = null;
            packet2 = null;
            return;
        }
        StringTokenizer tokenizer = new StringTokenizer(input, "[,]", true);
        Item actListItem = null;
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
//           out(token);
            if (token.equals("[")) {
                Item listItem = new Item();
                if (actListItem != null)
                    actListItem.add(listItem);
                actListItem = listItem;
            } else if (token.equals("]")) {
                if (actListItem.parent != null)
                    actListItem = actListItem.parent;
            } else if (token.equals(",")) {/**/} else {
                int value = Integer.parseInt(token);
                actListItem.add(new Item(value));
            }
        }

        out(actListItem);

        if (packet1 == null) packet1 = actListItem;
        else packet2 = actListItem;

        if (packet2 == null) return;
        packetCounter++;

        int compared = compareItems(packet1, packet2);
        out(compared);
        if (compared < 0)
            rightOrderIdxSum += packetCounter;
    }

    int compareItems(Item left, Item right) {
        if (left.isInt() && right.isInt())
            return Integer.compare(left.value, right.value);
        if (left.isList() && right.isList()) {
            Iterator<Item> leftIt = left.list.iterator();
            Iterator<Item> rightIt = right.list.iterator();

            int res = 0;
            while (res == 0) {
                if (leftIt.hasNext() && rightIt.hasNext()) {
                    res = compareItems(leftIt.next(), rightIt.next());
                } else if (!leftIt.hasNext() && rightIt.hasNext()) {
                    res = -1;
                } else if (leftIt.hasNext() && !rightIt.hasNext()) {
                    res = 1;
                } else break;
            }
            return res;
        }

        if (left.isInt() && right.isList()) {
            Item tmp = new Item();
            tmp.add(new Item(left.value));

            return compareItems(tmp, right);
        }
        if (left.isList() && right.isInt()) {
            Item tmp = new Item();
            tmp.add(new Item(right.value));

            return compareItems(left, tmp);
        }

        return 0;
    }

    public void afterParse() {
        out("rightOrderCounter" + rightOrderIdxSum);
    }


    public void out(Object str) {
        System.out.println(str);
    }

    public void ou(Object str) {
        System.out.print(str);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
