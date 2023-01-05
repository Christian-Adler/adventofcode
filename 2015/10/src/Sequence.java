public class Sequence {
    int num = 1;
    int of = 1;

    public Sequence(int num, int of) {
        this.num = num;
        this.of = of;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(num);
        sb.append(",");
        sb.append(of);
        sb.append('}');
        return sb.toString();
//        final StringBuilder sb = new StringBuilder("{");
//        sb.append("num=").append(num);
//        sb.append(", of=").append(of);
//        sb.append('}');
//        return sb.toString();
    }
}
