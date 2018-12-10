package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 {

  interface Circle {
    void addMarble(int value);
    int removeCc7();
  }

  public static class LinkedCircle implements Circle {
    public static class Marble {
      private Marble left;
      private Marble right;
      private int value;

      public Marble(int value) {
        this.value = value;
      }
    }

    private Marble head;

    public LinkedCircle() {
      head = new Marble(0);
      head.right = head;
      head.left = head;
    }

    @Override
    public void addMarble(int value) {
      Marble newHead = new Marble(value);

      Marble newHeadLeft = head.right;
      Marble newHeadRight = head.right.right;

      newHead.left = newHeadLeft;
      newHead.right = newHeadRight;

      newHeadLeft.right = newHead;
      newHeadRight.left = newHead;

      head = newHead;
    }

    @Override
    public int removeCc7() {
      Marble toRemove = head.left.left.left.left.left.left.left;

      head = toRemove.right;
      head.left = toRemove.left;
      head.left.right = head;

      return toRemove.value;
    }
  }

  public static class DequeCircle implements Circle {
    private Deque<Integer> circle = new ArrayDeque<>(Collections.singleton(0));

    @Override
    public void addMarble(int value) {
      circle.addLast(circle.removeFirst());
      circle.addLast(circle.removeFirst());
      circle.addFirst(value);
    }

    @Override
    public int removeCc7() {
      IntStream.range(0, 7).forEach(i-> circle.addFirst(circle.removeLast()));
      return circle.removeFirst();
    }
  }

  public long getHighScore(int players, int maxMarble) {
    //Circle circle = new LinkedCircle();
    Circle circle = new DequeCircle();

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

