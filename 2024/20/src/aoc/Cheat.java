package aoc;

import aoc.util.Vec;

import java.util.Objects;

public record Cheat(Vec start, Vec end, int len) {
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Cheat cheat = (Cheat) o;
    return len == cheat.len
        && (Objects.equals(end, cheat.end) && Objects.equals(start, cheat.start)
        || Objects.equals(start, cheat.end) && Objects.equals(end, cheat.start));
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end) + Objects.hash(end, start) + Objects.hash(len);
  }
}
