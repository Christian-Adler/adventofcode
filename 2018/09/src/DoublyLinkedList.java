public class DoublyLinkedList {
    Node head = null;
    Node actNode = null;

    public void addRelativeAfter(int value, int relative) {
        Node n = new Node(value);
        if (head == null) {
            head = n;
            actNode = head;
            n.next = head;
            n.prev = head;
            return;
        }

        if (relative < 0) {
            for (int i = 0; i < Math.abs(relative); i++) {
                actNode = actNode.prev;
            }
        } else {
            for (int i = 0; i < relative; i++) {
                actNode = actNode.next;
            }
        }

        Node next = actNode.next;

        actNode.next = n;
        n.prev = actNode;

        n.next = next;
        next.prev = n;

        actNode = n;
    }

    public boolean isEmpty() {
        return actNode == null;
    }

    public long removeRelative(int relative) {
        if (head == null) {
            return -1;
        }

        if (relative < 0) {
            for (int i = 0; i < Math.abs(relative); i++) {
                actNode = actNode.prev;
            }
        } else {
            for (int i = 0; i < relative; i++) {
                actNode = actNode.next;
            }
        }

        long returnValue = actNode.value;
        Node next = actNode.next;
        Node prev = actNode.prev;

        // letzte Node?
        if (next.equals(actNode)) {
            actNode = null;
            head = null;
            return returnValue;
        }

        prev.next = next;
        next.prev = prev;

        // head entfernt? Neuen head setzen
        if (actNode.equals(head))
            head = next;

        actNode = next;

        return returnValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        if (!isEmpty()) {
            Node n = head;
            sb.append(n);
            if (!n.next.equals(head)) {
                n = n.next;
                while (!n.equals(head)) {
                    sb.append(",").append(n);
                    n = n.next;
                }
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private static class Node {
        long value;
        Node next = null;
        Node prev = null;

        public Node(long value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
