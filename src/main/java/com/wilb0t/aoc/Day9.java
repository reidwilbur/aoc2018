package com.wilb0t.aoc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 {

  public static class Circle {
    public static class Marble {
      private Marble left;
      private Marble right;
      private int value;

      public Marble(int value) {
        this.value = value;
      }
    }

    private Marble head;

    public Circle() {
      head = new Marble(0);
      head.right = head;
      head.left = head;
    }

    void addMarble(int value) {
      Marble newHead = new Marble(value);

      Marble newHeadLeft = head.right;
      Marble newHeadRight = head.right.right;

      newHead.left = newHeadLeft;
      newHead.right = newHeadRight;

      newHeadLeft.right = newHead;
      newHeadRight.left = newHead;

      head = newHead;
    }

    int removeCc7() {
      Marble toRemove = head.left.left.left.left.left.left.left;

      head = toRemove.right;
      head.left = toRemove.left;
      head.left.right = head;

      return toRemove.value;
    }
  }

  public long getHighScore(int players, int maxMarble) {
    Circle circle = new Circle();

    List<Long> scores =
        IntStream.range(0, players).mapToObj(i -> 0L).collect(Collectors.toList());

    int curPlayer = 0;
    long maxScore = 0;
    for (int curMarble = 1; curMarble <= maxMarble; curMarble++) {
      if (curMarble % 23 == 0) {
        long newScore = scores.get(curPlayer) + curMarble + circle.removeCc7();
        scores.set(curPlayer, newScore);
        maxScore = (newScore > maxScore) ? newScore : maxScore;
      } else {
        circle.addMarble(curMarble);
      }
      curPlayer = (curPlayer + 1) % players;
    }

    return maxScore;
  }
}

