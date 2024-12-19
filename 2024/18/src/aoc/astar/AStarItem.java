package aoc.astar;

import java.util.Objects;
import java.util.function.BiFunction;

public class AStarItem<I> {
  public final I item;
  private final I end;
  public final long score;
  private AStarItem<I> predecessor = null;

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
    return predecessor;
  }

  public void setPredecessor(AStarItem<I> predecessor) {
    this.predecessor = predecessor;
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
