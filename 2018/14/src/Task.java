public class Task {
    int input = 0;

    public void init(int input) {
        this.input = input;
    }

    public void afterParse() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.add(3);
        list.add(7);

        DoublyLinkedList.Node first = list.getNode(0);
        DoublyLinkedList.Node second = list.getNode(1);

        out(list);
//        out(first);
//        out(second);

        while (list.size < input + 10) {
            int sumRecipeScore = first.value + second.value;
            if (sumRecipeScore < 10) {
                list.add(sumRecipeScore);
            } else {
                list.add(sumRecipeScore / 10);
                list.add(sumRecipeScore % 10);
            }

            int firstSteps = 1 + first.value;
            int secondSteps = 1 + second.value;

            for (int i = 0; i < firstSteps; i++) {
                first = first.next;
            }
            for (int i = 0; i < secondSteps; i++) {
                second = second.next;
            }

//            out(list);
//
//            out(first);
//            out(second);
        }
//        out(list);
        StringBuilder build10 = new StringBuilder();
        DoublyLinkedList.Node node = list.getNode(input);
        build10.append(node.value);
        for (int i = 0; i < 10 - 1; i++) {
            node = node.next;
            build10.append(node.value);
        }
        out("Part 1", build10);
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
