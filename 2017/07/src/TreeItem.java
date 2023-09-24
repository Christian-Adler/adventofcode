import java.util.ArrayList;
import java.util.List;

public class TreeItem {
    public final String key;
    public final int weight;
    public final List<String> childrenKeys;
    public TreeItem parent = null;
    public List<TreeItem> children = new ArrayList<>();
    public int weightWithChildren = -1;

    public TreeItem(String input) {
        String[] split = input.split(" -> ");
        String[] splitKey = split[0].split("\\s");
        this.key = splitKey[0];
        this.weight = Integer.parseInt(splitKey[1].replace("(", "").replace(")", ""));

        if (split.length > 1)
            childrenKeys = new ArrayList<>(List.of(split[1].split(", ")));
        else
            childrenKeys = new ArrayList<>();
    }

    public String toString() {
        return key + " (" + weight + ")" + (childrenKeys.isEmpty() ? "" : " -> (" + weightWithChildren + ") " + childrenKeys.toString());
    }

    public boolean isLeaf() {
        return childrenKeys.isEmpty();
    }
}
