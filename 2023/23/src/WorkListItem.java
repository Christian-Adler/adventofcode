import java.util.LinkedHashSet;

public record WorkListItem(Node node, int pathLength, LinkedHashSet<Node> visited, int visitedPathLength) {
}
