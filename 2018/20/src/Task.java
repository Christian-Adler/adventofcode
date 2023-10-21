import java.util.*;

public class Task {

    PathItem root = new PathItem();

    Set<Pos> map = new HashSet<>();

    List<Pos> longestPath = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        PathItem actPathItem = root;

        LinkedList<PathItem> lastOpenedList = new LinkedList<>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '^' || c == '$') continue;
            if (c == 'N' || c == 'S' || c == 'E' || c == 'W')
                actPathItem.add(String.valueOf(c));
            else if (c == '(') {
                lastOpenedList.add(actPathItem);
                PathItem pathItem = new PathItem();
                actPathItem.split.add(pathItem);
                actPathItem = pathItem;
            } else if (c == '|') {
                PathItem pathItem = new PathItem();
                lastOpenedList.getLast().split.add(pathItem);
                actPathItem = pathItem;
            } else if (c == ')') {
                // after Split
                PathItem pathItem = new PathItem();

                actPathItem = lastOpenedList.removeLast();
                actPathItem.afterSplit = pathItem;
                actPathItem = pathItem;
            } else
                throw new IllegalStateException("unexpected char " + c);
        }
    }


    public void afterParse() {
        out(root);

        Pos rootPos = new Pos(0, 0);
        rootPos.color = "#ff0000";
        map.add(rootPos);

        // Ceate map
        treeWalk(rootPos, root);

        Set<Pos> roomsWithShortestPathsWithAtLeast1000Doors = new HashSet<>();
        int countWaysWith1000Doors = 0;


        LinkedList<LinkedList<Pos>> workList = new LinkedList<>();

        LinkedList<Pos> start = new LinkedList<>();
        start.add(rootPos);

        workList.add(start);

        while (!workList.isEmpty()) {
            LinkedList<Pos> actList = workList.removeFirst();

            if (longestPath.size() % 2 == 0) {
                int doorsSoFar = longestPath.size() / 2;
                if (doorsSoFar >= 1000) {
                    countWaysWith1000Doors++;
                    roomsWithShortestPathsWithAtLeast1000Doors.add(actList.getLast());
                }
            }

            if (actList.size() > longestPath.size()) longestPath = actList;

            Pos actPos = actList.getLast();

            for (Pos pos : Pos.adjacent) {
                Pos p = actPos.addToNew(pos);
                if (!actList.contains(p) && map.contains(p)) {
                    LinkedList<Pos> nextList = new LinkedList<>(actList);
                    nextList.add(p);
                    workList.add(nextList);
                }
            }
        }


        out(toStringConsole());
        out(toStringSVG());

        out("longestPath", longestPath.size());
        int doorsLongestPath = (longestPath.size() - 1) / 2;
        out("Part1", "doors", doorsLongestPath);
        out("Part2", "countWaysWith1000Doors", countWaysWith1000Doors);  //>8782  < 17565 -> 8784
        out("Part2", "roomsWithShortestPathsWithAtLeast1000Doors", roomsWithShortestPathsWithAtLeast1000Doors.size());
    }

    Set<Pos> treeWalk(Pos actPos, PathItem pathItem) {
        Pos p = actPos;
        map.add(p);

        for (Pos pos : pathItem.pathBeforeSplit) {
            p = p.addToNew(pos);
            map.add(p);
            // immer 2x damit bei Zeichnen Waende sichtbar sind.
            p = p.addToNew(pos);
            map.add(p);
        }

        Set<Pos> splitEndPositions = new HashSet<>();
        for (PathItem item : pathItem.split) {
            splitEndPositions.addAll(treeWalk(p, item));
        }

        Set<Pos> afterSplitPositions = new HashSet<>();
        if (pathItem.afterSplit != null) {
            for (Pos splitEndPosition : splitEndPositions) {
                afterSplitPositions.addAll(treeWalk(splitEndPosition, pathItem.afterSplit));
            }
        }

        if (!afterSplitPositions.isEmpty())
            return afterSplitPositions;
        if (!splitEndPositions.isEmpty())
            return splitEndPositions;

        Set<Pos> lastPos = new HashSet<>();
        lastPos.add(p);
        return lastPos;
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {
        SVG svg = new SVG();
        map.forEach(svg::add);
        for (Pos pos : longestPath) {
            svg.add(pos, "#00a0ff");
        }
        svg.add(new Pos(0, 0, "#ff0000"));
        svg.add(longestPath.get(longestPath.size() - 1), "#ff0000");


        return svg.toSVGString();
    }


    public String toStringConsole() {
        SVG svg = new SVG();
        for (Pos pos : map) {
            svg.add(pos, "O");
        }
        for (Pos pos : longestPath) {
            svg.add(pos, "#");
        }


        return svg.toConsoleString();
    }
}
