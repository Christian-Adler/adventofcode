package aoc.astar;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AStar<T> {
  private final BiFunction<T, T, Long> heuristicDistance;
  private final Function<T, Collection<AStarNextItem<T>>> determineNextFnc;

  public AStar(BiFunction<T, T, Long> heuristicDistance, Function<T, Collection<AStarNextItem<T>>> determineNextFnc) {
    this.heuristicDistance = heuristicDistance;
    this.determineNextFnc = determineNextFnc;
  }

  public AStarItem<T> findShortestPath(T start, T end) {
    // https://de.wikipedia.org/wiki/A*-Algorithmus
    PriorityQueue<AStarItem<T>> openList = new PriorityQueue<>(Comparator.comparingLong(AStarItem::fScore));
    Set<AStarItem<T>> closedList = new HashSet<>();

    openList.add(new AStarItem<>(start, end, 0, heuristicDistance));

    AStarItem<T> targetReached = null;

    // long iterations = 0;
    while (!openList.isEmpty()) {
      // iterations++; if (iterations % 10000 == 0) System.out.printf("Iterations: %d, open: %d%n", iterations, openList.size());

      AStarItem<T> workItem = openList.poll();
      if (workItem == null)
        throw new IllegalStateException("null!");
      // reached end?
      if (heuristicDistance.apply(workItem.item, end) <= 0) {
        targetReached = workItem;
        break;
      }

      closedList.add(workItem);

      // expand Node
      Collection<AStarNextItem<T>> nextItems = determineNextFnc.apply(workItem.item);
      for (AStarNextItem<T> nextItem : nextItems) {
        long tentativeG = workItem.score + nextItem.stepCosts();

        AStarItem<T> nextWorkItemWouldBe = new AStarItem<>(nextItem.item(), end, tentativeG, heuristicDistance);
        nextWorkItemWouldBe.setPredecessor(workItem);
        if (closedList.contains(nextWorkItemWouldBe))
          continue;

        AStarItem<T> alreadyInList = openList.stream().filter(w -> w.equals(nextWorkItemWouldBe)).findFirst().orElse(null);
        if (alreadyInList != null && tentativeG >= alreadyInList.score)
          continue;

        openList.remove(alreadyInList);
        openList.add(nextWorkItemWouldBe);
      }
    }

    return targetReached;
  }
}
