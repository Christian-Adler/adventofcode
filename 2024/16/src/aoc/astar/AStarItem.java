package aoc.astar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class AStarItem<I> {
  public final I item;
  private final I end;
  public final long score;
  /**
   * normally only one - but in case find all shortest paths there could be more than one
   */
  private final List<AStarItem<I>> predecessors = new ArrayList<>();

  /**
   * heuristic function h(node) estimate costs to target
   */
  private final BiFunction<I, I, Long> h;

  AStarItem(I item, I end, long score, BiFunction<I, I, Long> h) {
    this.item = item;
    this.end = end;
    this.score = score;
    this.h = h;
  }

  public long fScore() {
    // f(x) = g(x) + h(x)
    return score + h.apply(item, end);
  }

  public AStarItem<I> getPredecessor() {
    if (predecessors.isEmpty()) return null;
    return predecessors.getFirst();
  }

  public List<AStarItem<I>> getPredecessors() {
    return predecessors;
  }

  public void setPredecessor(AStarItem<I> predecessor) {
    this.predecessors.clear();
    this.predecessors.add(predecessor);
  }

  public void addPredecessor(AStarItem<I> predecessor) {
    this.predecessors.add(predecessor);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AStarItem<?> other = (AStarItem<?>) o;
    return Objects.equals(item, other.item);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item);
  }
}
