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
    return findShortestPath(start, end, false);
  }

  public AStarItem<T> findShortestPaths(T start, T end) {
    return findShortestPath(start, end, true);
  }

  private AStarItem<T> findShortestPath(T start, T end, boolean findAllShortestPaths) {
    // https://de.wikipedia.org/wiki/A*-Algorithmus
    PriorityQueue<AStarItem<T>> openList = new PriorityQueue<>(Comparator.comparingLong(AStarItem::fScore));
    Set<AStarItem<T>> closedList = new HashSet<>();

    long minScore = -1;

    openList.add(new AStarItem<>(start, end, 0, heuristicDistance));

    AStarItem<T> targetReached = null;

    // long iterations = 0;
    while (!openList.isEmpty()) {
      // iterations++; // if (iterations % 10000 == 0) System.out.printf("Iterations: %d, open: %d%n", iterations, openList.size());

      AStarItem<T> workItem = openList.poll();
      if (workItem == null)
        throw new IllegalStateException("null!");

      // System.out.println(workItem.item);

      // reached end?
      if (workItem.item.equals(end)) {
        targetReached = workItem;
        minScore = workItem.score;

        // only one shortest path enough? -> ready
        // otherwise continue
        if (!findAllShortestPaths)
          break;
      }


      closedList.add(workItem);

      // expand Node
      Collection<AStarNextItem<T>> nextItems = determineNextFnc.apply(workItem.item);


      for (AStarNextItem<T> nextItem : nextItems) {
        long tentativeG = workItem.score + nextItem.stepCosts();

        // Already found shortestPath? Then check if Node is worth...
        if (minScore >= 0 && tentativeG > minScore)
          continue;

        AStarItem<T> nextWorkItemWouldBe = new AStarItem<>(nextItem.item(), end, tentativeG, heuristicDistance);

        if (closedList.contains(nextWorkItemWouldBe)) {
          for (AStarItem<T> closedItem : closedList) {
            if (closedItem.equals(nextWorkItemWouldBe) && closedItem.score == nextWorkItemWouldBe.score) {
              closedItem.addPredecessor(workItem);
              // out("found new path");
              break;
            }
          }
          continue;
        }

        // The same with stream but way slower:
        // AStarItem<T> alreadyInList = closedList.stream().filter(w -> w.equals(nextWorkItemWouldBe)).findFirst().orElse(null);
        //
        // if (alreadyInList != null) {
        //   if (findAllShortestPaths && alreadyInList.score == nextWorkItemWouldBe.score) // add workItem as predecessor
        //     alreadyInList.addPredecessor(workItem);
        //   continue;
        // }

        nextWorkItemWouldBe.addPredecessor(workItem);


        AStarItem<T> alreadyInList = openList.stream().filter(w -> w.equals(nextWorkItemWouldBe)).findFirst().orElse(null);
        if (alreadyInList != null) {
          if (findAllShortestPaths) {
            if (tentativeG > alreadyInList.score)
              continue;
            else if (tentativeG == alreadyInList.score) {
              alreadyInList.addPredecessor(workItem);
              continue;
            }
          } else {
            if (tentativeG >= alreadyInList.score)
              continue;
          }
        }

        openList.remove(alreadyInList);
        openList.add(nextWorkItemWouldBe);
      }
    }

    return targetReached;
  }
}
