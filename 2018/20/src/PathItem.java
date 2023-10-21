import java.util.LinkedList;

public class PathItem {

    String pth = "";
    LinkedList<Pos> pathBeforeSplit = new LinkedList<>();

    LinkedList<PathItem> split = new LinkedList<>();

    PathItem afterSplit = null;

    void add(String d) {
        pth += d;
        pathBeforeSplit.add(Pos.getDir(d));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(pth);
        if (!split.isEmpty()) {
            sb.append("-");
            for (PathItem pathItem : split) {
                sb.append(">");
            }
        }
        return sb.toString();
    }
}
