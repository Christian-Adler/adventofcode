import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TreeItem {
    public boolean isRoot;
    public String name;
    public boolean isDir;

    @Override
    public String toString() {
        return "TreeItem{" +
                "isRoot=" + isRoot +
                ", name='" + name + '\'' +
                ", isDir=" + isDir + (isDir ? " " + children.size() : "") +
                ", size=" + size +
                '}';
    }

    public int size = 0;
    public LinkedList<TreeItem> children = new LinkedList<>();
    public TreeItem parent = null;

    TreeItem getDirByName(String n) {
        for (TreeItem child : children) {
            if (child.name.equals(n))
                return child;
        }
        return null;
    }

    int calcDirSize() {
        if (!isDir)
            return 0;

        int sum = 0;

        for (TreeItem child :
                children) {
            if (child.isDir)
                sum += child.calcDirSize();
            else
                sum += child.size;
        }

        return sum;
    }

    Set<TreeItem> findDirsSmallerThan(int limit) {
        HashSet<TreeItem> result = new HashSet<>();
        if (isDir) {
            if (calcDirSize() <= limit)
                result.add(this);
            for (TreeItem child : children) {
                result.addAll(child.findDirsSmallerThan(limit));
            }
        }
        return result;
    }

    Set<TreeItem> findDirsGreaterThan(int limit) {
        HashSet<TreeItem> result = new HashSet<>();
        if (isDir) {
            if (calcDirSize() >= limit)
                result.add(this);
            for (TreeItem child : children) {
                result.addAll(child.findDirsGreaterThan(limit));
            }
        }
        return result;
    }

    void addChild(String in) {
        TreeItem item = new TreeItem();
        item.parent = this;
        if (in.startsWith("dir")) {
            in = in.substring(4);
            item.isDir = true;
            item.name = in;
        } else {
            String[] split = in.split(" ");
            item.size = Integer.parseInt(split[0]);
            item.name = split[1];
        }
        children.add(item);
    }

    void append(StringBuilder builder, String prefix) {
        builder.append("\r\n").append(prefix).append("- ").append(
                name
        ).append(" (");

        if (isDir)
            builder.append("dir sumSize=").append(calcDirSize());
        else
            builder.append("file, size=").append(size);

        builder.append(")");

        String childPrefix = prefix + "  ";
        for (TreeItem child : children) {
            child.append(builder, childPrefix);
        }
    }

    void appendJSON(StringBuilder builder, String prefix) {
        builder.append("\r\n").append(prefix).append("'").append(
                name
        ).append("'").append(" :");

        if (isDir) {
            builder.append("{");

            String childPrefix = prefix + "  ";
            for (TreeItem child : children) {
                child.append(builder, childPrefix);
            }
            builder.append("}");
        } else
            builder.append(size);

        builder.append(",");


    }
}
