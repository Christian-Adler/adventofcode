import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Task2 {

    Map<String, Node> nodes = new HashMap<>();
    Node firstNode = null;

    public void init() {
    }

    public void addLine(String input) {
        String[] parts = input.split(" ");
        String from = parts[0];
        String to = parts[2];
        int distance = Integer.parseInt(parts[4]);

        Node nodeFrom = nodes.get(from);
        Node nodeTo = nodes.get(to);

        if (firstNode == null)
            firstNode = nodeFrom;

        if (nodeFrom == null) {
            nodeFrom = new Node(from);
            nodes.put(from, nodeFrom);
        }
        if (nodeTo == null) {
            nodeTo = new Node(to);
            nodes.put(to, nodeTo);
        }

        nodeFrom.neighbors.put(nodeTo, distance);
        nodeTo.neighbors.put(nodeFrom, distance);
    }

    public void afterParse() {
        out(nodes);

        int shortest = Integer.MIN_VALUE;

        HashSet<Node> workList = new HashSet<>(nodes.values());
        for (Node node : workList) {
            int shortestPath = findSHortestPath(node, new ArrayList<>(), 0, 0);
            out(node.name, shortestPath);
            shortest = Math.max(shortestPath, shortest);
        }
        out(shortest);
    }

    int findSHortestPath(Node visitNode, ArrayList<Node> visited, int soFarDistance, int distance) {
        if (visited.size() > nodes.size() * 2)
            return Integer.MIN_VALUE;

        // keine Doppelten
        if (visited.contains(visitNode))
            return Integer.MIN_VALUE;

        int sumDist = soFarDistance + distance;
        ArrayList<Node> sumVisited = new ArrayList<>(visited);
        sumVisited.add(visitNode);

        // Alle Nodes erreicht?
        if (sumVisited.containsAll(nodes.values()))
            return sumDist;

        int minDist = Integer.MIN_VALUE;

        for (Map.Entry<Node, Integer> neighborEntry : visitNode.neighbors.entrySet()) {
            Node neighbor = neighborEntry.getKey();
            Integer distToNeighbor = neighborEntry.getValue();

            int dist = findSHortestPath(neighbor, new ArrayList<>(sumVisited), sumDist, distToNeighbor);
            minDist = Math.max(minDist, dist);
        }

        return minDist;
    }

    public void out(Object... str) {
        String out = "";
        for (Object o : str) {
            if (out.length() > 0)
                out += " ";
            out += o;
        }
        System.out.println(out);
    }


    String cleanFrom(String input, String... strings) {
        String result = input;
        for (String string : strings) {
            input = input.replace(string, "");
        }
        return input;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
