package aoc;

import java.util.concurrent.atomic.AtomicInteger;

public class Player {
  private static final AtomicInteger idProvider = new AtomicInteger();
  public final int id;
  public int pos = -1;
  public long score = 0;

  private final int winScore;

  public Player(int winScore) {
    this.winScore = winScore;
    id = idProvider.incrementAndGet();
  }

  private Player(int id, int winScore, int pos, long score) {
    this.id = id;
    this.winScore = winScore;
    this.pos = pos;
    this.score = score;
  }

  public Player copy() {
    return new Player(id, winScore, pos, score);
  }

  @Override
  public String toString() {
    return "Player{" +
        "id=" + id +
        ", pos=" + (pos + 1) +
        ", score=" + score +
        '}';
  }

  public int getPos() {
    return pos + 1;
  }

  private boolean addScore() {
    score += getPos();
    return score >= winScore;
  }

  public boolean move(int roll) {
    pos = (pos + roll) % 10;
    return addScore();
  }
}
