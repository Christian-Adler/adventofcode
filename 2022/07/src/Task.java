import java.util.Set;

public class Task {

    TreeItem root = new TreeItem();
    TreeItem actDirPointer = root;

    boolean isInCmdLS = false;

    Task() {
        root.name = "/";
        root.isDir = true;
    }

    public void addLine(String input) {
        String in = input;
        // cmd ?
        if (in.startsWith("$")) {
            isInCmdLS = false;
            in = in.substring(2);
            // cd
            if (in.startsWith("cd")) {
                in = in.substring(3);
                if (in.startsWith("/")) // goto root
                    actDirPointer = root;
                else if (in.startsWith("..") && !actDirPointer.isRoot) // one up
                    actDirPointer = actDirPointer.parent;
                else { // find dir by name
                    TreeItem childDir = actDirPointer.getDirByName(in);
                    if (childDir == null)
                        out("Found no child dir for " + in);
                    else
                        actDirPointer = childDir;
                }
            } else if (in.startsWith("ls")) {
                isInCmdLS = true;
            }
        } else if (isInCmdLS) {
            actDirPointer.addChild(in);
        }


    }

    public void afterParse() {
        Set<TreeItem> limitDirs = root.findDirsSmallerThan(100000);
        int sumLimitDirs = limitDirs.stream().mapToInt(TreeItem::calcDirSize).sum();
        out("Limit Sum: " + sumLimitDirs);

        // Aufgabe 2
        int diskSpace = 70000000;
        int requiredFreeSpace = 30000000;

        int usedSpace = root.calcDirSize();
        int freeSpace = diskSpace - usedSpace;
        int toFreeSpace = requiredFreeSpace - freeSpace;

        out("to free space " + toFreeSpace);

        limitDirs = root.findDirsGreaterThan(toFreeSpace);

        int smallestDirSize = limitDirs.stream().mapToInt(TreeItem::calcDirSize).min().orElse(0);
        out("Smallest dir to free " + smallestDirSize);
    }

    public void out(String str) {
        System.out.println(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        root.append(builder, "");

        return builder.toString();
    }
}
