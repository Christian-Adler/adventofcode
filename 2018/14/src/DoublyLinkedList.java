public class DoublyLinkedList {
    Node head = null;
    Node tail = null;
    int size = 0;

    public Node add(int value) {
        Node n = new Node(value, size);
        if (head == null) {
            head = n;
            tail = n;
            n.next = head;
            n.prev = head;
            size = 1;
            return n;
        }

        tail.next = n;
        n.prev = tail;
        head.prev = n;
        n.next = head;

        tail = n;
        size++;

        return n;
    }

    public Node getNode(int idx) {
        Node n = head;
        for (int i = 0; i < idx; i++) {
            n = n.next;
        }
        return n;
    }

    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        if (!isEmpty()) {
            Node n = head;
            sb.append(n.value);
            if (!n.next.equals(head)) {
                n = n.next;
                while (!n.equals(head)) {
                    sb.append(",").append(n.value);
                    n = n.next;
                }
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public static class Node {
        int value;
        int idx;
        Node next = null;
        Node prev = null;

        public Node(int value, int idx) {
            this.value = value;
            this.idx = idx;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("value=").append(value);
            sb.append(", idx=").append(idx);
            sb.append('}');
            return sb.toString();
        }
    }
}
