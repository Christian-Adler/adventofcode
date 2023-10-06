import java.util.ArrayList;
import java.util.Arrays;

public class Task {
    ArrayList<Integer> inputList = new ArrayList<>();
    TreeNode root = null;

    public void init() {
    }

    public void addLine(String input) {
        inputList.addAll(Arrays.stream(input.split("\\s+")).mapToInt(Integer::parseInt).boxed().toList());
    }

    public void afterParse() {
        findChild(root, 0);
        out(root);
        out("Part 1", sumMetadata(root));
        out("Part 2", calcNodeValue(root));
    }

    private int findChild(TreeNode parent, int startIdx) {
        TreeNode child = new TreeNode();
        child.headerChildNodes = inputList.get(startIdx);
        child.headerMetadataEntries = inputList.get(startIdx + 1);

        if (parent == null)
            root = child;
        else
            parent.children.add(child);

        int nextPartIdx = startIdx + 2;
        for (int i = 0; i < child.headerChildNodes; i++) {
            nextPartIdx = findChild(child, nextPartIdx);
        }

        for (int i = 0; i < child.headerMetadataEntries; i++) {
            child.metadataEntries.add(inputList.get(nextPartIdx));
            nextPartIdx++;
        }

        return nextPartIdx;
    }

    private int sumMetadata(TreeNode node) {
        int sum = 0;
        for (Integer metadataEntry : node.metadataEntries) {
            sum += metadataEntry;
        }
        for (TreeNode child : node.children) {
            sum += sumMetadata(child);
        }
        return sum;
    }

    private int calcNodeValue(TreeNode node) {
        int nodeValue = 0;
        if (node.headerChildNodes == 0) {
            for (Integer metadataEntry : node.metadataEntries) {
                nodeValue += metadataEntry;
            }
        } else {
            for (Integer metadataEntry : node.metadataEntries) {
                int childIdx = metadataEntry - 1;
                if (childIdx >= 0 && childIdx < node.headerChildNodes) {
                    nodeValue += calcNodeValue(node.children.get(childIdx));
                }
            }
        }
        return nodeValue;
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
