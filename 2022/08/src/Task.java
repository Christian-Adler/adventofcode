import java.util.HashMap;
import java.util.Map;

public class Task {
    Map<String, Tree> map = new HashMap<>();

    int yMax = -1;
    int xMax = -1;

    public void addLine(String input) {
        yMax++;
        String in = input.trim();
        if (xMax < 0) xMax = in.length() - 1;
        for (int i = 0; i < in.length(); i++) {
            int treeHeight = Integer.parseInt("" + in.charAt(i));
            map.put(key(i, yMax), new Tree(treeHeight));
        }
    }

    String key(int x, int y) {
        return x + "," + y;
    }

    public void afterParse() {
        // Sichtbarkeit ermitteln
        for (int y = 0; y <= yMax; y++) {
            for (int x = 0; x <= xMax; x++) {
                Tree tree = getTree(x, y);
                calcVisibility(tree, x, y);
                calcScenicScore(tree, x, y);
            }
        }
    }

    void calcVisibility(Tree tree, int x, int y) {
        // Zeile
        int xVal = x - 1;
        tree.visibleFromLeft = 1;
        while (xVal >= 0) {
            Tree checkTree = getTree(xVal, y);
            if (checkTree.height >= tree.height) {
                tree.visibleFromLeft = 0;
                break;
            }
            xVal--;
        }
        xVal = x + 1;
        tree.visibleFromRight = 1;
        while (xVal <= xMax) {
            Tree checkTree = getTree(xVal, y);
            if (checkTree.height >= tree.height) {
                tree.visibleFromRight = 0;
                break;
            }
            xVal++;
        }

        // Spalte
        int yVal = y - 1;
        tree.visibleFromTop = 1;
        while (yVal >= 0) {
            Tree checkTree = getTree(x, yVal);
            if (checkTree.height >= tree.height) {
                tree.visibleFromTop = 0;
                break;
            }
            yVal--;
        }
        yVal = y + 1;
        tree.visibleFromBottom = 1;
        while (yVal <= yMax) {
            Tree checkTree = getTree(x, yVal);
            if (checkTree.height >= tree.height) {
                tree.visibleFromBottom = 0;
                break;
            }
            yVal++;
        }

        tree.visible = (tree.visibleFromLeft + tree.visibleFromRight + tree.visibleFromTop + tree.visibleFromBottom) > 0 ? 1 : 0;
    }

    void calcScenicScore(Tree tree, int x, int y) {

        // Rand?

        if (x == 0 || x == xMax || y == 0 || y == yMax) {
            tree.scenicScore = 0;
            return;
        }

        // Zeile
        int xVal = x - 1;
        while (xVal >= 0) {
            Tree checkTree = getTree(xVal, y);
            if (checkTree.height >= tree.height) {
                int distance = Math.abs(x - xVal);
                tree.scenicScore *= distance;
                break;
            }
            xVal--;
            if (xVal < 0) // Rand erreicht?
            {
                int distance = Math.abs(x - 0);
                tree.scenicScore *= distance;
            }
        }
        xVal = x + 1;
        tree.visibleFromRight = 1;
        while (xVal <= xMax) {
            Tree checkTree = getTree(xVal, y);
            if (checkTree.height >= tree.height) {
                int distance = Math.abs(x - xVal);
                tree.scenicScore *= distance;
                break;
            }
            xVal++;
            if (xVal > xMax) // Rand erreicht?
            {
                int distance = Math.abs(x - xMax);
                tree.scenicScore *= distance;
            }
        }

        // Spalte
        int yVal = y - 1;
        tree.visibleFromTop = 1;
        while (yVal >= 0) {
            Tree checkTree = getTree(x, yVal);
            if (checkTree.height >= tree.height) {
                int distance = Math.abs(y - yVal);
                tree.scenicScore *= distance;
                break;
            }
            yVal--;
            if (yVal < 0) // Rand erreicht?
            {
                int distance = Math.abs(y - 0);
                tree.scenicScore *= distance;
            }
        }
        yVal = y + 1;
        tree.visibleFromBottom = 1;
        while (yVal <= yMax) {
            Tree checkTree = getTree(x, yVal);
            if (checkTree.height >= tree.height) {
                int distance = Math.abs(y - yVal);
                tree.scenicScore *= distance;
                break;
            }
            yVal++;
            if (yVal > yMax) // Rand erreicht?
            {
                int distance = Math.abs(y - yMax);
                tree.scenicScore *= distance;
            }
        }
    }

    Tree getTree(int x, int y) {
        return map.get(key(x, y));
    }

    public void out(String str) {
        System.out.println(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        int visibleCount = 0;
        int maxScenicScore = 0;
        for (Map.Entry<String, Tree> entry : map.entrySet()) {
            if (entry.getValue().visible > 0) {
//                builder.append("\r\n").append(entry.getKey());
                visibleCount++;
            }
            if (entry.getValue().scenicScore > maxScenicScore)
                maxScenicScore = entry.getValue().scenicScore;
        }


        builder.append("\r\nVisible:").append(visibleCount);
        builder.append("\r\nScenicScore:").append(maxScenicScore);

        for (int y = 0; y <= yMax; y++) {
            builder.append("\r\n");
            for (int x = 0; x <= xMax; x++) {
                Tree tree = getTree(x, y);
                builder.append("[");
                builder.append(tree.scenicScore);
                builder.append("]");
            }
        }

        return builder.toString();
    }
}
