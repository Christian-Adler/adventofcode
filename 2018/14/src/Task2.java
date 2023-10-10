public class Task2 {
    String input = "0";

    public void init(String input) {
        this.input = input;
    }

    public void afterParse() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.add(3);
        list.add(7);

        DoublyLinkedList.Node first = list.getNode(0);
        DoublyLinkedList.Node second = list.getNode(1);

//        out(list);
//        out(first);
//        out(second);

        while (true) {

            int sumRecipeScore = first.value + second.value;
            DoublyLinkedList.Node n;
            if (sumRecipeScore < 10) {
                n = list.add(sumRecipeScore);
                if (input.endsWith(String.valueOf(n.value))) {
                    if (checkLeftForInput(n))
                        break;
                }
            } else {
                int add = sumRecipeScore / 10;
                n = list.add(add);
                if (input.endsWith(String.valueOf(n.value))) {
                    if (checkLeftForInput(n))
                        break;
                }
                add = sumRecipeScore % 10;
                n = list.add(add);
                if (input.endsWith(String.valueOf(n.value))) {
                    if (checkLeftForInput(n))
                        break;
                }
            }

            int firstSteps = 1 + first.value;
            int secondSteps = 1 + second.value;

            for (int i = 0; i < firstSteps; i++) {
                first = first.next;
            }
            for (int i = 0; i < secondSteps; i++) {
                second = second.next;
            }

        }
//        out(list);
        out("Part 2 - recipes:", list.size - input.length());
    }

    private boolean checkLeftForInput(DoublyLinkedList.Node n) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            b.append(n.value);
            n = n.prev;
        }
        if (b.reverse().toString().equals(input)) {
            return true;
        }
        return false;
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
