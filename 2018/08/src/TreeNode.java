import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int headerChildNodes = 0;
    int headerMetadataEntries = 0;

    List<TreeNode> children = new ArrayList<>();
    List<Integer> metadataEntries = new ArrayList<>();

    @Override
    public String toString() {
        return "TreeNode{" + "headerChildNodes=" + headerChildNodes +
                ", headerMetadataEntries=" + headerMetadataEntries +
                ", children=" + children.size() +
                ", metadataEntries=" + metadataEntries +
                '}';
    }
}
