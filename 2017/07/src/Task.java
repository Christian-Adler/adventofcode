import javax.management.openmbean.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Task {
    Map<String, TreeItem> map = new HashMap<>();
    TreeItem root = null;

    public void init() {
    }

    public void addLine(String input) {
        TreeItem treeItem = new TreeItem(input);
        map.put(treeItem.key, treeItem);

//        out(treeItem);
    }

    public void afterParse() {
        // build tree
        for (TreeItem treeItem : map.values()) {
            for (TreeItem treeItem2 : map.values()) {
                if (Objects.equals(treeItem.key, treeItem2.key)) continue;

                if (treeItem2.isLeaf()) continue;

                if (treeItem2.childrenKeys.contains(treeItem.key)) {
                    treeItem.parent = treeItem2;
                    treeItem2.children.add(treeItem);
                    break;
                }
            }

            if (treeItem.parent == null) {
                if (root != null) out("Fehler: 2x root");
                root = treeItem;
            }
        }

        calcWeightWithChildren(root);

        out("root", root);

        findWrongSubTree(root);
    }

    private int calcWeightWithChildren(TreeItem treeItem) {
        int weight = treeItem.weight;
        if (!treeItem.isLeaf()) {
            for (TreeItem child : treeItem.children) {
                weight += calcWeightWithChildren(child);
            }
        }
        treeItem.weightWithChildren = weight;
        return weight;
    }

    private static record WrongWeightResult(TreeItem treeItem, int shouldWeighWithChildren) {
    }

    private WrongWeightResult getWrongWeightChild(TreeItem treeItem) {
        Map<Integer, Integer> mapWeightsCount = new HashMap<>();
        for (TreeItem child : treeItem.children) {
            Integer soFarValue = mapWeightsCount.getOrDefault(child.weightWithChildren, 0);
            mapWeightsCount.put(child.weightWithChildren, soFarValue + 1);
        }
        if (mapWeightsCount.size() == 1) return null;

        int wrongWeightWithChildren = -1;
        int shouldWeight = -1;
        for (Map.Entry<Integer, Integer> entry : mapWeightsCount.entrySet()) {
            if (entry.getValue() == 1) {
                wrongWeightWithChildren = entry.getKey();
                out("Wrong weight ", wrongWeightWithChildren);
            } else {
                shouldWeight = entry.getKey();
                out("Should weight ", shouldWeight);
            }
        }

        if (wrongWeightWithChildren < 0)
            throw new InvalidKeyException(" Found no child item");

        for (TreeItem child : treeItem.children) {
            if (child.weightWithChildren == wrongWeightWithChildren)
                return new WrongWeightResult(child, shouldWeight);
        }

        throw new InvalidKeyException(" Found no child item");
    }

    private boolean findWrongSubTree(TreeItem treeItem) {
        if (treeItem.isLeaf()) return false;

        WrongWeightResult wrongChildRes = getWrongWeightChild(treeItem);
        if (wrongChildRes != null) {
            TreeItem wrongChild = wrongChildRes.treeItem;
            WrongWeightResult wrongSubChild = getWrongWeightChild(wrongChild);
            while (wrongSubChild != null) {
                wrongChildRes = wrongSubChild;
                wrongSubChild = getWrongWeightChild(wrongSubChild.treeItem);
            }

            out(wrongChildRes);
            wrongChild = wrongChildRes.treeItem;

            TreeItem parent = wrongChild.parent;
            wrongChildRes = getWrongWeightChild(parent);
            if (wrongChildRes == null) throw new InvalidKeyException(" Found no child item");
            int shouldWeighWithChildren = wrongChildRes.shouldWeighWithChildren;
            out(shouldWeighWithChildren);

            int diff = wrongChild.weightWithChildren - shouldWeighWithChildren;
            out("diff", diff);
            out(wrongChild);
            int shouldWeigh = wrongChild.weight - diff;
            out("shouldWeigh", shouldWeigh); // < 37281
            return true;
        } else {
            for (TreeItem child : treeItem.children) {
                if (findWrongSubTree(child)) return true;
            }
        }
        return false;
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
