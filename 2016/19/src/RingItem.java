public class RingItem {
    final int num;
    int numPresents = 1;
    RingItem prev;
    RingItem next;

    public RingItem(int num) {
        this.num = num;
    }

    void removeFromRing() {
        prev.next = next;
        next.prev = prev;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RingItem{");
        sb.append("num=").append(num);
        sb.append(", numPresents=").append(numPresents);
        sb.append('}');
        return sb.toString();
    }
}
