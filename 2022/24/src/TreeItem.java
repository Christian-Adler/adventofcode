import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeItem {
    Pos pos;
    int steps = 0;

    TreeItem parent = null;
    List<TreeItem> children = new ArrayList<>();

    TreeItem addChild(Pos p) {
        TreeItem child = new TreeItem();
        child.pos = p;
        child.steps = steps + 1;
        child.parent = this;
        children.add(child);
        return child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeItem treeItem = (TreeItem) o;
        return pos.equals(treeItem.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }
}
