import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Task2 {

    ArrayList<Item> packets = new ArrayList<>();

    public void init() {
        addLine("[[2]]");
        addLine("[[6]]");
    }

    public void addLine(String input) {
        if (input.trim().isEmpty())
            return;

        Item actListItem = parseItem(input);
        packets.add(actListItem);
    }

    private Item parseItem(String input) {
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
        return actListItem;
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
        packets.sort((p1, p2) -> {
            return compareItems(p1, p2);
        });
        out(packets);

        int idx1 = packets.indexOf(parseItem("[[2]]")) + 1;
        int idx2 = packets.indexOf(parseItem("[[6]]")) + 1;
        out(idx1 * idx2);
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
