import java.util.*;

public class Task {

    Map<Pos, Node> nodes = new HashMap<>();
    ArrayList<Pos> sortedPos = null;
    int xMax = 0;
    int yMax = 0;

    Pos pos00 = new Pos(0, 0);
    Pos posTargetData = new Pos(0, 0);

    int minSteps = 230; // Integer.MAX_VALUE; // hat 130 min gedauert :(
    ArrayList<PosPair> minStepsPath = null;
    Map<Integer, Integer> visitedHashes2Stepts = new HashMap<>();

    long openCalls = 0;

    public void addLine(String input) {
        /*
        root@ebhq-gridcenter# df -h
Filesystem              Size  Used  Avail  Use%
/dev/grid/node-x0-y0     89T   65T    24T   73%
         */
        if (!input.startsWith("/dev")) return;
        String[] parts = Util.cleanFrom(input.replaceAll("\\s+", " "), "/dev/grid/node-", "T").split(" ");
        int[] xy = Arrays.stream(Util.cleanFrom(parts[0], "x", "y").split("-")).mapToInt(Integer::parseInt).toArray();
        Pos pos = new Pos(xy[0], xy[1]);
        if (pos.x > xMax) xMax = pos.x;
        if (pos.y > yMax) yMax = pos.y;
        Node node = new Node(parts[0], Long.parseLong(parts[1]), Long.parseLong(parts[2]));
        nodes.put(pos, node);
    }

    public void afterParse() {
        long viablePairs = 0;
        ArrayList<Node> nodeList = new ArrayList<>(nodes.values());
        for (Node node : nodeList) {
            for (Node testNode : nodeList) {
                if (node.equals(testNode)) continue;
                if (node.isEmpty()) continue;
                if (testNode.available() >= node.used) {
//                    out("viable: ", node, testNode);
                    viablePairs++;
                }
            }
        }
        out("viablePairs global", viablePairs);

        // Teil 2

        posTargetData = new Pos(xMax, 0);
        out("posTargetData", posTargetData);
        sortedPos = new ArrayList<>(nodes.keySet());
        Collections.sort(sortedPos);

        visitedHashes2Stepts.put(createHash(posTargetData, nodes), 0);
        int minSteps = findMin(posTargetData, nodes, 0, new ArrayList<>());
        out("min steps:", minSteps);
        out("min steps path", minStepsPath);
    }

    long counter = 0;

    int findMin(Pos actPos, Map<Pos, Node> nodesMap, int stepsSoFar, ArrayList<PosPair> stepsPath) {
        counter++;
        if (counter % 10000 == 0)
            out("openCalls:", openCalls, ", hashes:", visitedHashes2Stepts.size());


        if (stepsSoFar > minSteps)
            return Integer.MAX_VALUE;

        // Optimierung: Manhatten Distanz von Empty zu Target zu ZielPos *2

//        if (visitedHashes.contains(createHash(actPos, nodesMap)))
//            return Integer.MAX_VALUE;

        if (actPos.equals(pos00)) {
//            out("found way", stepsSoFar);
            if (stepsSoFar < minSteps) {
                out("found min", stepsSoFar);
                minSteps = stepsSoFar;
                minStepsPath = stepsPath;
            }
            return stepsSoFar;
        }

        int min = Integer.MAX_VALUE;

        Set<PosPair> adjacentViablePairs = findAdjacentViablePairs(nodesMap);
        openCalls += adjacentViablePairs.size();

        for (PosPair adjacentViablePair : adjacentViablePairs) {
            Pos actP = actPos;
            if (adjacentViablePair.p1.equals(actPos))
                actP = adjacentViablePair.p2;
            else if (adjacentViablePair.p2.equals(actPos))
                actP = adjacentViablePair.p1;

            Map<Pos, Node> nodesM = clone(nodesMap);
            exchangeUsed(nodesM.get(adjacentViablePair.p1), nodesM.get(adjacentViablePair.p2));

            int hash = createHash(actP, nodesM);
            Integer soFarStepsForHash = visitedHashes2Stepts.get(hash);
            if (soFarStepsForHash == null || stepsSoFar + 1 < soFarStepsForHash)
                visitedHashes2Stepts.put(hash, stepsSoFar + 1);
            else {
                openCalls--;
                continue;
            }

            ArrayList<PosPair> stepsP = new ArrayList<>(stepsPath);
            stepsP.add(adjacentViablePair);


            int m = findMin(actP, nodesM, stepsSoFar + 1, stepsP);
            if (m < min)
                min = m;

            openCalls--;
        }

        return min;
    }

    void exchangeUsed(Node nodeP1, Node nodeP2) {
        long tmpUsed = nodeP1.used;
        nodeP1.used = nodeP2.used;
        nodeP2.used = tmpUsed;
    }

    Set<PosPair> findAdjacentViablePairs(Map<Pos, Node> nodesMap) {
        Set<PosPair> pairs = new HashSet<>();

        for (Map.Entry<Pos, Node> entry : nodesMap.entrySet()) {
            Pos pos = entry.getKey();
            Node node = entry.getValue();
//            if (node.isEmpty()) continue;
            if (!node.isEmpty()) continue;
            List<Pos> adjacent = getAdjacentPositions(pos);
            for (Pos adjPos : adjacent) {
                Node testNode = nodesMap.get(adjPos);
//                if (node.equals(testNode)) continue; // kann nicht vorkommen
//                if (node.isEmpty()) continue;

                // Empty??
//                if (testNode.isEmpty() && testNode.available() >= node.used) {
                if (!testNode.isEmpty() && node.available() >= testNode.used) {
                    pairs.add(new PosPair(pos, adjPos));
                }
            }
        }

        return pairs;
    }

    List<Pos> getAdjacentPositions(Pos p) {
        List<Pos> res = new ArrayList<>();
        Pos tmp = p.addToNew(0, 1);
        if (tmp.y >= 0 && tmp.y <= yMax)
            res.add(tmp);
        tmp = p.addToNew(0, -1);
        if (tmp.y >= 0 && tmp.y <= yMax)
            res.add(tmp);
        tmp = p.addToNew(1, 0);
        if (tmp.x >= 0 && tmp.x <= xMax)
            res.add(tmp);
        tmp = p.addToNew(-1, 0);
        if (tmp.x >= 0 && tmp.x <= xMax)
            res.add(tmp);
        return res;
    }

    Map<Pos, Node> clone(Map<Pos, Node> nodesMap) {
        Map<Pos, Node> res = new HashMap<>();
        for (Map.Entry<Pos, Node> entry : nodesMap.entrySet()) {
            res.put(entry.getKey(), entry.getValue().copy());
        }
        return res;
    }

    int createHash(Pos actPos, Map<Pos, Node> nodesMap) {
        StringBuilder hash = new StringBuilder();
//        for (Pos pos : sortedPos) {
//            hash.append("_").append(nodesMap.get(pos).used);
//        }

        // Nur Pos der leeren Nodes hashen?
        for (Pos pos : sortedPos) {
            Node node = nodesMap.get(pos);
            if (node.isEmpty())
                hash.append("_").append(pos);
        }
        hash.append("#").append(actPos.toString());
        return hash.toString().hashCode();
//        return hash.toString();
    }

    public void out(Object... str) {
        Util.out(str);
    }

}
