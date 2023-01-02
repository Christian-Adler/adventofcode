import java.util.HashSet;
import java.util.Set;

public class Task {
    TreeItem root = null;
    TreeItem humn = null;
    Set<TreeItem> treeItems = new HashSet<>();


    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split(": ");

        TreeItem treeItem = treeItems.stream().filter(t -> t.name.equals(split[0])).findAny().orElse(null);
        if (treeItem == null) {
            treeItem = new TreeItem(split[0]);
            treeItems.add(treeItem);
        }

        if (treeItem.name.equals("root"))
            root = treeItem;
        else if (treeItem.name.equals("humn")) {
            humn = treeItem;
            treeItem.inMyPath = true;
        }


        String[] operation = split[1].split(" ");

        if (operation.length == 1) { // Zahl
            treeItem.number = Long.parseLong(operation[0]);
        } else {
            String nameLeft = operation[0];
            String nameRight = operation[2];
            String operator = operation[1];

            if (operator.equals("+"))
                treeItem.calcBase = TreeItem.add;
            else if (operator.equals("-"))
                treeItem.calcBase = TreeItem.sub;
            else if (operator.equals("*"))
                treeItem.calcBase = TreeItem.mult;
            else if (operator.equals("/"))
                treeItem.calcBase = TreeItem.div;

            TreeItem left = treeItems.stream().filter(t -> t.name.equals(nameLeft)).findAny().orElse(null);
            if (left == null) {
                left = new TreeItem(nameLeft);
                treeItems.add(left);
            }
            TreeItem right = treeItems.stream().filter(t -> t.name.equals(nameRight)).findAny().orElse(null);
            if (right == null) {
                right = new TreeItem(nameRight);
                treeItems.add(right);
            }

            treeItem.left = left;
            treeItem.right = right;

            left.parent = treeItem;
            right.parent = treeItem;
        }
    }

    public void afterParse() {
        long v = root.calc();
        out("root calc: " + v);

        TreeItem parent = humn.parent;
        while (parent != null) {
            parent.inMyPath = true;
            parent = parent.parent;
        }

        root.calcBase = TreeItem.equ;
        root.calcMe(0);

        v = root.calc();
        out("root calc: " + v);
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
